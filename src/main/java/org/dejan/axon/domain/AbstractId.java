package org.dejan.axon.domain;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static java.util.UUID.randomUUID;
import static org.axonframework.common.Assert.isFalse;
import static org.axonframework.common.Assert.isTrue;
import static org.axonframework.common.Assert.notNull;

@Data
@MappedSuperclass
public abstract class AbstractId implements Serializable {

    private final String identifier;

    protected AbstractId() {
        identifier = randomUUID().toString();
    }

    protected AbstractId(String identifier) {
        notNull(identifier, () -> "Identifier may not be null");
        isFalse(identifier.isEmpty(), () -> "Identifier may not be empty");
        isTrue(identifier.strip().length() == identifier.length(), () -> "Identifier may not contain leading or trailing whitespace");
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
