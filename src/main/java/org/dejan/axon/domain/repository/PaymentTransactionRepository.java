package org.dejan.axon.domain.repository;

import org.dejan.axon.domain.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, UUID> {
    List<PaymentTransactionEntity> findByAccountIdAndDateGreaterThanEqual(UUID accountId, LocalDate date);
}
