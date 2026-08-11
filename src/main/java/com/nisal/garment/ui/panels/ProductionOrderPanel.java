package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class ProductionOrderPanel extends CrudPanel {
    public ProductionOrderPanel(SessionUser user) {
        super(user, "Production Orders", "Assign orders to production.",
                new String[]{"ID", "Order", "Staff", "Start", "Expected", "Status"},
                List.of(
                        new FieldSpec("Order", COMBO, 1, "SELECT order_id, CONCAT('Order #', order_id) FROM orders ORDER BY order_id", true),
                        new FieldSpec("Assigned Staff", COMBO, 2, "SELECT staff_id, name FROM staff ORDER BY name", false),
                        new FieldSpec("Start Date", DATE, 3, false),
                        new FieldSpec("Expected Date", DATE, 4, false),
                        new FieldSpec("Status", TEXT, 5)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT production_order_id,order_id,assigned_staff_id,start_date,expected_date,status FROM production_orders ORDER BY production_order_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO production_orders(order_id,assigned_staff_id,start_date,expected_date,status) VALUES(?,?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE production_orders SET order_id=?,assigned_staff_id=?,start_date=?,expected_date=?,status=? WHERE production_order_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM production_orders WHERE production_order_id=?";
    }
}
