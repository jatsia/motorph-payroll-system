package com.motorph.payroll.service.report;

import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.Payslip;
import com.motorph.payroll.util.MoneyFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PdfReportWriter {
    private static final float MARGIN = 42;
    private static final float FONT_SIZE = 8;
    private static final float HEADER_SIZE = 14;
    private static final float ROW_HEIGHT = 18;
    private static final float BOTTOM = 48;

    public Path writePayrollSummary(String fileName, List<PayrollSummaryRow> rows) {
        return write(fileName, true, (document, cursor) -> {
            writeReportHeader(cursor, "MONTHLY PAYROLL SUMMARY REPORT");
            String[] headers = {"Emp No.", "Name", "Position", "Dept", "Gross", "SSS", "PhilHealth", "Pag-IBIG", "BIR", "Total Deductions", "Net Pay"};
            float[] widths = {42, 140, 135, 90, 85, 78, 88, 78, 70, 110, 90};
            cursor.tableHeader(headers, widths);

            double totalGross = 0;
            double totalSss = 0;
            double totalPhilhealth = 0;
            double totalPagibig = 0;
            double totalWithholding = 0;
            double totalDeductions = 0;
            double totalNet = 0;

            for (PayrollSummaryRow row : rows) {
                cursor.ensureSpace(document, ROW_HEIGHT * 2);
                cursor.tableRow(new String[]{
                        String.valueOf(row.getEmployeeId()),
                        row.getFullName(),
                        row.getPosition(),
                        row.getDepartment(),
                        MoneyFormatter.format(row.getGrossIncome()),
                        MoneyFormatter.format(row.getSssDeduction()),
                        MoneyFormatter.format(row.getPhilhealthDeduction()),
                        MoneyFormatter.format(row.getPagibigDeduction()),
                        MoneyFormatter.format(row.getWithholdingTax()),
                        MoneyFormatter.format(row.getDeductions()),
                        MoneyFormatter.format(row.getNetPay())
                }, widths);
                totalGross += row.getGrossIncome();
                totalSss += row.getSssDeduction();
                totalPhilhealth += row.getPhilhealthDeduction();
                totalPagibig += row.getPagibigDeduction();
                totalWithholding += row.getWithholdingTax();
                totalDeductions += row.getDeductions();
                totalNet += row.getNetPay();
            }

            cursor.tableRow(new String[]{"TOTAL", "", "", "", MoneyFormatter.format(totalGross), MoneyFormatter.format(totalSss), MoneyFormatter.format(totalPhilhealth), MoneyFormatter.format(totalPagibig), MoneyFormatter.format(totalWithholding), MoneyFormatter.format(totalDeductions), MoneyFormatter.format(totalNet)}, widths);
        });
    }

    public Path writePayslip(String fileName, Payslip payslip) {
        return write(fileName, true, (document, cursor) -> {
            Employee employee = payslip.getEmployee();
            writeReportHeader(cursor, "EMPLOYEE PAYSLIP");
            cursor.line("Payslip No: " + payslip.getPayslipNumber());
            cursor.line("Period: " + payslip.getPeriodStart() + " to " + payslip.getPeriodEnd());
            cursor.line("Employee: " + employee.getEmployeeId() + " - " + employee.getFullName());
            cursor.line("Position/Department: " + employee.getPosition() + " / " + employee.getDepartment());
            cursor.blank();

            float[] widths = {180, 120};
            cursor.tableHeader(new String[]{"Section", "Amount"}, widths);
            cursor.tableRow(new String[]{"Gross Income", MoneyFormatter.format(payslip.getGrossIncome())}, widths);
            cursor.tableRow(new String[]{"Benefits", MoneyFormatter.format(payslip.getBenefits())}, widths);
            cursor.tableRow(new String[]{"SSS", MoneyFormatter.format(payslip.getSssDeduction())}, widths);
            cursor.tableRow(new String[]{"PhilHealth", MoneyFormatter.format(payslip.getPhilhealthDeduction())}, widths);
            cursor.tableRow(new String[]{"Pag-IBIG", MoneyFormatter.format(payslip.getPagibigDeduction())}, widths);
            cursor.tableRow(new String[]{"Withholding Tax", MoneyFormatter.format(payslip.getWithholdingTax())}, widths);
            cursor.tableRow(new String[]{"Total Deductions", MoneyFormatter.format(payslip.getDeductions())}, widths);
            cursor.tableRow(new String[]{"Take Home Pay", MoneyFormatter.format(payslip.getNetPay())}, widths);
        });
    }

    public Path writeTimecard(String fileName, Employee employee, List<AttendanceRecord> records) {
        return write(fileName, true, (document, cursor) -> {
            writeReportHeader(cursor, "EMPLOYEE TIMECARD");
            cursor.line("Employee: " + employee.getEmployeeId() + " - " + employee.getFullName());
            cursor.line("Position/Department: " + employee.getPosition() + " / " + employee.getDepartment());
            cursor.blank();

            String[] headers = {"Date", "Day", "In", "Out", "Hours", "Remarks"};
            float[] widths = {72, 40, 58, 58, 45, 250};
            cursor.tableHeader(headers, widths);

            for (AttendanceRecord record : records) {
                cursor.ensureSpace(document, ROW_HEIGHT * 2);
                cursor.tableRow(new String[]{
                        record.getWorkDate(),
                        record.getDayName(),
                        record.getTimeIn(),
                        record.getTimeOut(),
                        String.format("%.2f", record.getHoursWorked()),
                        record.getRemarks()
                }, widths);
            }
        });
    }

    public Path write(String fileName, String text) {
        return write(fileName, false, (document, cursor) -> {
            for (String line : text.split("\\R")) {
                cursor.ensureSpace(document, ROW_HEIGHT);
                cursor.line(line);
            }
        });
    }

    private Path write(String fileName, boolean landscape, PdfContentWriter writer) {
        try {
            Path exportPath = Path.of("exports", fileName);
            Files.createDirectories(exportPath.getParent());

            try (PDDocument document = new PDDocument()) {
                PdfCursor cursor = new PdfCursor(document, landscape);
                writer.write(document, cursor);
                cursor.close();
                document.save(exportPath.toFile());
            }

            return exportPath;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not write PDF report.", exception);
        }
    }

    private void writeReportHeader(PdfCursor cursor, String title) throws IOException {
        cursor.heading("MotorPH");
        cursor.line("7 Jupiter Avenue cor. F. Sandoval Jr., Bagong Nayon, Quezon City");
        cursor.line("Phone: (028) 911-5071 / (028) 911-5072 / (028) 911-5073");
        cursor.line("Email: corporate@motorph.com");
        cursor.blank();
        cursor.heading(title);
        cursor.blank();
    }

    private interface PdfContentWriter {
        void write(PDDocument document, PdfCursor cursor) throws IOException;
    }

    private static class PdfCursor {
        private final PDType1Font regularFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        private final PDType1Font boldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        private final boolean landscape;
        private PDPageContentStream content;
        private PDPage page;
        private float y;

        PdfCursor(PDDocument document, boolean landscape) throws IOException {
            this.landscape = landscape;
            newPage(document);
        }

        void heading(String text) throws IOException {
            content.setFont(boldFont, HEADER_SIZE);
            content.beginText();
            content.newLineAtOffset(MARGIN, y);
            content.showText(fit(text, 80));
            content.endText();
            y -= ROW_HEIGHT + 4;
            content.setFont(regularFont, FONT_SIZE);
        }

        void line(String text) throws IOException {
            content.setFont(regularFont, FONT_SIZE);
            content.beginText();
            content.newLineAtOffset(MARGIN, y);
            content.showText(fit(text, 120));
            content.endText();
            y -= ROW_HEIGHT;
        }

        void blank() {
            y -= ROW_HEIGHT / 2;
        }

        void tableHeader(String[] values, float[] widths) throws IOException {
            ensureSpace(null, ROW_HEIGHT * 2);
            drawRow(values, widths, true);
        }

        void tableRow(String[] values, float[] widths) throws IOException {
            drawRow(values, widths, false);
        }

        void ensureSpace(PDDocument document, float needed) throws IOException {
            if (y - needed > BOTTOM) {
                return;
            }

            if (document == null) {
                return;
            }

            close();
            newPage(document);
        }

        void close() throws IOException {
            if (content != null) {
                content.close();
            }
        }

        private void drawRow(String[] values, float[] widths, boolean header) throws IOException {
            float x = MARGIN;
            float rowY = y;

            content.setStrokingColor(new Color(209, 213, 219));
            content.moveTo(MARGIN, rowY + 5);
            content.lineTo(MARGIN + totalWidth(widths), rowY + 5);
            content.stroke();

            content.setFont(header ? boldFont : regularFont, FONT_SIZE);
            for (int index = 0; index < values.length; index++) {
                content.beginText();
                content.newLineAtOffset(x + 3, rowY - 8);
                content.showText(fit(values[index], Math.max(4, (int) (widths[index] / 4.0))));
                content.endText();
                x += widths[index];
            }

            y -= ROW_HEIGHT;
        }

        private void newPage(PDDocument document) throws IOException {
            PDRectangle rectangle = landscape
                    ? new PDRectangle(PDRectangle.A3.getHeight(), PDRectangle.A3.getWidth())
                    : PDRectangle.LETTER;
            page = new PDPage(rectangle);
            document.addPage(page);
            content = new PDPageContentStream(document, page);
            y = page.getMediaBox().getHeight() - MARGIN;
        }

        private float totalWidth(float[] widths) {
            float total = 0;
            for (float width : widths) {
                total += width;
            }
            return total;
        }

        private String fit(String value, int maxLength) {
            if (value == null) {
                return "";
            }

            if (value.length() <= maxLength) {
                return value;
            }

            return value.substring(0, Math.max(0, maxLength - 3)) + "...";
        }
    }
}
