package org.dejan.axon.domain.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dejan.axon.domain.entity.AccountEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedAccountsList {
    List<AccountEntity> redAccounts;
}
