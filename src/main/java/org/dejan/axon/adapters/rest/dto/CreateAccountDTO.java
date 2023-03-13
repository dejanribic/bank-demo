package org.dejan.axon.adapters.rest.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateAccountDTO {
    String customerName;

    BigDecimal initialBalance;

    BigDecimal creditLine;
}
