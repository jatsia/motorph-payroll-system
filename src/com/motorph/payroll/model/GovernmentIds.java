package com.motorph.payroll.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "government_ids")
public class GovernmentIds {
    @Id
    @Column(name = "employee_id")
    private int employeeId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "sss_number", nullable = false)
    private String sssNumber;

    @Column(name = "philhealth_number", nullable = false)
    private String philhealthNumber;

    @Column(name = "tin_number", nullable = false)
    private String tinNumber;

    @Column(name = "pagibig_number", nullable = false)
    private String pagibigNumber;

    protected GovernmentIds() {
    }

    public GovernmentIds(Employee employee, String sssNumber, String philhealthNumber, String tinNumber, String pagibigNumber) {
        this.employee = employee;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.tinNumber = tinNumber;
        this.pagibigNumber = pagibigNumber;
    }
}

