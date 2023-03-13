package org.dejan.axon.domain.event;

import lombok.Value;
import org.axonframework.serialization.Revision;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Revision("1")
public class AccountCreditedEvent {
    UUID id;

    BigDecimal creditAmount;

    LocalDate valueDate;
}
