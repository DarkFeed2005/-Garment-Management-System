package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import javax.swing.*;
import java.util.List;

public class UserPanel extends CrudPanel {
    private final int currentUserId;

    public UserPanel(SessionUser user) {
        super(user, "User Management", "Create and manage system accounts.",
                new String[]{"ID", "Username", "Full Name", "Email", "Role", "Active"},
                List.of(
                        new FieldSpec("Username", TEXT, 1),
                        new FieldSpec("Password", PASSWORD, -1, true, true),
                        new FieldSpec("Full Name", TEXT, 2),
                        new FieldSpec("Email", TEXT, 3),
                        new FieldSpec("Role", STATIC, 4, new String[]{"ADMIN", "PRODUCT_MANAGER", "STAFF", "CUSTOMER"}, true),
                        new FieldSpec("Active", BOOL, 5)
                ));
        this.currentUserId = user.id();
    }

    @Override protected String selectSql() {
        return "SELECT user_id,username,full_name,email,role,active FROM users ORDER BY user_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO users(username,password_hash,full_name,email,role,active) VALUES(?,?,?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE users SET username=?,full_name=?,email=?,role=?,active=? WHERE user_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM users WHERE user_id=?";
    }

    @Override protected boolean beforeDelete(Object pk) {
        if (pk instanceof Number && ((Number) pk).intValue() == currentUserId) {
            JOptionPane.showMessageDialog(this,
                    "You cannot delete the account you are currently signed in with.",
                    "Delete", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
