package org.dejan.axon.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.dejan.axon.actions.command.CreateAccountCommand;
import org.dejan.axon.actions.command.CreditAccountCommand;
import org.dejan.axon.actions.command.DebitAccountCommand;
import org.dejan.axon.domain.aggregate.Account;
import org.dejan.axon.domain.aggregate.PaymentTransactionId;
import org.dejan.axon.domain.aggregate.PaymentTransactionIdGenerator;
import org.dejan.axon.domain.entity.PaymentType;
import org.dejan.axon.domain.event.AccountCreatedEvent;
import org.dejan.axon.domain.event.AccountCreditedEvent;
import org.dejan.axon.domain.event.AccountDebitedEvent;
import org.dejan.axon.domain.event.PaymentTransactionCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountAggregateTest {
    private static final String customerName = "Dejan";

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1000);
    private static final BigDecimal SMALL_AMOUNT = BigDecimal.valueOf(100);
    private static final BigDecimal LARGE_AMOUNT = BigDecimal.valueOf(5000);
    private static final BigDecimal CREDIT_LINE = BigDecimal.valueOf(200);

    private final List<PaymentTransactionId> paymentTransactionIds = List.of(new PaymentTransactionId());

    LocalDate now = LocalDate.now();

    private FixtureConfiguration<Account> fixture;

    private UUID accountId;

    private PaymentTransactionIdGenerator paymentTransactionIdGenerator;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(Account.class);
        accountId = UUID.fromString("eabf2c65-9dca-4fc4-8b36-cdd35302cbc0");
        paymentTransactionIdGenerator = mock(PaymentTransactionIdGenerator.class);
        fixture.registerInjectableResource(paymentTransactionIdGenerator);
    }

    @Test
    public void shouldDispatchAccountCreatedEventWhenCreateAccountCommandReceived() {
        fixture.givenNoPriorActivity()
            .when(createAccount())
            .expectEvents(accountCreated());
    }

    private CreateAccountCommand createAccount() {
        return new CreateAccountCommand(
            accountId,
            customerName,
            INITIAL_BALANCE,
            CREDIT_LINE);
    }

    private AccountCreatedEvent accountCreated() {
        return new AccountCreatedEvent(
            accountId,
            customerName,
            INITIAL_BALANCE,
            CREDIT_LINE);
    }

    @Test
    public void shouldDispatchAccountCreditedEventWhenCreditAccountCommandReceived() {
        when(paymentTransactionIdGenerator.next())
            .thenReturn(paymentTransactionIds.get(0));

        fixture.given(accountCreated())
            .when(new CreditAccountCommand(
                accountId,
                SMALL_AMOUNT,
                now))
            .expectEvents(
                new PaymentTransactionCreatedEvent(
                    paymentTransactionIds.get(0),
                    accountId,
                    PaymentType.CREDIT,
                    SMALL_AMOUNT,
                    now
                ),
                new AccountCreditedEvent(
                accountId,
                SMALL_AMOUNT,
                now));
    }

    @Test
    public void shouldDispatchAccountDebitedEventWhenDebitAccountCommandReceived() {
        when(paymentTransactionIdGenerator.next())
            .thenReturn(paymentTransactionIds.get(0));

        fixture.given(accountCreated())
            .when(new DebitAccountCommand(
                accountId,
                SMALL_AMOUNT,
                now))
            .expectEvents(
                new PaymentTransactionCreatedEvent(
                    paymentTransactionIds.get(0),
                    accountId,
                    PaymentType.DEBIT,
                    SMALL_AMOUNT,
                    now
                ),
                new AccountDebitedEvent(
                    accountId,
                    SMALL_AMOUNT,
                    now));
    }

    @Test
    public void shouldNotDispatchEventWhenBalanceIsLowerThanDebitAmount() {
        fixture.given(accountCreated())
            .when(new DebitAccountCommand(
                accountId,
                LARGE_AMOUNT,
                now))
            .expectNoEvents();
    }
}
