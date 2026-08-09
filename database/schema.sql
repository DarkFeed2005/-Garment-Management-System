CREATE DATABASE IF NOT EXISTS garment_production;
USE garment_production;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    role ENUM('ADMIN','PRODUCT_MANAGER','STAFF','CUSTOMER') NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(30),
    email VARCHAR(120),
    address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_code VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    category VARCHAR(80),
    size VARCHAR(30),
    unit_price DECIMAL(12,2) NOT NULL DEFAULT 0,
    stock_qty INT NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS staff (
    staff_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(30),
    department VARCHAR(80),
    position VARCHAR(80),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    order_date DATE NOT NULL,
    due_date DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE IF NOT EXISTS order_details (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE IF NOT EXISTS production_orders (
    production_order_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    assigned_staff_id INT NULL,
    start_date DATE,
    expected_date DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'PLANNED',
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (assigned_staff_id) REFERENCES staff(staff_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS production_stages (
    stage_id INT PRIMARY KEY AUTO_INCREMENT,
    production_order_id INT NOT NULL,
    stage_name VARCHAR(80) NOT NULL,
    stage_status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    started_at DATETIME NULL,
    completed_at DATETIME NULL,
    FOREIGN KEY (production_order_id) REFERENCES production_orders(production_order_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS deliveries (
    delivery_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    delivery_date DATE,
    address VARCHAR(255),
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

CREATE TABLE IF NOT EXISTS delivery_items (
    delivery_item_id INT PRIMARY KEY AUTO_INCREMENT,
    delivery_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (delivery_id) REFERENCES deliveries(delivery_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

INSERT INTO users(username,password_hash,role,full_name,email)
VALUES
('admin', SHA2('admin123',256), 'ADMIN', 'System Administrator', 'admin@example.com'),
('manager', SHA2('manager123',256), 'PRODUCT_MANAGER', 'Product Manager', 'manager@example.com'),
('staff', SHA2('staff123',256), 'STAFF', 'Production Staff', 'staff@example.com'),
('customer', SHA2('customer123',256), 'CUSTOMER', 'Demo Customer', 'customer@example.com')
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO customers(user_id,name,phone,email,address)
SELECT user_id,full_name,'0771234567',email,'Colombo'
FROM users WHERE username='customer'
AND NOT EXISTS (SELECT 1 FROM customers c WHERE c.user_id=users.user_id);

INSERT INTO products(product_code,name,category,size,unit_price,stock_qty)
VALUES
('TS-001','Classic T-Shirt','T-Shirt','M',2500.00,100),
('SH-001','Formal Shirt','Shirt','L',4500.00,60),
('JP-001','Denim Jacket','Jacket','M',8500.00,25)
ON DUPLICATE KEY UPDATE product_code=product_code;
