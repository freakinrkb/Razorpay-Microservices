package com.razorpay.merchant_service.service.impl;


import com.razorpay.common_lib.enums.MerchantStatus;
import com.razorpay.common_lib.enums.UserRole;
import com.razorpay.common_lib.exception.DuplicateResourceException;
import com.razorpay.common_lib.exception.ResourceNotFoundException;
import com.razorpay.merchant_service.dto.request.LoginRequest;
import com.razorpay.merchant_service.dto.request.MerchantSignupRequest;
import com.razorpay.merchant_service.dto.response.LoginResponse;
import com.razorpay.merchant_service.dto.response.MerchantResponse;
import com.razorpay.merchant_service.entity.AppUser;
import com.razorpay.merchant_service.entity.Merchant;
import com.razorpay.merchant_service.mapper.MerchantMapper;
import com.razorpay.merchant_service.repository.AppUserRepository;
import com.razorpay.merchant_service.repository.MerchantRepository;
import com.razorpay.merchant_service.security.JwtUtil;
import com.razorpay.merchant_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {
        if(merchantRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL",
                    "Merchant with email already exists: " + request.email());
        }
        Merchant merchant = merchantMapper.toEntityFromSignUpRequest(request);
        merchant.setStatus(MerchantStatus.PENDING_KYC);

        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .merchant(merchant)
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(UserRole.OWNER)
                .build();
        appUserRepository.save(appUser);

        return merchantMapper.toResponse(merchant);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        AppUser appUser = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.email()));

        String token = jwtUtil.generateAccessToken(request.email(),
                appUser.getMerchant().getId(), appUser.getRole().toString());

        return new LoginResponse(token);
    }
}
