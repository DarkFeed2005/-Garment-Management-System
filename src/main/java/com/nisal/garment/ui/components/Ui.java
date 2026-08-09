package com.nisal.garment.ui.components;

import com.nisal.garment.util.Theme;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public final class Ui {
    private Ui() {}

    public static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.H2);
        l.setForeground(Theme.TEXT);
        return l;
    }

    public static JLabel pageTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.H1);
        l.setForeground(Theme.TEXT);
        return l;
    }

    public static JLabel muted(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(Theme.MUTED);
        return l;
    }

    public static JButton button(String text) {
        return button(text, Theme.BLUE);
    }

    public static JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_BOLD);
        b.setForeground(Color.WHITE);
        b.setBackground(color);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(11, 18, 11, 18));
        b.setContentAreaFilled(false);
        b.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override public void paint(Graphics g, JComponent c) {
                Graphics2D g2=(Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.isEnabled() ? (((AbstractButton) c).getModel().isRollover()? color.brighter():color) : new Color(190,195,205));
                g2.fillRoundRect(0,0,c.getWidth(),c.getHeight(),14,14);
                super.paint(g2,c);
                g2.dispose();
            }
        });
        return b;
    }

    public static JButton outlineButton(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_BOLD);
        b.setForeground(Theme.BLUE);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new CompoundBorder(
                new LineBorder(new Color(195, 210, 238), 1, true),
                new EmptyBorder(9, 16, 9, 16)));
        return b;
    }

    public static JTextField field() {
        JTextField f = new JTextField();
        styleField(f);
        return f;
    }

    public static JPasswordField passwordField() {
        JPasswordField f = new JPasswordField();
        styleField(f);
        return f;
    }

    private static void styleField(JTextField f) {
        f.setFont(Theme.FONT);
        f.setForeground(Theme.TEXT);
        f.setBackground(Color.WHITE);
        f.setBorder(new CompoundBorder(
                new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(10, 12, 10, 12)));
        f.setPreferredSize(new Dimension(250, 42));
    }

    public static JPanel card() {
        return new RoundedPanel(Color.WHITE, 18);
    }

    public static JPanel softCard() {
        return new RoundedPanel(new Color(251,252,254), 16);
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(36);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(Theme.TEXT);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(235,238,244));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(229,237,253));
        table.setSelectionForeground(Theme.TEXT);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setForeground(Theme.TEXT);
        table.getTableHeader().setBackground(new Color(247,249,252));
        table.getTableHeader().setBorder(new MatteBorder(0,0,1,0,Theme.BORDER));
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setBorder(new EmptyBorder(0,10,0,10));
        table.setDefaultRenderer(Object.class, r);
    }

    public static JPanel sectionHeader(String title, String subtitle, JComponent action) {
        JPanel p = new JPanel(new BorderLayout(10, 3));
        p.setOpaque(false);
        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));
        texts.add(title(title));
        texts.add(Box.createVerticalStrut(3));
        if (subtitle != null) texts.add(muted(subtitle));
        p.add(texts, BorderLayout.WEST);
        if (action != null) p.add(action, BorderLayout.EAST);
        return p;
    }

    public static JPanel statCard(String icon, String value, String label, Color accent) {
        JPanel p = new RoundedPanel(Color.WHITE, 18);
        p.setLayout(new BorderLayout(12, 4));
        p.setBorder(new EmptyBorder(16, 18, 16, 18));
        JLabel i = new JLabel(icon);
        i.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        i.setForeground(accent);
        p.add(i, BorderLayout.WEST);
        JPanel txt = new JPanel();
        txt.setOpaque(false);
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 23));
        v.setForeground(Theme.TEXT);
        txt.add(v);
        JLabel lab = muted(label);
        txt.add(lab);
        p.add(txt, BorderLayout.CENTER);
        return p;
    }

    public static JLabel pill(String text, Color color) {
        JLabel l = new JLabel(text);
        l.setOpaque(true);
        l.setForeground(color.darker());
        l.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 35));
        l.setBorder(new EmptyBorder(5, 9, 5, 9));
        return l;
    }
}
