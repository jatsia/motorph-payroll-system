package com.motorph.payroll.util;

public final class MoneyFormatter {
    private MoneyFormatter() {
    }

    public static String format(double value) {
        return String.format("PHP %,.2f", value);
    }
}

