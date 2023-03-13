package org.dejan.axon.domain.service;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.dejan.axon.domain.query.ListPaymentTransactionsSinceDateForAccountQuery;
import org.dejan.axon.domain.transfer.PaymentTransactionsSinceDate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class PaymentTransactionsQueryService {
    private final QueryGateway queryGateway;

    public CompletableFuture<PaymentTransactionsSinceDate> listPaymentsForAccountSinceDate(String accountId, LocalDate date) {
        return queryGateway.query(
            new ListPaymentTransactionsSinceDateForAccountQuery(ServiceUtils.formatUuid(accountId), date),
            ResponseTypes.instanceOf(PaymentTransactionsSinceDate.class));
    }
}
