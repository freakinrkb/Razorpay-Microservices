package com.razorpay.common_lib.util;


import com.razorpay.common_lib.context.MerchantContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.web.context.annotation.RequestScope;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@AutoConfiguration
public class SharedSecurityAutoConfiguration {

    @Bean
    public SignerUtil signerUtil() {
        return new SignerUtil();
    }

    @Bean
    @ConditionalOnProperty(name = "vault.master-key")
    public BytesEncryptor masterKeyEncryptor(@Value("${vault.master-key}") String masterKey) {
        byte[] keyBytes = Base64.getDecoder().decode(masterKey);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES/GCM/NoPadding");
        return new AesBytesEncryptor(key, KeyGenerators.secureRandom(12),
                AesBytesEncryptor.CipherAlgorithm.GCM);
    }

    @Bean
    @ConditionalOnProperty(name = "webhook.secret-encryption-key")
    public BytesEncryptor webhookSecretEncryptor(@Value("${webhook.secret-encryption-key}") String masterKey) {
        byte[] keyBytes = Base64.getDecoder().decode(masterKey);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES/GCM/NoPadding");
        return new AesBytesEncryptor(key, KeyGenerators.secureRandom(12),
                AesBytesEncryptor.CipherAlgorithm.GCM);
    }

    @Bean
    @RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public MerchantContext merchantContext() {
        return new MerchantContext();
    }

}
