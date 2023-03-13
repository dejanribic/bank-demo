package org.dejan.axon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentTransactionEntity {

    @Id
    private UUID id;

    private UUID accountId;

    private PaymentType type;

    private BigDecimal amount;

    private LocalDate date;
}
