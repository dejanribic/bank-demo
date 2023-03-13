package org.dejan.axon.domain.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ServiceUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d-MM-yyyy");

    public static UUID formatUuid(String accountId) {
        accountId = accountId.replace("-", "");
        String formatted = accountId.substring(0, 8) + "-" +
            accountId.substring(8, 12) + "-" +
            accountId.substring(12, 16) + "-" +
            accountId.substring(16, 20) + "-" +
            accountId.substring(20, 32);
        return UUID.fromString(formatted);
    }

    public static UUID generateUuid() {
        return UUID.randomUUID();
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    public static boolean mockValidation(Object data) {
        return true;
        // throw some exception, bad request etc.
    }
}
