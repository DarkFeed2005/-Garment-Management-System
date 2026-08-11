package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class OrderDetailPanel extends CrudPanel {
    public OrderDetailPanel(SessionUser user) {
        super(user, "Order Details", "Manage line items on orders.",
                new String[]{"ID", "Order", "Product", "Quantity", "Unit Price"},
                List.of(
                        new FieldSpec("Order", COMBO, 1, "SELECT order_id, CONCAT('Order #', order_id) FROM orders ORDER BY order_id", true),
                        new FieldSpec("Product", COMBO, 2, "SELECT product_id, name FROM products ORDER BY name", true),
                        new FieldSpec("Quantity", INT, 3),
                        new FieldSpec("Unit Price", DECIMAL, 4)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT order_detail_id,order_id,product_id,quantity,unit_price FROM order_details ORDER BY order_detail_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO order_details(order_id,product_id,quantity,unit_price) VALUES(?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE order_details SET order_id=?,product_id=?,quantity=?,unit_price=? WHERE order_detail_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM order_details WHERE order_detail_id=?";
    }
}
