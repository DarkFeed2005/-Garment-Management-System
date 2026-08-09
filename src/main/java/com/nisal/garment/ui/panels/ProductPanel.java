package com.nisal.garment.ui.panels;

import com.nisal.garment.dao.CrudDAO;
import com.nisal.garment.model.SessionUser;
import com.nisal.garment.ui.components.Ui;
import com.nisal.garment.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductPanel extends JPanel {
    private final SessionUser user;
    private final CrudDAO dao = new CrudDAO();
    private final JTable table = new JTable();

    public ProductPanel(SessionUser user) {
        this.user = user;
        setLayout(new BorderLayout(16,16));
        setBorder(new EmptyBorder(22,28,28,28));
        setBackground(Theme.BG);

        JButton refresh = Ui.outlineButton("↻  Refresh");
        refresh.addActionListener(e -> load());

        add(Ui.sectionHeader("Products", "View and monitor products.", refresh), BorderLayout.NORTH);

        JPanel card = Ui.card();
        card.setBorder(new EmptyBorder(10,10,10,10));
        Ui.styleTable(table);
        card.add(new JScrollPane(table), BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);
        load();
    }

    private void load() {
        try {
            var rows = dao.select("SELECT product_id,product_code,name,category,size,unit_price,stock_qty,status FROM products");
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Code", "Name", "Category", "Size", "Unit Price", "Stock", "Status"}, 0) {
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
