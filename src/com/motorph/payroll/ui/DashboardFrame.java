package com.motorph.payroll.ui;

import com.motorph.payroll.repository.AttendanceRepository;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.repository.RepositoryFactory;
import com.motorph.payroll.service.PayrollService;
import com.motorph.payroll.ui.theme.AppTheme;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        EmployeeRepository employeeRepository = repositoryFactory.createEmployeeRepository();
        AttendanceRepository attendanceRepository = repositoryFactory.createAttendanceRepository();
        PayrollService payrollService = new PayrollService();

        setTitle("MotorPH Payroll System");
        setSize(1280, 760);
        setMinimumSize(new Dimension(1100, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.ACCENT);
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("MotorPH Payroll System");
        title.setForeground(java.awt.Color.WHITE);
        title.setFont(title.getFont().deriveFont(20f));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Employees", new EmployeePanel(employeeRepository));
        tabs.addTab("Timecard", new TimecardPanel(employeeRepository, attendanceRepository));
        tabs.addTab("Payroll Summary", new PayrollSummaryPanel(employeeRepository, payrollService));
        tabs.addTab("Payslip", new PayslipPanel(employeeRepository, payrollService));
        add(tabs, BorderLayout.CENTER);
    }
}
