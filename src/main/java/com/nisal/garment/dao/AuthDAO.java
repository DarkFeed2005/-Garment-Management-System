package com.nisal.garment.dao;

import com.nisal.garment.config.DB;
import com.nisal.garment.model.SessionUser;
import com.nisal.garment.util.PasswordUtil;
import java.sql.*;

public class AuthDAO {
    public SessionUser login(String username, String password) throws SQLException {
        String sql = "SELECT user_id,username,full_name,email,role FROM users " +
                     "WHERE username=? AND password_hash=? AND active=1";
        try (Connection c = DB.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, PasswordUtil.sha256(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new SessionUser(
                    rs.getInt("user_id"), rs.getString("username"),
                    rs.getString("full_name"), rs.getString("email"),
                    rs.getString("role"));
            }
        }
        return null;
    }
}
