# 🚀 Garment Production & Order Management System - Enterprise Manufacturing Platform

> A modern, ultra-fast desktop application built natively in Java (Swing) that seamlessly consolidates customer order lifecycle management, apparel production stage tracking, real-time inventory control, and dispatch logistics into a lightweight, unified manufacturing workspace.
> 
<p align="center">
  <img src="https://skillicons.dev/icons?i=java" width="50"/>
  <img src="https://skillicons.dev/icons?i=maven" width="50"/>
  <img src="https://skillicons.dev/icons?i=mysql" width="50"/>
  <img src="https://skillicons.dev/icons?i=git" width="50"/>
</p>

---

## 🌟 Overview

**Garment Production & Order Management System** is an enterprise-grade, self-hosted apparel manufacturing and order management platform built to eliminate tool fragmentation for garment manufacturing teams.

Operating as a standalone desktop application, the system provides a unified workspace where **Product Managers, Factory Staff, and Customers** can manage and monitor the complete garment production lifecycle under one application.

By bypassing resource-heavy Web/Electron containers in favor of native **Java Swing powered by FlatLaf**, the system delivers a lightweight desktop experience with efficient database-driven operations.

### Why Garment Production System?

* **Unified Workspace:** Replaces disconnected spreadsheets, inventory tools, order tracking systems, and delivery records in one application.
* **End-to-End Production Tracking:** Provides visibility across *Fabric Cutting, Sewing, Quality Inspection,* and *Packing* stages.
* **Role-Tailored Workspaces:** Dedicated dashboards and permissions for Product Managers, Staff, and Customers.
* **Automated Data Management:** Uses MySQL relational architecture with foreign keys, triggers, generated columns, and automated calculations.
* **Modern Desktop UI:** Clean two-column layouts, dashboard metric cards, sidebar navigation, interactive tables, and FlatLaf themes.

---

## ✨ Features

### 🎯 Core Features

* Role-Based Access Control
* Customer Registration & Management
* Customer Order Lifecycle Management
* Automatic Order Subtotal Calculation
* Multi-Stage Garment Production Tracking
* Staff Task Assignments
* Product Inventory Management
* Automatic Low-Stock & Reorder Alerts
* Delivery Dispatch Management
* Delivery Tracking
* Order Status Monitoring
* Production Progress Monitoring

### 📦 Production & Inventory

* Fabric Cutting Stage Tracking
* Sewing Stage Tracking
* Quality Inspection Tracking
* Packing Stage Tracking
* Staff Assignment to Production Tasks
* Real-Time Inventory Updates
* Stock-Level Monitoring
* Automatic Reorder Alerts
* Product and Material Management

### 🎨 UI/UX

* FlatLaf Modern Look and Feel
* Dark and Light Theme Support
* Responsive Two-Column Authentication
* Interactive `JTable` Data Tables
* Table Sorting and Filtering
* Dashboard Summary Metric Cards
* Sidebar Navigation
* Role-Specific Dashboards
* Clean Enterprise Desktop Interface

### 🔧 Technical

* Modular MVC Architecture
* DAO-Based Database Access Layer
* Thread-Safe Swing Event Dispatch Thread (EDT) Architecture
* JDBC Database Connectivity
* Prepared Statements for Database Operations
* MySQL Relational Database
* Foreign Key Constraints
* Database Triggers
* Virtual Generated Columns
* SHA-256 Password Hashing
* Property-Based Database Configuration
* Maven Build Management

---

## 🎥 Demo

### Release Downloads

> Add your GitHub Releases link here.

---

## 🛠 Technologies Used

### Desktop UI

* Java 17+
* Swing
* FlatLaf
* `JTable`
* Java AWT/Swing Components

### Backend

* Java Database Connectivity (JDBC)
* Java Security
* SHA-256 Password Hashing
* DAO Architecture
* MVC Architecture

### Storage & Build

* MySQL 8+
* MariaDB
* XAMPP
* Apache Maven

---

## 📦 Installation

Clone the repository:

```bash
git clone https://github.com/DarkFeed2005/Garment-Management-System.git
cd Garment-Management-System
```

Import the database schema and seed data into MySQL or phpMyAdmin:

```bash
mysql -u root -p < database/schema.sql
```

Update your database configuration in:

```text
src/main/resources/db.properties
```

Example:

```properties
db.url=jdbc:mysql://localhost:3306/garment_management?serverTimezone=UTC
db.user=your_username
db.password=your_password
```

Compile the application:

```bash
mvn clean compile
```

Run the application:

```bash
mvn exec:java -Dexec.mainClass="com.nisal.garment.Main"
```

---

## 🚀 Usage

1. Launch the application.
2. Sign in using a configured account.
3. Access the dashboard according to your assigned role.
4. Manage customer orders and order details.
5. Track active garment production stages.
6. Assign production tasks to factory staff.
7. Monitor product and material inventory.
8. Review low-stock and reorder alerts.
9. Manage delivery dispatches and tracking information.
10. Monitor the complete order lifecycle from production to delivery.

### Demo Accounts

| Role            | Username    | Password      |
| --------------- | ----------- | ------------- |
| Product Manager | `manager1`  | `password123` |
| Staff           | `staff1`    | `password123` |
| Customer        | `customer1` | `password123` |

> **Security Note:** These credentials are intended only for development/demo environments. Change or remove seed passwords before deploying the application in a production environment.

---

## 📁 Project Structure

```text
Garment-Management-System/
├── database/
│   └── schema.sql
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── nisal/
│   │   │           └── garment/
│   │   │               ├── Main.java
│   │   │               ├── config/
│   │   │               ├── dao/
│   │   │               ├── model/
│   │   │               ├── security/
│   │   │               ├── util/
│   │   │               └── ui/
│   │   │                   ├── LoginFrame.java
│   │   │                   ├── RegisterFrame.java
│   │   │                   ├── DashboardFrame.java
│   │   │                   ├── components/
│   │   │                   └── panels/
│   │   └── resources/
│   │       └── db.properties
│   └── test/
├── pom.xml
└── README.md
```

---

## 🤝 Contributing

Create a new feature branch:

```bash
git checkout -b feature/AmazingFeature
```

Commit your changes:

```bash
git commit -m "Add AmazingFeature"
```

Push the branch:

```bash
git push origin feature/AmazingFeature
```

### Contribution Guidelines

* Follow standard Java coding conventions.
* Keep Swing UI updates on the Event Dispatch Thread (EDT).
* Use prepared statements for all database operations.
* Follow the existing MVC and DAO architecture.
* Add or update tests when introducing new functionality.
* Update database scripts when modifying the schema.
* Keep documentation synchronized with application changes.
* Avoid committing sensitive database credentials.

---

## 👨‍💻 Author

* **GitHub:** https://github.com/darkfeed2005
* **LinkedIn:** https://www.linkedin.com/in/kalana-yasassri-684591251/
* **Instagram:** https://www.instagram.com/kalana_yasassri
