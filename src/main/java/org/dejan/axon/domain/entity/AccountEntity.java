package org.dejan.axon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountEntity implements Serializable {
    @Id
    private UUID id;

    private String customerName;

    private BigDecimal balance;

    private BigDecimal creditLine;
}
