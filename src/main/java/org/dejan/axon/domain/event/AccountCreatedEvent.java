package org.dejan.axon.domain.event;

import lombok.Data;
import org.axonframework.serialization.Revision;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Revision("1")
public class AccountCreatedEvent {
    private final UUID id;

    private final String customerName;

    private final BigDecimal initialBalance;

    private final BigDecimal creditLine;
}
