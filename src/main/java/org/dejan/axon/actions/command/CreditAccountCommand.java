package org.dejan.axon.actions.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccountCommand {

    @TargetAggregateIdentifier
    private UUID accountId;

    private BigDecimal bookingAmount;

    private LocalDate valueDate;
}
