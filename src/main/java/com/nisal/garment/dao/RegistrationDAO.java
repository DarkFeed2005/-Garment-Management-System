package com.nisal.garment.dao;

import com.nisal.garment.config.DB;
import com.nisal.garment.util.PasswordUtil;
import java.sql.*;

public class RegistrationDAO {
    public void registerCustomer(String username, String password, String name, String email, String phone, String address)
            throws SQLException {
        String u = "INSERT INTO users(username,password_hash,role,full_name,email) VALUES(?,?, 'CUSTOMER',?,?)";
        String c = "INSERT INTO customers(user_id,name,phone,email,address) VALUES(?,?,?,?,?)";
        try (Connection con = DB.get()) {
            con.setAutoCommit(false);
            try {
                int userId;
                try (PreparedStatement ps = con.prepareStatement(u, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, username);
                    ps.setString(2, PasswordUtil.sha256(password));
                    ps.setString(3, name);
                    ps.setString(4, email);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("Could not create user");
                        userId = rs.getInt(1);
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(c)) {
                    ps.setInt(1, userId); ps.setString(2, name); ps.setString(3, phone);
                    ps.setString(4, email); ps.setString(5, address); ps.executeUpdate();
                }
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }
        }
    }
}
