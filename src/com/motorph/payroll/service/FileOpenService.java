package com.motorph.payroll.service;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOpenService {
    public boolean canOpenFiles() {
        return Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN);
    }

    public void open(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File does not exist.");
        }

        if (!canOpenFiles()) {
            throw new IllegalStateException("Opening files is not supported on this system.");
        }

        try {
            Desktop.getDesktop().open(path.toFile());
        } catch (IOException exception) {
            throw new IllegalStateException("Could not open file.", exception);
        }
    }
}

