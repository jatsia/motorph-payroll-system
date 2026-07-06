USE motorph_payroll;

INSERT IGNORE INTO departments (department_id, name) VALUES
    (1, 'Leadership'),
    (2, 'IT'),
    (3, 'HR'),
    (4, 'Accounting'),
    (5, 'Marketing');

INSERT IGNORE INTO employment_statuses (status_id, name) VALUES
    (1, 'Regular'),
    (2, 'Probationary');

INSERT IGNORE INTO positions (position_id, department_id, title) VALUES
    (1, 1, 'Chief Executive Officer'),
    (2, 2, 'IT Operations and Systems'),
    (3, 3, 'HR Manager'),
    (4, 4, 'Account Manager'),
    (5, 5, 'Customer Service and Relations');

INSERT IGNORE INTO employees (
    employee_id,
    last_name,
    first_name,
    birthday,
    address,
    phone_number,
    status_id,
    position_id,
    supervisor_id
) VALUES
    (10001, 'Garcia', 'Manuel III', '1983-10-11', 'Valero Carpark Building Valero Street, Makati City', '966-860-270', 1, 1, NULL),
    (10005, 'Hernandez', 'Eduard', '1989-09-23', 'National Highway, Gingoog, Misamis Occidental', '088-861-012', 1, 2, 10001),
    (10015, 'Romualdez', 'Fredrick', '1985-03-10', '22A/52 Lubowitz Meadows, Pililla, Zambales', '023-079-009', 1, 4, 10001),
    (10034, 'Santos', 'Beatriz', '1990-08-07', 'Agapita Building, Metro Manila', '526-639-511', 1, 5, 10001);

INSERT IGNORE INTO government_ids (
    employee_id,
    sss_number,
    philhealth_number,
    tin_number,
    pagibig_number
) VALUES
    (10001, '44-4506057-3', '820126853951', '442-605-657-000', '691295330870'),
    (10005, '50-5577638-1', '957436191812', '031-702-374-000', '952347222457'),
    (10015, '26-8768374-1', '308366860059', '598-065-761-000', '223057707853'),
    (10034, '20-2987501-5', '918460050077', '911-529-713-000', '874042259378');

INSERT IGNORE INTO compensation (
    employee_id,
    basic_salary,
    rice_subsidy,
    phone_allowance,
    clothing_allowance,
    gross_semi_monthly_rate,
    hourly_rate
) VALUES
    (10001, 90000.00, 1500.00, 2000.00, 1000.00, 45000.00, 535.71),
    (10005, 52670.00, 1500.00, 1000.00, 1000.00, 26335.00, 313.51),
    (10015, 53500.00, 1500.00, 1000.00, 1000.00, 26750.00, 318.45),
    (10034, 52670.00, 1500.00, 1000.00, 1000.00, 26335.00, 313.51);

INSERT IGNORE INTO users (user_id, employee_id, username, password, role) VALUES
    (1, NULL, 'admin', 'admin123', 'Admin');

INSERT IGNORE INTO attendance (
    employee_id,
    work_date,
    day_name,
    time_in,
    break_out,
    break_in,
    time_out,
    hours_worked,
    overtime_hours,
    remarks
) VALUES
    (10001, '2023-12-01', 'Fri', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10001, '2023-12-04', 'Mon', '08:10 AM', '12:00 PM', '01:00 PM', '05:10 PM', 8, 0.25, 'Late In'),
    (10001, '2023-12-05', 'Tue', '08:00 AM', '12:00 PM', '01:00 PM', '06:00 PM', 8, 1, 'Overtime'),
    (10005, '2023-12-01', 'Fri', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10005, '2023-12-04', 'Mon', '08:00 AM', '12:00 PM', '01:00 PM', '05:30 PM', 8, 0.5, 'Overtime'),
    (10005, '2023-12-05', 'Tue', NULL, NULL, NULL, NULL, 0, 0, 'Leave'),
    (10015, '2023-12-01', 'Fri', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-02', 'Sat', NULL, NULL, NULL, NULL, 0, 0, 'Weekend'),
    (10015, '2023-12-03', 'Sun', NULL, NULL, NULL, NULL, 0, 0, 'Weekend'),
    (10015, '2023-12-04', 'Mon', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-05', 'Tue', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-06', 'Wed', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-07', 'Thu', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-08', 'Fri', NULL, NULL, NULL, NULL, 0, 0, 'Holiday (Feast of Immaculate Conception)'),
    (10015, '2023-12-11', 'Mon', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-12', 'Tue', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-13', 'Wed', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-14', 'Thu', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-15', 'Fri', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-18', 'Mon', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-19', 'Tue', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-20', 'Wed', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-21', 'Thu', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-22', 'Fri', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-25', 'Mon', NULL, NULL, NULL, NULL, 0, 0, 'Holiday (Christmas)'),
    (10015, '2023-12-26', 'Tue', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-27', 'Wed', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-28', 'Thu', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-29', 'Fri', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10015, '2023-12-30', 'Sat', NULL, NULL, NULL, NULL, 0, 0, 'Holiday (Rizal Day)'),
    (10015, '2023-12-31', 'Sun', NULL, NULL, NULL, NULL, 0, 0, 'Weekend'),
    (10034, '2023-12-01', 'Fri', '08:15 AM', '12:00 PM', '01:00 PM', '05:00 PM', 7.75, 0, 'Late In'),
    (10034, '2023-12-04', 'Mon', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present'),
    (10034, '2023-12-05', 'Tue', '08:00 AM', '12:00 PM', '01:00 PM', '05:00 PM', 8, 0, 'Present');

INSERT INTO leave_requests (
    employee_id,
    start_date,
    end_date,
    leave_type,
    reason,
    status
)
SELECT 10005, '2026-01-20', '2026-01-21', 'Vacation Leave', 'Family commitment', 'Pending'
WHERE NOT EXISTS (
    SELECT 1 FROM leave_requests
    WHERE employee_id = 10005
    AND start_date = '2026-01-20'
    AND end_date = '2026-01-21'
);

INSERT INTO leave_requests (
    employee_id,
    start_date,
    end_date,
    leave_type,
    reason,
    status
)
SELECT 10034, '2026-01-24', '2026-01-24', 'Sick Leave', 'Medical appointment', 'Approved'
WHERE NOT EXISTS (
    SELECT 1 FROM leave_requests
    WHERE employee_id = 10034
    AND start_date = '2026-01-24'
    AND end_date = '2026-01-24'
);
