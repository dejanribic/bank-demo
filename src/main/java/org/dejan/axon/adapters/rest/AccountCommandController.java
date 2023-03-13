package org.dejan.axon.adapters.rest;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.dejan.axon.adapters.rest.dto.AccountCreatedDTO;
import org.dejan.axon.adapters.rest.dto.CreateAccountDTO;
import org.dejan.axon.adapters.rest.dto.PaymentDTO;
import org.dejan.axon.domain.service.AccountCommandService;
import org.dejan.axon.domain.service.ServiceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/account")
@Api(value = "AccountEntity Commands")
@AllArgsConstructor
public class AccountCommandController {
    private final AccountCommandService accountCommandService;

    @PostMapping
    @ResponseStatus(value = CREATED)
    public AccountCreatedDTO createAccount(@RequestBody CreateAccountDTO creationDTO) {
        if (ServiceUtils.mockValidation(creationDTO)) {
            return accountCommandService.createAccount(creationDTO);
        }
        return null;
    }

    @PutMapping(value = "/credit/{accountId}")
    public boolean creditAccount(@PathVariable(value = "accountId") String accountId,
                                 @RequestBody PaymentDTO payment) {
        if (ServiceUtils.mockValidation(payment)) {
            return accountCommandService.creditAccount(accountId, payment);
        }
        return false;
    }

    @PutMapping(value = "/debit/{accountId}")
    public boolean debitAccount(@PathVariable(value = "accountId") String accountId,
                                @RequestBody PaymentDTO payment) {
        if (ServiceUtils.mockValidation(payment)) {
            return accountCommandService.debitAccount(accountId, payment);
        }
        return false;
    }
}
