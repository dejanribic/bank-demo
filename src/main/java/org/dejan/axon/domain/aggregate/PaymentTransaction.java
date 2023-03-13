package org.dejan.axon.domain.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.dejan.axon.domain.entity.PaymentType;
import org.dejan.axon.domain.event.PaymentTransactionCreatedEvent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@NoArgsConstructor
@Getter
@Aggregate
public class PaymentTransaction {

    @AggregateIdentifier
    private PaymentTransactionId paymentTransactionId;

    private UUID accountId;

    private PaymentType type;

    private BigDecimal bookingAmount;

    private LocalDate valueDate;

    public transient PaymentTransactionIdGenerator idGenerator;

    public PaymentTransaction(PaymentTransactionId id, UUID accountId, PaymentType type, BigDecimal bookingAmount, LocalDate valueDate) {
        apply(new PaymentTransactionCreatedEvent(
            id,
            accountId,
            type,
            bookingAmount,
            valueDate));
    }

    @EventSourcingHandler
    public void on(PaymentTransactionCreatedEvent event) {
        paymentTransactionId = new PaymentTransactionId(event.getPaymentTransactionId().toString());
        accountId = event.getAccountId();
        type = event.getType();
        bookingAmount = event.getBookingAmount();
        valueDate = event.getValueDate();
    }
}