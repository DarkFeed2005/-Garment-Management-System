package com.nisal.garment.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public final class Theme {
    private Theme() {}

    public static final Color NAVY = new Color(18, 28, 48);
    public static final Color NAVY_2 = new Color(31, 45, 72);
    public static final Color BLUE = new Color(65, 105, 225);
    public static final Color BLUE_DARK = new Color(45, 78, 160);
    public static final Color TEAL = new Color(24, 164, 150);
    public static final Color BG = new Color(246, 248, 252);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(28, 37, 54);
    public static final Color MUTED = new Color(112, 122, 140);
    public static final Color BORDER = new Color(226, 231, 239);
    public static final Color SUCCESS = new Color(39, 156, 91);
    public static final Color WARNING = new Color(230, 159, 47);
    public static final Color DANGER = new Color(205, 72, 72);

    public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font H1 = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font H2 = new Font("Segoe UI", Font.BOLD, 21);
    public static final Font H3 = new Font("Segoe UI", Font.BOLD, 16);

    public static void install() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        UIManager.put("defaultFont", FONT);
        UIManager.put("Label.font", FONT);
        UIManager.put("Button.font", FONT_BOLD);
        UIManager.put("TextField.font", FONT);
        UIManager.put("PasswordField.font", FONT);
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("Table.rowHeight", 34);
        UIManager.put("Table.gridColor", BORDER);
        UIManager.put("Table.selectionBackground", new Color(226, 235, 255));
        UIManager.put("Table.selectionForeground", TEXT);
        UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
        UIManager.put("TextField.caretForeground", BLUE);
        UIManager.put("PasswordField.caretForeground", BLUE);
    }
}
