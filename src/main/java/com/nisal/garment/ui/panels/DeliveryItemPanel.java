package com.nisal.garment.ui.panels;

import com.nisal.garment.dao.CrudDAO;
import com.nisal.garment.model.SessionUser;
import com.nisal.garment.ui.components.Ui;
import com.nisal.garment.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DeliveryItemPanel extends JPanel {
    private final SessionUser user;
    private final CrudDAO dao = new CrudDAO();
    private final JTable table = new JTable();

    public DeliveryItemPanel(SessionUser user) {
        this.user = user;
        setLayout(new BorderLayout(16,16));
        setBorder(new EmptyBorder(22,28,28,28));
        setBackground(Theme.BG);

        JButton refresh = Ui.outlineButton("↻  Refresh");
        refresh.addActionListener(e -> load());

        add(Ui.sectionHeader("Delivery Items", "View and monitor delivery items.", refresh), BorderLayout.NORTH);

        JPanel card = Ui.card();
        card.setBorder(new EmptyBorder(10,10,10,10));
        Ui.styleTable(table);
        card.add(new JScrollPane(table), BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);
        load();
    }

    private void load() {
        try {
            var rows = dao.select("SELECT delivery_item_id,delivery_id,product_id,quantity FROM delivery_items");
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Delivery", "Product", "Quantity"}, 0) {
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
