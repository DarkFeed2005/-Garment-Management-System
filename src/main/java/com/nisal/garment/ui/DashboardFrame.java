package com.nisal.garment.ui;

import com.nisal.garment.model.SessionUser;
import com.nisal.garment.security.AccessControl;
import com.nisal.garment.ui.components.Ui;
import com.nisal.garment.ui.panels.*;
import com.nisal.garment.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardFrame extends JFrame {
    private final SessionUser user;
    private final CardLayout cards=new CardLayout();
    private final JPanel content=new JPanel(cards);
    private final Map<String,JButton> nav=new LinkedHashMap<>();
    private final JLabel pageTitle=new JLabel("Dashboard");
    private final JLabel pageSubtitle=new JLabel();

    public DashboardFrame(SessionUser user) {
        this.user=user;
        setTitle("Garment Production & Order Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1380,820);
        setMinimumSize(new Dimension(1100,700));
        setLocationRelativeTo(null);
        build();
    }

    private void build() {
        JPanel root=new JPanel(new BorderLayout());
        root.setBackground(Theme.BG);
        root.add(sidebar(),BorderLayout.WEST);
        root.add(mainArea(),BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel sidebar() {
        JPanel side=new JPanel();
        side.setPreferredSize(new Dimension(245,0));
        side.setBackground(Theme.NAVY);
        side.setBorder(new EmptyBorder(24,16,20,16));
        side.setLayout(new BoxLayout(side,BoxLayout.Y_AXIS));

        JLabel brand=new JLabel("GARMENT");
        brand.setFont(new Font("Segoe UI",Font.BOLD,24));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(brand);

        JLabel brand2=new JLabel("PRODUCTION SYSTEM");
        brand2.setFont(new Font("Segoe UI",Font.BOLD,10));
        brand2.setForeground(new Color(157,173,202));
        brand2.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(brand2);
        side.add(Box.createVerticalStrut(30));

        JLabel menu=new JLabel("MAIN MENU");
        menu.setFont(new Font("Segoe UI",Font.BOLD,10));
        menu.setForeground(new Color(125,143,176));
        menu.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(menu);
        side.add(Box.createVerticalStrut(10));

        addNav(side,"⌂","Dashboard","HOME");
        if(AccessControl.canManageCustomers(user)) addNav(side,"♙","Customers","CUSTOMERS");
        if(AccessControl.canManageProducts(user)) addNav(side,"▣","Products","PRODUCTS");
        if(AccessControl.canManageProduction(user)) addNav(side,"♟","Staff","STAFF");
        if(AccessControl.canManageOrders(user)) {
            addNav(side,"▤","Orders","ORDERS");
            addNav(side,"≡","Order Details","ORDER_DETAILS");
        }
        if(AccessControl.canManageProduction(user)) {
            addNav(side,"⚙","Production Orders","PRODUCTION_ORDERS");
            addNav(side,"◆","Production Stages","PRODUCTION_STAGES");
        }
        if(AccessControl.canManageDeliveries(user)) {
            addNav(side,"➤","Deliveries","DELIVERIES");
            addNav(side,"□","Delivery Items","DELIVERY_ITEMS");
        }
        if(AccessControl.canManageUsers(user)) addNav(side,"●","User Management","USERS");

        side.add(Box.createVerticalGlue());

        JPanel profile=new JPanel(new BorderLayout(10,0));
        profile.setOpaque(false);
        profile.setBorder(new EmptyBorder(10,4,14,4));
        JLabel avatar=new JLabel(initials(user.fullName()),SwingConstants.CENTER);
        avatar.setOpaque(true); avatar.setBackground(Theme.BLUE);
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("Segoe UI",Font.BOLD,14));
        avatar.setPreferredSize(new Dimension(40,40));
        JPanel text=new JPanel();
        text.setOpaque(false); text.setLayout(new BoxLayout(text,BoxLayout.Y_AXIS));
        JLabel name=new JLabel(user.fullName());
        name.setForeground(Color.WHITE); name.setFont(Theme.FONT_BOLD);
        JLabel role=new JLabel(user.role().replace('_',' '));
        role.setForeground(new Color(161,175,199)); role.setFont(new Font("Segoe UI",Font.PLAIN,11));
        text.add(name); text.add(role);
        profile.add(avatar,BorderLayout.WEST); profile.add(text,BorderLayout.CENTER);
        side.add(profile);

        JButton logout=navButton("⇥","Sign Out");
        logout.addActionListener(e->{dispose();new LoginFrame().setVisible(true);});
        side.add(logout);
        return side;
    }

    private String initials(String s) {
        String[] p=s.trim().split("\\s+");
        if(p.length==1) return p[0].substring(0,1).toUpperCase();
        return (p[0].substring(0,1)+p[p.length-1].substring(0,1)).toUpperCase();
    }

    private void addNav(JPanel side,String icon,String text,String key) {
        JButton b=navButton(icon,text);
        b.addActionListener(e->{
            cards.show(content,key);
            pageTitle.setText(text);
            pageSubtitle.setText("Manage and monitor "+text.toLowerCase()+".");
            refreshActive(key);
        });
        side.add(b);
        side.add(Box.createVerticalStrut(5));
        nav.put(key,b);
    }

    private JButton navButton(String icon,String text) {
        JButton b=new JButton();
        b.setLayout(new BorderLayout(12,0));
        JLabel i=new JLabel(icon);
        i.setFont(new Font("Segoe UI Symbol",Font.PLAIN,17));
        i.setForeground(new Color(184,197,221));
        JLabel t=new JLabel(text);
        t.setFont(Theme.FONT_BOLD);
        t.setForeground(new Color(220,227,239));
        b.add(i,BorderLayout.WEST); b.add(t,BorderLayout.CENTER);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE,44));
        b.setPreferredSize(new Dimension(210,44));
        b.setBackground(Theme.BLUE);
        b.setBorder(new EmptyBorder(10,12,10,10));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void refreshActive(String key) {
        for(var e:nav.entrySet()) {
            e.getValue().setBackground(e.getKey().equals(key) ? Theme.BLUE_DARK : Theme.BLUE);
        }
    }

    private JPanel mainArea() {
        JPanel main=new JPanel(new BorderLayout());
        main.setBackground(Theme.BG);
        main.add(header(),BorderLayout.NORTH);
        buildPages();
        content.setBackground(Theme.BG);
        main.add(content,BorderLayout.CENTER);
        return main;
    }

    private JPanel header() {
        JPanel h=new JPanel(new BorderLayout());
        h.setBackground(Color.WHITE);
        h.setBorder(new EmptyBorder(22,28,18,28));
        JPanel left=new JPanel();
        left.setOpaque(false); left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        pageTitle.setFont(Theme.H1); pageTitle.setForeground(Theme.TEXT);
        pageSubtitle.setFont(new Font("Segoe UI",Font.PLAIN,13)); pageSubtitle.setForeground(Theme.MUTED);
        pageSubtitle.setText("Overview of your garment production system.");
        left.add(pageTitle); left.add(Box.createVerticalStrut(4)); left.add(pageSubtitle);
        h.add(left,BorderLayout.WEST);

        JPanel right=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
        right.setOpaque(false);
        JLabel dot=new JLabel("●");
        dot.setForeground(Theme.SUCCESS);
        JLabel online=new JLabel("System Online");
        online.setForeground(Theme.SUCCESS); online.setFont(Theme.FONT_BOLD);
        right.add(dot); right.add(online);
        h.add(right,BorderLayout.EAST);
        return h;
    }

    private void buildPages() {
        content.add(homePanel(),"HOME");
        if(AccessControl.canManageCustomers(user)) content.add(new CustomerPanel(user),"CUSTOMERS");
        if(AccessControl.canManageProducts(user)) content.add(new ProductPanel(user),"PRODUCTS");
        if(AccessControl.canManageProduction(user)) content.add(new StaffPanel(user),"STAFF");
        if(AccessControl.canManageOrders(user)) {
            content.add(new OrderPanel(user),"ORDERS");
            content.add(new OrderDetailPanel(user),"ORDER_DETAILS");
        }
        if(AccessControl.canManageProduction(user)) {
            content.add(new ProductionOrderPanel(user),"PRODUCTION_ORDERS");
            content.add(new ProductionStagePanel(user),"PRODUCTION_STAGES");
        }
        if(AccessControl.canManageDeliveries(user)) {
            content.add(new DeliveryPanel(user),"DELIVERIES");
            content.add(new DeliveryItemPanel(user),"DELIVERY_ITEMS");
        }
        if(AccessControl.canManageUsers(user)) content.add(new UserPanel(user),"USERS");
        cards.show(content,"HOME");
        refreshActive("HOME");
    }

    private JPanel homePanel() {
        JPanel p=new JPanel(new BorderLayout(18,18));
        p.setBackground(Theme.BG);
        p.setBorder(new EmptyBorder(22,28,28,28));

        JPanel welcome=Ui.card();
        welcome.setBorder(new EmptyBorder(22,24,22,24));
        JPanel w=new JPanel(new BorderLayout());
        w.setOpaque(false);
        JPanel texts=new JPanel();
        texts.setOpaque(false); texts.setLayout(new BoxLayout(texts,BoxLayout.Y_AXIS));
        JLabel big=new JLabel("Good to see you, "+user.fullName()+"!");
        big.setFont(new Font("Segoe UI",Font.BOLD,24)); big.setForeground(Theme.TEXT);
        texts.add(big); texts.add(Box.createVerticalStrut(7));
        texts.add(Ui.muted("You are signed in as "+user.role().replace('_',' ')+". Choose a module from the sidebar to get started."));
        w.add(texts,BorderLayout.WEST);
        JLabel badge=Ui.pill("ACTIVE SESSION",Theme.SUCCESS);
        w.add(badge,BorderLayout.EAST);
        welcome.add(w,BorderLayout.CENTER);
        p.add(welcome,BorderLayout.NORTH);

        JPanel stats=new JPanel(new GridLayout(1,4,15,0));
        stats.setOpaque(false);
        stats.add(Ui.statCard("▣","Products","Product catalogue",Theme.BLUE));
        stats.add(Ui.statCard("▤","Orders","Order management",Theme.TEAL));
        stats.add(Ui.statCard("⚙","Production","Production workflow",Theme.WARNING));
        stats.add(Ui.statCard("➤","Delivery","Delivery tracking",Theme.SUCCESS));
        p.add(stats,BorderLayout.CENTER);

        JPanel bottom=new JPanel(new GridLayout(1,2,15,0));
        bottom.setOpaque(false);
        JPanel quick=Ui.card();
        quick.setBorder(new EmptyBorder(20,22,20,22));
        quick.setLayout(new BoxLayout(quick,BoxLayout.Y_AXIS));
        quick.add(Ui.title("Quick access"));
        quick.add(Box.createVerticalStrut(12));
        quick.add(Ui.muted("Use the navigation menu to access the modules available to your role."));
        quick.add(Box.createVerticalStrut(15));
        quick.add(Ui.pill("ROLE: "+user.role().replace('_',' '),Theme.BLUE));
        JPanel about=Ui.card();
        about.setBorder(new EmptyBorder(20,22,20,22));
        about.setLayout(new BoxLayout(about,BoxLayout.Y_AXIS));
        about.add(Ui.title("Garment Production System"));
        about.add(Box.createVerticalStrut(12));
        about.add(Ui.muted("A simple desktop management system for customers, products, orders, production and deliveries."));
        bottom.add(quick); bottom.add(about);
        p.add(bottom,BorderLayout.SOUTH);
        return p;
    }
}
