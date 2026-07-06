package com.motorph.payroll;

import com.motorph.payroll.util.MoneyFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyFormatterTest {
    @Test
    void formatsPesoValues() {
        assertEquals("PHP 26,750.00", MoneyFormatter.format(26750));
    }
}

