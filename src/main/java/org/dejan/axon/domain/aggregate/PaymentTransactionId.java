package org.dejan.axon.domain.aggregate;

import org.dejan.axon.domain.AbstractId;

public class PaymentTransactionId extends AbstractId {
    
    public PaymentTransactionId(String identifier) {
        super(identifier);
    }

    public PaymentTransactionId() {
    }
}