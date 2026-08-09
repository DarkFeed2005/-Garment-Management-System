# Garment Production & Order Management System - Modern UI Edition

## What was changed

This version keeps the existing MySQL/DAO/security structure and replaces the plain Swing presentation with a modern desktop UI:

- Modern two-column login screen
- Branded garment-management look
- Rounded cards and buttons
- Styled input fields
- Modern registration screen
- Professional dashboard with sidebar navigation
- Role-aware menu visibility
- Dashboard summary cards
- Styled data tables
- Consistent colors, typography and spacing
- Fixed invalid `catch(...)` declarations in the generated panel classes

## Project structure

```text
GarmentProductionOrderSystem/
├── pom.xml
├── README.md
├── database/
│   └── schema.sql
└── src/main/
    ├── java/com/nisal/garment/
    │   ├── Main.java
    │   ├── config/
    │   ├── dao/
    │   ├── model/
    │   ├── security/
    │   ├── util/
    │   └── ui/
    │       ├── LoginFrame.java
    │       ├── RegisterFrame.java
    │       ├── DashboardFrame.java
    │       ├── components/
    │       └── panels/
    └── resources/
        └── db.properties
```

## MySQL

Run `database/schema.sql` in MySQL Workbench.

Then update:

```properties
db.user=root
db.password=YOUR_MYSQL_PASSWORD
```

inside `src/main/resources/db.properties`.

## Run

From the project root:

```bash
mvn clean compile
mvn exec:java
```

## Demo accounts

- Admin: `admin` / `admin123`
- Product Manager: `manager` / `manager123`
- Staff: `staff` / `staff123`
- Customer: `customer` / `customer123`

## Important

The UI is implemented using standard Java Swing only. No extra UI framework is required.
