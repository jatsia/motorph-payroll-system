package com.motorph.payroll;

import com.motorph.payroll.service.FileOpenService;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileOpenServiceTest {
    @Test
    void rejectsMissingFile() {
        FileOpenService service = new FileOpenService();

        assertThrows(IllegalArgumentException.class, () -> service.open(Path.of("exports", "missing-file.pdf")));
    }
}

