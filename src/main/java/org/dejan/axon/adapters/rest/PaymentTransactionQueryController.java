package org.dejan.axon.adapters.rest;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.dejan.axon.domain.service.PaymentTransactionsQueryService;
import org.dejan.axon.domain.service.ServiceUtils;
import org.dejan.axon.domain.transfer.PaymentTransactionsSinceDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/payments")
@Api(value = "Payment Transaction Queries")
@AllArgsConstructor
public class PaymentTransactionQueryController {

    private final PaymentTransactionsQueryService paymentTransactionsQueryService;

    @GetMapping("/{accountId}/events/{date}")
    public CompletableFuture<PaymentTransactionsSinceDate> listPaymentTransactionsForAccountSinceDate(@PathVariable(value = "accountId") String accountId,
                                                                                                      @PathVariable(value = "date") String date) {
        return paymentTransactionsQueryService.listPaymentsForAccountSinceDate(accountId, ServiceUtils.parseDate(date));
    }
}
