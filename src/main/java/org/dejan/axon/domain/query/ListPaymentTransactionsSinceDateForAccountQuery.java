package org.dejan.axon.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPaymentTransactionsSinceDateForAccountQuery {
    private UUID accountId;

    private LocalDate date;
}
