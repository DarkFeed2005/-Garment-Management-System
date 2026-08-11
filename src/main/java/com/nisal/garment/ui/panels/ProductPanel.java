package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class ProductPanel extends CrudPanel {
    public ProductPanel(SessionUser user) {
        super(user, "Products", "Manage the product catalogue.",
                new String[]{"ID", "Code", "Name", "Category", "Size", "Unit Price", "Stock", "Status"},
                List.of(
                        new FieldSpec("Product Code", TEXT, 1),
                        new FieldSpec("Name", TEXT, 2),
                        new FieldSpec("Category", TEXT, 3),
                        new FieldSpec("Size", TEXT, 4),
                        new FieldSpec("Unit Price", DECIMAL, 5),
                        new FieldSpec("Stock Qty", INT, 6),
                        new FieldSpec("Status", TEXT, 7)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT product_id,product_code,name,category,size,unit_price,stock_qty,status FROM products ORDER BY product_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO products(product_code,name,category,size,unit_price,stock_qty,status) VALUES(?,?,?,?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE products SET product_code=?,name=?,category=?,size=?,unit_price=?,stock_qty=?,status=? WHERE product_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM products WHERE product_id=?";
    }
}
