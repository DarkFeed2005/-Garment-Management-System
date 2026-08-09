package com.nisal.garment.ui;

import com.nisal.garment.dao.AuthDAO;
import com.nisal.garment.model.SessionUser;
import com.nisal.garment.ui.components.Ui;
import com.nisal.garment.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginFrame extends JFrame {
    private final JTextField username = Ui.field();
    private final JPasswordField password = Ui.passwordField();

    public LoginFrame() {
        setTitle("Garment Production & Order Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 680);
        setMinimumSize(new Dimension(920, 600));
        setLocationRelativeTo(null);
        build();
    }

    private void build() {
        JPanel root = new JPanel(new GridLayout(1,2));
        root.setBackground(Theme.BG);
        root.add(brandPanel());
        root.add(formPanel());
        setContentPane(root);

        getRootPane().setDefaultButton(null);
        password.addActionListener(e -> login());
    }

    private JPanel brandPanel() {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2=(Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp=new GradientPaint(0,0,Theme.NAVY,getWidth(),getHeight(),Theme.NAVY_2);
                g2.setPaint(gp); g2.fillRect(0,0,getWidth(),getHeight());
                g2.setColor(new Color(255,255,255,18));
                g2.fillOval(-110, getHeight()-250, 360, 360);
                g2.fillOval(getWidth()-250, -130, 330, 330);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(55, 55, 55, 55));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("GARMENT");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        logo.setForeground(Color.WHITE);
        p.add(logo);

        JLabel sub = new JLabel("Production & Order Management");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sub.setForeground(new Color(218,225,239));
        p.add(Box.createVerticalStrut(8));
        p.add(sub);

        p.add(Box.createVerticalGlue());

        JLabel heading = new JLabel("<html>Manage your garment<br>business in one place.</html>");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 32));
        heading.setForeground(Color.WHITE);
        p.add(heading);
        p.add(Box.createVerticalStrut(18));

        JLabel text = new JLabel("<html>Orders • Production • Products • Staff • Deliveries</html>");
        text.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        text.setForeground(new Color(215,222,236));
        p.add(text);

        p.add(Box.createVerticalStrut(55));
        JLabel footer = new JLabel("Smart • Simple • Organized");
        footer.setFont(new Font("Segoe UI", Font.BOLD, 13));
        footer.setForeground(new Color(177,190,214));
        p.add(footer);

        return p;
    }

    private JPanel formPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(Theme.BG);

        JPanel card = Ui.card();
        card.setPreferredSize(new Dimension(430, 500));
        card.setBorder(new EmptyBorder(35, 38, 35, 38));
        card.setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel title = Ui.pageTitle("Welcome back");
        form.add(title);
        form.add(Box.createVerticalStrut(8));
        form.add(Ui.muted("Sign in to continue to your dashboard."));
        form.add(Box.createVerticalStrut(32));

        form.add(label("Username"));
        form.add(Box.createVerticalStrut(7));
        form.add(username);
        form.add(Box.createVerticalStrut(18));

        form.add(label("Password"));
        form.add(Box.createVerticalStrut(7));
        form.add(password);
        form.add(Box.createVerticalStrut(10));

        JLabel hint = Ui.muted("Use your registered system account.");
        form.add(hint);
        form.add(Box.createVerticalStrut(28));

        JButton login = Ui.button("Sign In");
        login.setAlignmentX(Component.LEFT_ALIGNMENT);
        login.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        login.addActionListener(e -> login());
        form.add(login);

        form.add(Box.createVerticalStrut(14));
        JButton register = Ui.outlineButton("Create Customer Account");
        register.setAlignmentX(Component.LEFT_ALIGNMENT);
        register.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        register.addActionListener(e -> new RegisterFrame(this).setVisible(true));
        form.add(register);

        card.add(form, BorderLayout.CENTER);
        outer.add(card);
        return outer;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.FONT_BOLD);
        l.setForeground(Theme.TEXT);
        return l;
    }

    private void login() {
        if (username.getText().isBlank() || password.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter your username and password.",
                    "Sign In", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            SessionUser user = new AuthDAO().login(
                    username.getText().trim(), new String(password.getPassword()));
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.",
                        "Sign In", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dispose();
            new DashboardFrame(user).setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not connect to MySQL.\n\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
