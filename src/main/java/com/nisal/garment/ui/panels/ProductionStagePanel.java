package com.nisal.garment.ui.panels;

import com.nisal.garment.model.SessionUser;

import java.util.List;

public class ProductionStagePanel extends CrudPanel {
    public ProductionStagePanel(SessionUser user) {
        super(user, "Production Stages", "Track cutting, sewing and QC stages.",
                new String[]{"ID", "Production", "Stage", "Status", "Started", "Completed"},
                List.of(
                        new FieldSpec("Production Order", COMBO, 1, "SELECT production_order_id, CONCAT('PO #', production_order_id) FROM production_orders ORDER BY production_order_id", true),
                        new FieldSpec("Stage Name", TEXT, 2),
                        new FieldSpec("Stage Status", TEXT, 3),
                        new FieldSpec("Started At", DATETIME, 4, false),
                        new FieldSpec("Completed At", DATETIME, 5, false)
                ));
    }

    @Override protected String selectSql() {
        return "SELECT stage_id,production_order_id,stage_name,stage_status,started_at,completed_at FROM production_stages ORDER BY stage_id";
    }

    @Override protected String insertSql() {
        return "INSERT INTO production_stages(production_order_id,stage_name,stage_status,started_at,completed_at) VALUES(?,?,?,?,?)";
    }

    @Override protected String updateSql() {
        return "UPDATE production_stages SET production_order_id=?,stage_name=?,stage_status=?,started_at=?,completed_at=? WHERE stage_id=?";
    }

    @Override protected String deleteSql() {
        return "DELETE FROM production_stages WHERE stage_id=?";
    }
}
