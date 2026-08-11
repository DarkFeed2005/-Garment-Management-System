package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class CustomerPanel extends CrudPanel {
    public CustomerPanel(SessionUser user) {
        super(user, "Customers", "Manage customer records.",
                new String[]{"Customer ID", "Name", "Phone", "Email", "Address"},
                List.of(
                        new FieldSpec("Name", TEXT, 1),
                        new FieldSpec("Phone", TEXT, 2),
                        new FieldSpec("Email", TEXT, 3),
                        new FieldSpec("Address", TEXT, 4)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT customer_id,name,phone,email,address FROM customers ORDER BY customer_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO customers(name,phone,email,address) VALUES(?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE customers SET name=?,phone=?,email=?,address=? WHERE customer_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM customers WHERE customer_id=?";
    }
}
