package org.dejan.axon.domain.exception;

import java.util.UUID;

public class AccountNotFoundException extends Throwable {
    public AccountNotFoundException(UUID id) {
        super("Cannot find account number [" + id + "]");
    }
}
