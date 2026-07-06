package com.motorph.payroll.service.report;

import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.Payslip;
import com.motorph.payroll.util.MoneyFormatter;

public class PayslipReportService {
    public String buildText(Payslip payslip) {
        Employee employee = payslip.getEmployee();

        return """
                MotorPH
                7 Jupiter Avenue cor. F. Sandoval Jr., Bagong Nayon, Quezon City
                Phone: (028) 911-5071 / (028) 911-5072 / (028) 911-5073
                Email: corporate@motorph.com

                EMPLOYEE PAYSLIP

                Payslip No:        %s
                Period Start:      %s
                Period End:        %s

                Employee ID:       %d
                Employee Name:     %s
                Position/Dept:     %s / %s

                EARNINGS
                Gross Income:      %s

                BENEFITS
                Total Benefits:    %s

                DEDUCTIONS
                SSS:               %s
                PhilHealth:        %s
                Pag-IBIG:          %s
                Withholding Tax:   %s
                Total Deductions:  %s

                SUMMARY
                Take Home Pay:     %s
                """.formatted(
                payslip.getPayslipNumber(),
                payslip.getPeriodStart(),
                payslip.getPeriodEnd(),
                employee.getEmployeeId(),
                employee.getFullName(),
                employee.getPosition(),
                employee.getDepartment(),
                MoneyFormatter.format(payslip.getGrossIncome()),
                MoneyFormatter.format(payslip.getBenefits()),
                MoneyFormatter.format(payslip.getSssDeduction()),
                MoneyFormatter.format(payslip.getPhilhealthDeduction()),
                MoneyFormatter.format(payslip.getPagibigDeduction()),
                MoneyFormatter.format(payslip.getWithholdingTax()),
                MoneyFormatter.format(payslip.getDeductions()),
                MoneyFormatter.format(payslip.getNetPay())
        );
    }
}
