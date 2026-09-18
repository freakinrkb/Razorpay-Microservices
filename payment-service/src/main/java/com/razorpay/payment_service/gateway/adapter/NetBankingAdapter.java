package com.razorpay.payment_service.gateway.adapter;

import com.razorpay.common_lib.enums.PaymentMethod;
import com.razorpay.payment_service.gateway.PaymentAdapter;
import com.razorpay.payment_service.gateway.PaymentGatewayRouter;
import com.razorpay.payment_service.gateway.dto.PaymentRequest;
import com.razorpay.payment_service.gateway.dto.PaymentResult;
import com.razorpay.payment_service.processor.PaymentProcessorRouter;
import com.razorpay.payment_service.processor.dto.PaymentProcessorRequest;
import com.razorpay.payment_service.processor.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
@RequiredArgsConstructor
@Component("NETBANKING")
@Slf4j
public class NetBankingAdapter implements PaymentAdapter {

    private final PaymentProcessorRouter paymentProcessorRouter;

    @Override
    public PaymentResult initiate(PaymentRequest request) {
        log.info("Initiate Payment with NetBankingAdapter, paymentId: {}", request.paymentId());
        try {
            PaymentProcessorRequest paymentProcessorRequest = PaymentProcessorRequest.nonCard(
                    request.paymentId(),
                    PaymentMethod.NETBANKING,
                    request.amount(),
                    request.methodDetails()
            );

            PaymentProcessorResponse paymentProcessorResponse =
                    paymentProcessorRouter.charge(paymentProcessorRequest);

            return switch (paymentProcessorResponse) {
                case PaymentProcessorResponse.Failure failure ->
                        new PaymentResult.Failure(failure.errorCode(), failure.errorDescription());

                case PaymentProcessorResponse.Pending pending ->
                        new PaymentResult.Pending(pending.processorReference());

                case PaymentProcessorResponse.Success success -> new PaymentResult.Success(success.bankReference());

            };
        } catch(Exception e) {
            log.warn("UPI failed, paymentId: {}", request.paymentId());
            return new PaymentResult.Failure("NBK_FAILED", e.getMessage());
        }
    }

    @Override
    public PaymentResult capture(UUID paymentId) {
        return new PaymentResult.Success("UPI_REF");
    }
}
