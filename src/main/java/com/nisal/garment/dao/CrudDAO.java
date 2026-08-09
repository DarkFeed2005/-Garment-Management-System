package com.nisal.garment.dao;

import com.nisal.garment.config.DB;
import java.sql.*;
import java.util.*;

public class CrudDAO {
    public List<Object[]> select(String sql) throws SQLException {
        List<Object[]> rows = new ArrayList<>();
        try (Connection c = DB.get(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            ResultSetMetaData m = rs.getMetaData();
            int n = m.getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[n];
                for (int i=0;i<n;i++) row[i]=rs.getObject(i+1);
                rows.add(row);
            }
        }
        return rows;
    }

    public int update(String sql, Object... args) throws SQLException {
        try (Connection c = DB.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i=0;i<args.length;i++) ps.setObject(i+1,args[i]);
            return ps.executeUpdate();
        }
    }
}
