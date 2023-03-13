package org.dejan.axon.adapters.rest;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.dejan.axon.adapters.rest.dto.PaymentDTO;
import org.dejan.axon.adapters.rest.dto.TestDebitResponse;
import org.dejan.axon.domain.entity.AccountEntity;
import org.dejan.axon.domain.service.AccountQueryService;
import org.dejan.axon.domain.service.ServiceUtils;
import org.dejan.axon.domain.transfer.RedAccountsList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/accounts")
@Api(value = "AccountEntity Queries")
@AllArgsConstructor
public class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @GetMapping("/{accountId}")
    public CompletableFuture<AccountEntity> findById(@PathVariable("accountId") String accountId) {
        if (ServiceUtils.mockValidation(accountId)) {
            return accountQueryService.findById(accountId);
        }
        return null;
    }

    @GetMapping("/{accountId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "accountId") String accountId) {
        if (ServiceUtils.mockValidation(accountId)) {
            return accountQueryService.listAllEventsForAccount(accountId);
        }
        return null;
    }

    @GetMapping("/red-accounts")
    public CompletableFuture<RedAccountsList> findRed() {
        return accountQueryService.findRedAccounts();
    }

    @PostMapping("/{accountId}/test-debit")
    public CompletableFuture<TestDebitResponse> testDebit(@PathVariable(value = "accountId") String accountId,
                                                          @RequestBody PaymentDTO payment) {
        return accountQueryService.testDebit(accountId, payment.getAmount());
    }
}
