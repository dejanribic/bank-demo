package org.dejan.axon.domain.projection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.dejan.axon.domain.entity.PaymentTransactionEntity;
import org.dejan.axon.domain.event.PaymentTransactionCreatedEvent;
import org.dejan.axon.domain.query.ListPaymentTransactionsSinceDateForAccountQuery;
import org.dejan.axon.domain.repository.PaymentTransactionRepository;
import org.dejan.axon.domain.service.ServiceUtils;
import org.dejan.axon.domain.transfer.PaymentTransactionsSinceDate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentTransactionProjection {

    private final PaymentTransactionRepository repository;

    @EventHandler
    public void on(PaymentTransactionCreatedEvent event) {
        log.debug("Handling Payment Transaction creation event {}", event.getPaymentTransactionId());
        PaymentTransactionEntity paymentTransaction = new PaymentTransactionEntity(
            ServiceUtils.formatUuid(event.getPaymentTransactionId().getIdentifier()),
            event.getAccountId(),
            event.getType(),
            event.getBookingAmount(),
            event.getValueDate()
        );
        repository.save(paymentTransaction);
    }

    @QueryHandler
    public PaymentTransactionsSinceDate handle(ListPaymentTransactionsSinceDateForAccountQuery query) {
        log.debug("Handling ListPaymentTransactionsSinceDateForAccountQuery query: {}", query);
        return new PaymentTransactionsSinceDate(repository.findByAccountIdAndDateGreaterThanEqual(query.getAccountId(), query.getDate()));
    }
}
