package com.motorph.payroll.service;

import com.motorph.payroll.ui.components.MessageDialogs;

import java.awt.Component;
import java.nio.file.Path;

public class PdfExportService {
    private final FileOpenService fileOpenService;

    public PdfExportService() {
        this(new FileOpenService());
    }

    public PdfExportService(FileOpenService fileOpenService) {
        this.fileOpenService = fileOpenService;
    }

    public void showExported(Component parent, Path path) {
        if (!MessageDialogs.confirm(parent, "PDF exported to " + path + "\n\nOpen PDF now?")) {
            return;
        }

        try {
            fileOpenService.open(path);
        } catch (Exception exception) {
            MessageDialogs.error(parent, exception.getMessage());
        }
    }
}

