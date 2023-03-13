package org.dejan.axon.domain.event;

import lombok.Value;
import org.axonframework.serialization.Revision;
import org.dejan.axon.domain.aggregate.PaymentTransactionId;
import org.dejan.axon.domain.entity.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Revision("1")
public class PaymentTransactionCreatedEvent {
    PaymentTransactionId paymentTransactionId;

    UUID accountId;

    PaymentType type;

    BigDecimal bookingAmount;

    LocalDate valueDate;
}