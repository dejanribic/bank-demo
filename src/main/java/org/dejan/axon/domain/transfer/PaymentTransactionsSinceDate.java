package org.dejan.axon.domain.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dejan.axon.domain.entity.PaymentTransactionEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionsSinceDate {
    List<PaymentTransactionEntity> transactions;
}
