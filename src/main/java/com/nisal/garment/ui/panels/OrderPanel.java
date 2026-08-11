package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class OrderPanel extends CrudPanel {
    public OrderPanel(SessionUser user) {
        super(user, "Orders", "Manage customer orders.",
                new String[]{"ID", "Customer", "Order Date", "Due Date", "Status", "Total"},
                List.of(
                        new FieldSpec("Customer", COMBO, 1, "SELECT customer_id,name FROM customers ORDER BY name", true),
                        new FieldSpec("Order Date", DATE, 2),
                        new FieldSpec("Due Date", DATE, 3, false),
                        new FieldSpec("Status", TEXT, 4),
                        new FieldSpec("Total Amount", DECIMAL, 5)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT order_id,customer_id,order_date,due_date,status,total_amount FROM orders ORDER BY order_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO orders(customer_id,order_date,due_date,status,total_amount) VALUES(?,?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE orders SET customer_id=?,order_date=?,due_date=?,status=?,total_amount=? WHERE order_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM orders WHERE order_id=?";
    }
}
