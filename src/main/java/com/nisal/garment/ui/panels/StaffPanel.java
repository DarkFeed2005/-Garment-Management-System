package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class StaffPanel extends CrudPanel {
    public StaffPanel(SessionUser user) {
        super(user, "Staff", "Manage factory staff.",
                new String[]{"ID", "Name", "Phone", "Department", "Position"},
                List.of(
                        new FieldSpec("Name", TEXT, 1),
                        new FieldSpec("Phone", TEXT, 2),
                        new FieldSpec("Department", TEXT, 3),
                        new FieldSpec("Position", TEXT, 4)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT staff_id,name,phone,department,position FROM staff ORDER BY staff_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO staff(name,phone,department,position) VALUES(?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE staff SET name=?,phone=?,department=?,position=? WHERE staff_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM staff WHERE staff_id=?";
    }
}
