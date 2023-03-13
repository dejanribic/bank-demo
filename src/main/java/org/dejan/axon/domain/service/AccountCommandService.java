package org.dejan.axon.domain.service;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.dejan.axon.actions.command.CreateAccountCommand;
import org.dejan.axon.actions.command.CreditAccountCommand;
import org.dejan.axon.actions.command.DebitAccountCommand;
import org.dejan.axon.adapters.rest.dto.AccountCreatedDTO;
import org.dejan.axon.adapters.rest.dto.CreateAccountDTO;
import org.dejan.axon.adapters.rest.dto.PaymentDTO;
import org.dejan.axon.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountCommandService {
    private final CommandGateway commandGateway;

    public AccountCreatedDTO createAccount(CreateAccountDTO creationDTO) {
        BigDecimal creditLine = creationDTO.getCreditLine();
        String customerName = creationDTO.getCustomerName();
        BigDecimal initialBalance = creationDTO.getInitialBalance();
        UUID uuid = UUID.randomUUID();

        commandGateway.send(new CreateAccountCommand(
            uuid,
            customerName,
            initialBalance,
            creditLine
        ));
        return new AccountCreatedDTO(uuid, customerName, initialBalance, creditLine);
    }

    public boolean creditAccount(String accountId, PaymentDTO payment) {
        commandGateway.send(new CreditAccountCommand(
            ServiceUtils.formatUuid(accountId),
            payment.getAmount(),
            ServiceUtils.parseDate(payment.getValueDate())));
        return true;
    }

    public boolean debitAccount(String accountId, PaymentDTO payment) {
        commandGateway.send(new DebitAccountCommand(
            ServiceUtils.formatUuid(accountId),
            payment.getAmount(),
            ServiceUtils.parseDate(payment.getValueDate())));
        return true;
    }
}
