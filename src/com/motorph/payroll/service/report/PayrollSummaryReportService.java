package com.motorph.payroll.service.report;

import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.PayrollResult;
import com.motorph.payroll.service.PayrollService;
import com.motorph.payroll.util.MoneyFormatter;

import java.util.ArrayList;
import java.util.List;

public class PayrollSummaryReportService {
    public List<PayrollSummaryRow> buildRows(List<Employee> employees, PayrollService payrollService) {
        List<PayrollSummaryRow> rows = new ArrayList<>();

        for (Employee employee : employees) {
            PayrollResult result = payrollService.calculate(employee);
            rows.add(new PayrollSummaryRow(
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    employee.getPosition(),
                    employee.getDepartment(),
                    result.getGrossIncome(),
                    result.getSssDeduction(),
                    result.getPhilhealthDeduction(),
                    result.getPagibigDeduction(),
                    result.getWithholdingTax(),
                    result.getDeductions(),
                    result.getNetPay()
            ));
        }

        return rows;
    }

    public String buildText(List<Employee> employees, PayrollService payrollService) {
        StringBuilder builder = new StringBuilder();
        builder.append("""
                MotorPH
                MONTHLY PAYROLL SUMMARY REPORT

                Employee No. | Employee Full Name | Position | Department | Gross Income | Deductions | Net Pay
                """);

        double totalGross = 0;
        double totalDeductions = 0;
        double totalNet = 0;

        for (PayrollSummaryRow row : buildRows(employees, payrollService)) {
            double grossIncome = row.getGrossIncome();
            double deductions = row.getDeductions();
            double netPay = row.getNetPay();

            totalGross += grossIncome;
            totalDeductions += deductions;
            totalNet += netPay;

            builder.append("%d | %s | %s | %s | %s | %s | %s%n".formatted(
                    row.getEmployeeId(),
                    row.getFullName(),
                    row.getPosition(),
                    row.getDepartment(),
                    MoneyFormatter.format(grossIncome),
                    MoneyFormatter.format(deductions),
                    MoneyFormatter.format(netPay)
            ));
        }

        builder.append("%nTOTAL | | | | %s | %s | %s%n".formatted(
                MoneyFormatter.format(totalGross),
                MoneyFormatter.format(totalDeductions),
                MoneyFormatter.format(totalNet)
        ));

        return builder.toString();
    }
}
