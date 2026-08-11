package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class DeliveryPanel extends CrudPanel {
    public DeliveryPanel(SessionUser user) {
        super(user, "Deliveries", "Manage order dispatch.",
                new String[]{"ID", "Order", "Date", "Address", "Status"},
                List.of(
                        new FieldSpec("Order", COMBO, 1, "SELECT order_id, CONCAT('Order #', order_id) FROM orders ORDER BY order_id", true),
                        new FieldSpec("Delivery Date", DATE, 2, false),
                        new FieldSpec("Address", TEXT, 3),
                        new FieldSpec("Status", TEXT, 4)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT delivery_id,order_id,delivery_date,address,status FROM deliveries ORDER BY delivery_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO deliveries(order_id,delivery_date,address,status) VALUES(?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE deliveries SET order_id=?,delivery_date=?,address=?,status=? WHERE delivery_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM deliveries WHERE delivery_id=?";
    }
}
