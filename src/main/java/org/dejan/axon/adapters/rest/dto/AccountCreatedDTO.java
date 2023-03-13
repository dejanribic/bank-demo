package org.dejan.axon.adapters.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreatedDTO {
    private UUID accountId;

    private String customerName;

    private BigDecimal initialBalance;

    private BigDecimal creditLine;
}
