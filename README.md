# MotorPH Payroll System

Simple Java Swing payroll system for the MotorPH case study.

## Prerequisites

- Java JDK 21+
- Maven 3.9+
- MySQL running locally
- NetBeans, IntelliJ, or VS Code

## Database

The app uses MySQL through Hibernate.

- Hibernate config: `resources/hibernate.cfg.xml`
- Schema script: `database/schema.sql`
- Seed script: `database/seed.sql`
- Java seeder: `src/com/motorph/payroll/database/DataSeeder.java`

Default local database:

```text
database: motorph_payroll
username: root
password: empty
```

Create and seed manually if needed:

```sh
mysql -u root < database/schema.sql
mysql -u root < database/seed.sql
```

The app also runs `DataSeeder` on startup, so missing sample data is inserted automatically.

## Payroll Notes

Payslips show a simple deduction breakdown for demo purposes:

- SSS
- PhilHealth
- Pag-IBIG
- Withholding Tax

The payroll summary displays the total deductions and net pay.

## Run

From terminal:

```sh
mvn exec:java
```

Run internal tests:

```sh
mvn test
```

## NetBeans

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

- `Employees`: view employee list and details.
- `Timecard`: select employee, add/edit attendance, save record, export PDF.
- `Leave Requests`: submit leave requests and approve or reject selected requests.
- `Payroll Summary`: generate summary from persisted employee data, export PDF.
- `Payslip`: select employee, view payslip, export PDF, print.

Employee dropdowns in `Timecard` and `Payslip` update the displayed records automatically.
