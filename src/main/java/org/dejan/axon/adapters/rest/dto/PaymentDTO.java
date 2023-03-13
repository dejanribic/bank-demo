package org.dejan.axon.adapters.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentDTO {
    private BigDecimal amount;

    private String valueDate;
}
