# MotorPH Payroll System

MotorPH payroll system in Java Swing with layered architecture and MySQL persistence.

## Prerequisites

- Java JDK 21+
- Maven 3.9+
- MySQL running locally
- NetBeans, IntelliJ, or VS Code

## Database

The app uses MySQL through Hibernate.

```text
database: motorph_payroll
username: root
password: empty
```

Files:

- Hibernate config: `resources/hibernate.cfg.xml`
- Schema script: `database/schema.sql`
- Seed script: `database/seed.sql`
- Java seeder: `src/com/motorph/payroll/database/DataSeeder.java`

Create and seed manually if needed:

```sh
mysql -u root < database/schema.sql
mysql -u root < database/seed.sql
```

The app also runs `DataSeeder` on startup, so sample data is inserted automatically when missing.

## Current Features

- Login
- Employee CRUD
- Timecard entry and export
- Leave requests
- Payroll summary
- Payslip preview and export

## Reports

Reports are based on the provided MotorPH sample PDFs:

- employee details
- monthly payroll summary
- employee payslip

## Run

From terminal:

```sh
mvn exec:java
```

Run internal tests:

```sh
mvn test
```

## Setup In NetBeans

1. Open NetBeans.
2. Choose `File > Open Project`.
3. Select this folder.
4. Let NetBeans load the Maven project.
5. Run `com.motorph.payroll.Main`.

## Login

```text
username: admin
password: admin123
```

## How To Use

- `Employees`: select a row to edit, then save or delete.
- `Timecard`: select an employee, add or edit attendance, save, export pdf.
- `Leave Requests`: submit, approve, or reject leave requests.
- `Payroll Summary`: generate the report from saved employee data and export pdf.
- `Payslip`: select an employee, view the payslip, export pdf, print.

The employee dropdowns in `Timecard` and `Payslip` refresh the shown records automatically.

## Handoff Files

- `client-handoff-summary.xlsx`: project scope, setup, and test handoff notes.
- `unit-test-planning-summary.xlsx`: unit test planning sheet for the MotorPH submission.
