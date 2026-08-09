package com.nisal.garment.ui.panels;

import com.nisal.garment.dao.CrudDAO;
import com.nisal.garment.model.SessionUser;
import com.nisal.garment.ui.components.Ui;
import com.nisal.garment.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductionOrderPanel extends JPanel {
    private final SessionUser user;
    private final CrudDAO dao = new CrudDAO();
    private final JTable table = new JTable();

    public ProductionOrderPanel(SessionUser user) {
        this.user = user;
        setLayout(new BorderLayout(16,16));
        setBorder(new EmptyBorder(22,28,28,28));
        setBackground(Theme.BG);

        JButton refresh = Ui.outlineButton("↻  Refresh");
        refresh.addActionListener(e -> load());

        add(Ui.sectionHeader("Production Orders", "View and monitor production orders.", refresh), BorderLayout.NORTH);

        JPanel card = Ui.card();
        card.setBorder(new EmptyBorder(10,10,10,10));
        Ui.styleTable(table);
        card.add(new JScrollPane(table), BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);
        load();
    }

    private void load() {
        try {
            var rows = dao.select("SELECT production_order_id,order_id,assigned_staff_id,start_date,expected_date,status FROM production_orders");
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Order", "Staff", "Start", "Expected", "Status"}, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            for (Object[] row : rows) model.addRow(row);
            table.setModel(model);
            Ui.styleTable(table);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error:\n" + ex.getMessage(),
                    "Database", JOptionPane.ERROR_MESSAGE);
        }
    }
}
