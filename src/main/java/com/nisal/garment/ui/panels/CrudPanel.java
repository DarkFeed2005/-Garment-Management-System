package com.nisal.garment.ui.panels;

import com.nisal.garment.dao.CrudDAO;
import com.nisal.garment.model.SessionUser;
import com.nisal.garment.ui.components.Ui;
import com.nisal.garment.util.PasswordUtil;
import com.nisal.garment.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CrudPanel extends JPanel {
    public static final int TEXT = 0;
    public static final int INT = 1;
    public static final int DECIMAL = 2;
    public static final int DATE = 3;
    public static final int DATETIME = 4;
    public static final int COMBO = 5;
    public static final int STATIC = 6;
    public static final int PASSWORD = 7;
    public static final int BOOL = 8;

    protected final SessionUser user;
    protected final CrudDAO dao = new CrudDAO();
    protected final JTable table = new JTable();

    private final String pageTitle;
    private final String subtitle;
    private final String[] tableColumns;
    private final List<FieldSpec> fields;

    protected CrudPanel(SessionUser user, String pageTitle, String subtitle,
                        String[] tableColumns, List<FieldSpec> fields) {
        this.user = user;
        this.pageTitle = pageTitle;
        this.subtitle = subtitle;
        this.tableColumns = tableColumns;
        this.fields = fields;
        setLayout(new BorderLayout(16, 16));
        setBorder(new EmptyBorder(22, 28, 28, 28));
        setBackground(Theme.BG);
        build();
        load();
    }

    protected abstract String selectSql();
    protected abstract String insertSql();
    protected abstract String updateSql();
    protected abstract String deleteSql();

    private void build() {
        JButton add = Ui.button("+  Add", Theme.SUCCESS);
        add.addActionListener(e -> newRecord());
        JButton edit = Ui.button("✎  Edit", Theme.BLUE);
        edit.addActionListener(e -> editRecord());
        JButton del = Ui.button("✕  Delete", Theme.DANGER);
        del.addActionListener(e -> deleteRecord());
        JButton refresh = Ui.outlineButton("↻  Refresh");
        refresh.addActionListener(e -> load());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(add);
        actions.add(edit);
        actions.add(del);
        actions.add(refresh);

        add(Ui.sectionHeader(pageTitle, subtitle, actions), BorderLayout.NORTH);

        JPanel card = Ui.card();
        card.setBorder(new EmptyBorder(10, 10, 10, 10));
        Ui.styleTable(table);
        card.add(new JScrollPane(table), BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);
    }

    protected void load() {
        try {
            List<Object[]> rows = dao.select(selectSql());
            DefaultTableModel model = new DefaultTableModel(tableColumns, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };
            for (Object[] row : rows) model.addRow(row);
            table.setModel(model);
            Ui.styleTable(table);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error:\n" + ex.getMessage(),
                    "Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int selectedModelRow() {
        int v = table.getSelectedRow();
        return v < 0 ? -1 : table.convertRowIndexToModel(v);
    }

    protected boolean beforeDelete(Object pk) {
        return true;
    }

    private void newRecord() {
        new FormDialog(pageTitle, null, null).setVisible(true);
    }

    private void editRecord() {
        int r = selectedModelRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to edit.", "Edit", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int cols = table.getModel().getColumnCount();
        Object[] row = new Object[cols];
        for (int i = 0; i < cols; i++) row[i] = table.getModel().getValueAt(r, i);
        Object pk = row[0];
        new FormDialog(pageTitle, row, pk).setVisible(true);
    }

    private void deleteRecord() {
        int r = selectedModelRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.", "Delete", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object pk = table.getModel().getValueAt(r, 0);
        if (!beforeDelete(pk)) return;
        int ans = JOptionPane.showConfirmDialog(this,
                "Delete record " + pk + "?\nThis cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ans != JOptionPane.YES_OPTION) return;
        try {
            int n = dao.update(deleteSql(), pk);
            if (n > 0) {
                load();
            } else {
                JOptionPane.showMessageDialog(this, "No record deleted.", "Delete", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Delete failed:\n" + ex.getMessage(),
                    "Delete", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class FormDialog extends JDialog {
        private final List<JComponent> comps = new ArrayList<>();
        private final Object pk;

        FormDialog(String title, Object[] row, Object pk) {
            super(ownerOf(CrudPanel.this), "Add " + title, ModalityType.APPLICATION_MODAL);
            this.pk = pk;
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(15, 15));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 22, 20, 22));

            JPanel form = new JPanel(new GridBagLayout());
            form.setOpaque(false);
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(6, 6, 6, 6);
            g.fill = GridBagConstraints.HORIZONTAL;
            int y = 0;
            for (FieldSpec f : fields) {
                g.gridx = 0; g.gridy = y; g.weightx = 0;
                form.add(new JLabel(f.label + (f.required ? " *" : "")), g);
                JComponent comp = createField(f);
                g.gridx = 1; g.weightx = 1;
                form.add(comp, g);
                comps.add(comp);
                y++;
            }
            add(form, BorderLayout.CENTER);

            JButton save = Ui.button("Save");
            save.addActionListener(e -> save(pk == null));
            JButton cancel = Ui.outlineButton("Cancel");
            cancel.addActionListener(e -> dispose());
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            btns.setOpaque(false);
            btns.add(cancel);
            btns.add(save);
            add(btns, BorderLayout.SOUTH);

            if (row != null) fillForm(comps, row);
            getRootPane().setDefaultButton(save);
            pack();
            setLocationRelativeTo(ownerOf(CrudPanel.this));
        }

        private static Frame ownerOf(CrudPanel panel) {
            Window w = SwingUtilities.getWindowAncestor(panel);
            return w instanceof Frame ? (Frame) w : null;
        }

        private JComponent createField(FieldSpec f) {
            switch (f.type) {
                case PASSWORD: {
                    JPasswordField pf = Ui.passwordField();
                    pf.setPreferredSize(new Dimension(280, 40));
                    return pf;
                }
                case COMBO:
                case STATIC: {
                    JComboBox<Object> cb = new JComboBox<>();
                    cb.setPreferredSize(new Dimension(280, 40));
                    cb.setRenderer(new DefaultListCellRenderer() {
                        @Override public Component getListCellRendererComponent(JList<?> list, Object value,
                                int index, boolean isSel, boolean hasFocus) {
                            JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSel, hasFocus);
                            l.setText(comboLabel(value));
                            return l;
                        }
                    });
                    if (f.type == STATIC) {
                        if (!f.required) cb.addItem("<None>");
                        for (String o : f.options) cb.addItem(o);
                    } else {
                        if (!f.required) cb.addItem(new Object[]{null, "<None>"});
                        try {
                            for (Object[] r : dao.select(f.comboQuery)) {
                                cb.addItem(new Object[]{r[0], r.length > 1 ? r[1] : r[0]});
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Could not load options:\n" + ex.getMessage());
                        }
                    }
                    return cb;
                }
                case BOOL:
                    return new JCheckBox();
                default: {
                    JTextField tf = Ui.field();
                    tf.setPreferredSize(new Dimension(280, 40));
                    return tf;
                }
            }
        }

        private String comboLabel(Object value) {
            if (value instanceof Object[]) {
                Object d = ((Object[]) value)[1];
                return d == null ? "" : String.valueOf(d);
            }
            return value == null ? "" : String.valueOf(value);
        }

        private Object valueOf(JComponent comp, FieldSpec f) throws Exception {
            if (comp instanceof JComboBox) {
                Object sel = ((JComboBox<?>) comp).getSelectedItem();
                if (sel instanceof Object[]) return ((Object[]) sel)[0];
                return sel;
            }
            if (comp instanceof JCheckBox) return ((JCheckBox) comp).isSelected();
            if (comp instanceof JPasswordField) {
                String p = new String(((JPasswordField) comp).getPassword());
                if (f.required && p.length() < 4) throw new IllegalArgumentException("Password must be at least 4 characters.");
                return PasswordUtil.sha256(p);
            }
            String text = ((JTextField) comp).getText().trim();
            if (f.required && text.isEmpty()) throw new IllegalArgumentException("'" + f.label + "' is required.");
            if (text.isEmpty()) return null;
            switch (f.type) {
                case INT: return Integer.valueOf(text);
                case DECIMAL: return new BigDecimal(text);
                case DATE: return Date.valueOf(LocalDate.parse(text));
                case DATETIME: return Timestamp.valueOf(LocalDateTime.parse(text.replace(' ', 'T')));
                default: return text;
            }
        }

        private void fillForm(List<JComponent> comps, Object[] row) {
            for (int i = 0; i < comps.size(); i++) {
                JComponent comp = comps.get(i);
                FieldSpec f = fields.get(i);
                if (f.col < 0 || f.col >= row.length) continue;
                Object val = row[f.col];
                if (comp instanceof JComboBox) {
                    JComboBox<?> cb = (JComboBox<?>) comp;
                    for (int k = 0; k < cb.getItemCount(); k++) {
                        Object item = cb.getItemAt(k);
                        Object match = item instanceof Object[] ? ((Object[]) item)[0] : item;
                        if (Objects.equals(match, val)) { cb.setSelectedIndex(k); break; }
                    }
                } else if (comp instanceof JCheckBox) {
                    ((JCheckBox) comp).setSelected(Boolean.TRUE.equals(val));
                } else if (comp instanceof JPasswordField) {
                    // do not prefill passwords
                } else if (comp instanceof JTextField) {
                    ((JTextField) comp).setText(format(val));
                }
            }
        }

        private String format(Object val) {
            if (val == null) return "";
            if (val instanceof Timestamp) return ((Timestamp) val).toLocalDateTime().toString().replace('T', ' ');
            if (val instanceof Date) return ((Date) val).toLocalDate().toString();
            if (val instanceof java.util.Date) return val.toString();
            return String.valueOf(val);
        }

        private void save(boolean isInsert) {
            try {
                List<Object> params = new ArrayList<>();
                for (int i = 0; i < comps.size(); i++) {
                    if (!isInsert && fields.get(i).insertOnly) continue;
                    params.add(valueOf(comps.get(i), fields.get(i)));
                }
                if (!isInsert) {
                    if (pk == null) { dispose(); return; }
                    params.add(pk);
                }
                int n = dao.update(isInsert ? insertSql() : updateSql(), params.toArray());
                if (n > 0) {
                    load();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No changes saved.", "Save", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Save failed:\n" + ex.getMessage(),
                        "Save", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected static class FieldSpec {
        final String label;
        final int type;
        final int col;
        final String comboQuery;
        final String[] options;
        final boolean required;
        final boolean insertOnly;

        FieldSpec(String label, int type, int col) { this(label, type, col, null, null, true, false); }
        FieldSpec(String label, int type, int col, boolean required) { this(label, type, col, null, null, required, false); }
        FieldSpec(String label, int type, int col, boolean required, boolean insertOnly) {
            this(label, type, col, null, null, required, insertOnly);
        }
        FieldSpec(String label, int type, int col, String comboQuery, boolean required) {
            this(label, type, col, comboQuery, null, required, false);
        }
        FieldSpec(String label, int type, int col, String comboQuery, boolean required, boolean insertOnly) {
            this(label, type, col, comboQuery, null, required, insertOnly);
        }
        FieldSpec(String label, int type, int col, String[] options, boolean required) {
            this(label, type, col, null, options, required, false);
        }
        FieldSpec(String label, int type, int col, String comboQuery, String[] options, boolean required, boolean insertOnly) {
            this.label = label;
            this.type = type;
            this.col = col;
            this.comboQuery = comboQuery;
            this.options = options;
            this.required = required;
            this.insertOnly = insertOnly;
        }
    }
}
