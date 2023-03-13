package org.dejan.axon.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.dejan.axon.actions.command.CreateAccountCommand;
import org.dejan.axon.actions.command.CreditAccountCommand;
import org.dejan.axon.actions.command.DebitAccountCommand;
import org.dejan.axon.domain.entity.PaymentType;
import org.dejan.axon.domain.event.AccountCreatedEvent;
import org.dejan.axon.domain.event.AccountCreditedEvent;
import org.dejan.axon.domain.event.AccountDebitedEvent;
import org.dejan.axon.domain.exception.InsufficientBalanceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.createNew;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class Account {

    @AggregateIdentifier
    private UUID accountId;

    private BigDecimal balance;

    private BigDecimal creditLine;

    private String customerName;

    @CommandHandler
    public Account(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(
            command.getAccountId(),
            command.getCustomerName(),
            command.getInitialBalance(),
            command.getCreditLine()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        accountId = event.getId();
        customerName = event.getCustomerName();
        balance = event.getInitialBalance();
        creditLine = event.getCreditLine();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command, PaymentTransactionIdGenerator idGenerator) {
        UUID accountId = command.getAccountId();
        BigDecimal bookingAmount = command.getBookingAmount();
        LocalDate valueDate = command.getValueDate();
        try {
            createNew(
                PaymentTransaction.class, () -> new PaymentTransaction(
                    idGenerator.next(),
                    accountId,
                    PaymentType.CREDIT,
                    bookingAmount,
                    valueDate
                )
            );
            apply(new AccountCreditedEvent(
                accountId,
                bookingAmount,
                valueDate));
        } catch (Exception e) {
            log.debug("Failed to credit account: {}}", accountId);
        }
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        balance = balance.add(event.getCreditAmount());
    }

    @CommandHandler
    public void handle(DebitAccountCommand command, PaymentTransactionIdGenerator idGenerator) throws InsufficientBalanceException {
        UUID accountId = command.getAccountId();
        BigDecimal bookingAmount = command.getBookingAmount();

        if (balanceInsufficient(bookingAmount)) {
            log.debug("Insufficient balance for debit transaction, account: {}}", accountId);
            throw new InsufficientBalanceException(accountId, bookingAmount);
        }

        try {
            LocalDate valueDate = command.getValueDate();
            createNew(
                PaymentTransaction.class, () -> new PaymentTransaction(
                    idGenerator.next(),
                    accountId,
                    PaymentType.DEBIT,
                    bookingAmount,
                    valueDate
                )
            );
            apply(new AccountDebitedEvent(
                accountId,
                bookingAmount,
                valueDate));
        } catch (Exception e) {
            log.debug("Failed to debit account: {}}", accountId);
        }
    }

    private boolean balanceInsufficient(BigDecimal debitAmount) {
        return balance.subtract(debitAmount).compareTo(creditLine.negate()) < 0;
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        balance = balance.subtract(event.getDebitAmount());
    }
}