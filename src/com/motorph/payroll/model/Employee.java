package com.motorph.payroll.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private EmploymentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    @OneToOne(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Compensation compensation;

    protected Employee() {
    }

    public Employee(int employeeId, String lastName, String firstName, String birthday, String address, String phoneNumber, EmploymentStatus status, Position position, Employee supervisor) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.position = position;
        this.supervisor = supervisor;
    }

    @Transient
    public String getFullName() {
        return lastName + ", " + firstName;
    }

    @Transient
    public String getPosition() {
        return position.getTitle();
    }

    @Transient
    public String getDepartment() {
        return position.getDepartment().getName();
    }

    @Transient
    public double getBasicSalary() {
        return compensation.getBasicSalary();
    }

    @Transient
    public double getGrossSemiMonthlyRate() {
        return compensation.getGrossSemiMonthlyRate();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public EmploymentStatus getStatus() {
        return status;
    }

    public Position getPositionEntity() {
        return position;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public Compensation getCompensation() {
        return compensation;
    }

    void setCompensation(Compensation compensation) {
        this.compensation = compensation;
    }
}
