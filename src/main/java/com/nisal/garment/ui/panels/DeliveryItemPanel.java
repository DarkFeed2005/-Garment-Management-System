package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class DeliveryItemPanel extends CrudPanel {
    public DeliveryItemPanel(SessionUser user) {
        super(user, "Delivery Items", "Manage items in each delivery.",
                new String[]{"ID", "Delivery", "Product", "Quantity"},
                List.of(
                        new FieldSpec("Delivery", COMBO, 1, "SELECT delivery_id, CONCAT('Delivery #', delivery_id) FROM deliveries ORDER BY delivery_id", true),
                        new FieldSpec("Product", COMBO, 2, "SELECT product_id, name FROM products ORDER BY name", true),
                        new FieldSpec("Quantity", INT, 3)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT delivery_item_id,delivery_id,product_id,quantity FROM delivery_items ORDER BY delivery_item_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO delivery_items(delivery_id,product_id,quantity) VALUES(?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE delivery_items SET delivery_id=?,product_id=?,quantity=? WHERE delivery_item_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM delivery_items WHERE delivery_item_id=?";
    }
}
