package org.dejan.axon.domain.aggregate;

import org.springframework.stereotype.Component;

@Component
public class PaymentTransactionIdGenerator {
    public PaymentTransactionId next() {
            return new PaymentTransactionId();
        }
}