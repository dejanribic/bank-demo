package org.dejan.axon.domain.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDebit {
    private UUID accountId;

    private BigDecimal amount;
}
