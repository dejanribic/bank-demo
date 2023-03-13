package org.dejan.axon.domain.service;

import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.dejan.axon.adapters.rest.dto.TestDebitResponse;
import org.dejan.axon.domain.entity.AccountEntity;
import org.dejan.axon.domain.query.FindAccountQuery;
import org.dejan.axon.domain.query.FindRedAccounts;
import org.dejan.axon.domain.transfer.RedAccountsList;
import org.dejan.axon.domain.transfer.TestDebit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountQueryService {
    private final QueryGateway queryGateway;

    private final EventStore eventStore;

    public CompletableFuture<RedAccountsList> findRedAccounts() {
        return queryGateway.query(
            new FindRedAccounts(),
            ResponseTypes.instanceOf(RedAccountsList.class));
    }

    public CompletableFuture<TestDebitResponse> testDebit(String accountId, BigDecimal amount) {
        return queryGateway.query(
            new TestDebit(ServiceUtils.formatUuid(accountId), amount),
            ResponseTypes.instanceOf(TestDebitResponse.class));
    }

    public CompletableFuture<AccountEntity> findById(String accountId) {
        return queryGateway.query(
            new FindAccountQuery(ServiceUtils.formatUuid(accountId)),
            ResponseTypes.instanceOf(AccountEntity.class));
    }

    public List<Object> listAllEventsForAccount(String accountId) {
        return eventStore
            .readEvents(ServiceUtils.formatUuid(accountId).toString())
            .asStream()
            .map(Message::getPayload)
            .collect(Collectors.toList());
    }
}
