package com.nls.myrewards.util;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public final class LocalDates {
    private LocalDates() {
    }

    public static LocalDate tryParse(String string) {
        if (string == null || string.trim().isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(string.trim(), DateTimeFormat.forPattern("yyyy-MM-dd"));
        } catch (Throwable ignore) {
        }

        try {
            return LocalDate.parse(string.trim(), DateTimeFormat.forPattern("dd/MM/yyyy"));
        } catch (Throwable ignore) {
        }

        return null;
    }
}
