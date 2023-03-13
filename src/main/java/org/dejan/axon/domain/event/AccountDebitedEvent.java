package org.dejan.axon.domain.event;

import lombok.Value;
import org.axonframework.serialization.Revision;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Revision("1")
public class AccountDebitedEvent {
    UUID id;

    BigDecimal debitAmount;

    LocalDate valueDate;
}
