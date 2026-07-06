CREATE DATABASE IF NOT EXISTS motorph_payroll;
USE motorph_payroll;

CREATE TABLE IF NOT EXISTS departments (
    department_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS positions (
    position_id INT PRIMARY KEY,
    department_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    CONSTRAINT fk_positions_department
        FOREIGN KEY (department_id) REFERENCES departments(department_id)
);

CREATE TABLE IF NOT EXISTS employment_statuses (
    status_id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS employees (
    employee_id INT PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    birthday VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    status_id INT NOT NULL,
    position_id INT NOT NULL,
    supervisor_id INT NULL,
    CONSTRAINT fk_employees_status
        FOREIGN KEY (status_id) REFERENCES employment_statuses(status_id),
    CONSTRAINT fk_employees_position
        FOREIGN KEY (position_id) REFERENCES positions(position_id),
    CONSTRAINT fk_employees_supervisor
        FOREIGN KEY (supervisor_id) REFERENCES employees(employee_id)
);

CREATE TABLE IF NOT EXISTS government_ids (
    employee_id INT PRIMARY KEY,
    sss_number VARCHAR(50) NOT NULL,
    philhealth_number VARCHAR(50) NOT NULL,
    tin_number VARCHAR(50) NOT NULL,
    pagibig_number VARCHAR(50) NOT NULL,
    CONSTRAINT fk_government_ids_employee
        FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE IF NOT EXISTS compensation (
    employee_id INT PRIMARY KEY,
    basic_salary DECIMAL(10, 2) NOT NULL,
    rice_subsidy DECIMAL(10, 2) NOT NULL,
    phone_allowance DECIMAL(10, 2) NOT NULL,
    clothing_allowance DECIMAL(10, 2) NOT NULL,
    gross_semi_monthly_rate DECIMAL(10, 2) NOT NULL,
    hourly_rate DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_compensation_employee
        FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE IF NOT EXISTS attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    work_date VARCHAR(20) NOT NULL,
    day_name VARCHAR(10) NOT NULL,
    time_in VARCHAR(20),
    break_out VARCHAR(20),
    break_in VARCHAR(20),
    time_out VARCHAR(20),
    hours_worked DECIMAL(5, 2) NOT NULL DEFAULT 0,
    overtime_hours DECIMAL(5, 2) NOT NULL DEFAULT 0,
    remarks VARCHAR(150) NOT NULL,
    CONSTRAINT uq_attendance_employee_date
        UNIQUE (employee_id, work_date),
    CONSTRAINT fk_attendance_employee
        FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY,
    employee_id INT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_users_employee
        FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
