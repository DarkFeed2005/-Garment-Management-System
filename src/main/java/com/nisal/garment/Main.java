package com.nisal.garment;

import com.nisal.garment.ui.LoginFrame;
import com.nisal.garment.util.Theme;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Theme.install();
            new LoginFrame().setVisible(true);
        });
    }
}
