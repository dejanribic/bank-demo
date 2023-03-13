package org.dejan.axon.domain.projection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.dejan.axon.adapters.rest.dto.TestDebitResponse;
import org.dejan.axon.domain.entity.AccountEntity;
import org.dejan.axon.domain.event.AccountCreatedEvent;
import org.dejan.axon.domain.event.AccountCreditedEvent;
import org.dejan.axon.domain.event.AccountDebitedEvent;
import org.dejan.axon.domain.exception.AccountNotFoundException;
import org.dejan.axon.domain.query.FindAccountQuery;
import org.dejan.axon.domain.query.FindRedAccounts;
import org.dejan.axon.domain.repository.AccountRepository;
import org.dejan.axon.domain.transfer.RedAccountsList;
import org.dejan.axon.domain.transfer.TestDebit;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AccountProjection {

    private final AccountRepository repository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.debug("Handling Account creation command {}", event.getId());
        AccountEntity account = new AccountEntity(
            event.getId(),
            event.getCustomerName(),
            event.getInitialBalance(),
            event.getCreditLine()
        );
        repository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event) throws AccountNotFoundException {
        log.debug("Handling a Account Credit command {}", event.getId());
        Optional<AccountEntity> optionalAccount = repository.findById(event.getId());
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(event.getId());
        } else {
            AccountEntity account = optionalAccount.get();
            account.setBalance(account.getBalance().add(event.getCreditAmount()));
            repository.save(account);
        }
    }

    @EventHandler
    public void on(AccountDebitedEvent event) throws AccountNotFoundException {
        log.debug("Handling a Account Debit command {}", event.getId());
        Optional<AccountEntity> optionalAccount = repository.findById(event.getId());
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(event.getId());
        } else {
            AccountEntity account = optionalAccount.get();
            account.setBalance(account.getBalance().subtract(event.getDebitAmount()));
            repository.save(account);
        }
    }

    @QueryHandler
    public AccountEntity handle(FindAccountQuery query) throws AccountNotFoundException {
        log.debug("Handling FindAccountQuery query: {}", query);
        UUID accountId = query.getAccountId();
        Optional<AccountEntity> optionalAccount = repository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(accountId);
        } else {
            return optionalAccount.get();
        }
    }

    @QueryHandler
    public RedAccountsList handle(FindRedAccounts query) {
        log.debug("Handling FindRedAccounts query: {}", query);
        return new RedAccountsList(repository.findByBalanceLessThan(BigDecimal.ZERO));
    }

    @QueryHandler
    public TestDebitResponse handle(TestDebit query) throws AccountNotFoundException {
        log.debug("Handling TestDebit query: {}", query);
        UUID accountId = query.getAccountId();
        Optional<AccountEntity> account = repository.findById(accountId);
        if (account.isEmpty()) {
            throw new AccountNotFoundException(accountId);
        } else {
            AccountEntity accountEntity = account.get();
            if (accountEntity.getBalance()
                .subtract(query.getAmount())
                    .compareTo(accountEntity.getCreditLine().negate()) < 0) {
                        return new TestDebitResponse(false);
            }
            return new TestDebitResponse(true);
        }
    }
}
