package com.nisal.garment.ui;

import com.nisal.garment.dao.RegistrationDAO;
import com.nisal.garment.ui.components.Ui;
import com.nisal.garment.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private final JFrame owner;
    private final JTextField name=Ui.field();
    private final JTextField email=Ui.field();
    private final JTextField phone=Ui.field();
    private final JTextField address=Ui.field();
    private final JTextField username=Ui.field();
    private final JPasswordField password=Ui.passwordField();

    public RegisterFrame(JFrame owner) {
        this.owner=owner;
        setTitle("Create Customer Account");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 690);
        setMinimumSize(new Dimension(620,620));
        setLocationRelativeTo(owner);
        build();
    }

    private void build() {
        JPanel root=new JPanel(new BorderLayout());
        root.setBackground(Theme.BG);
        root.setBorder(new EmptyBorder(28, 34, 28, 34));

        JPanel header=new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header,BoxLayout.Y_AXIS));
        header.add(Ui.pageTitle("Create your account"));
        header.add(Box.createVerticalStrut(6));
        header.add(Ui.muted("Register as a customer to place and track orders."));
        root.add(header,BorderLayout.NORTH);

        JPanel card=Ui.card();
        card.setBorder(new EmptyBorder(25,28,25,28));
        JPanel form=new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints g=new GridBagConstraints();
        g.insets=new Insets(8,8,8,8);
        g.fill=GridBagConstraints.HORIZONTAL;
        g.weightx=1;

        addField(form,g,0,"Full Name",name,0);
        addField(form,g,1,"Email",email,1);
        addField(form,g,2,"Phone",phone,2);
        addField(form,g,3,"Address",address,3);
        addField(form,g,4,"Username",username,4);
        addField(form,g,5,"Password",password,5);

        card.add(form,BorderLayout.CENTER);
        root.add(card,BorderLayout.CENTER);

        JPanel actions=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        actions.setOpaque(false);
        JButton cancel=Ui.outlineButton("Cancel");
        cancel.addActionListener(e->dispose());
        JButton create=Ui.button("Create Account");
        create.addActionListener(e->register());
        actions.add(cancel); actions.add(create);
        root.add(actions,BorderLayout.SOUTH);
        setContentPane(root);
    }

    private void addField(JPanel p,GridBagConstraints g,int row,String label,JComponent field,int col) {
        g.gridy=row; g.gridx=0; g.gridwidth=1; g.weightx=.25;
        JLabel l=new JLabel(label);
        l.setFont(Theme.FONT_BOLD); l.setForeground(Theme.TEXT);
        p.add(l,g);
        g.gridx=1; g.gridwidth=2; g.weightx=1;
        p.add(field,g);
    }

    private void register() {
        if(name.getText().isBlank() || email.getText().isBlank() ||
           username.getText().isBlank() || password.getPassword().length<4) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields.\nPassword must be at least 4 characters.",
                    "Registration",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            new RegistrationDAO().registerCustomer(
                    username.getText().trim(),new String(password.getPassword()),
                    name.getText().trim(),email.getText().trim(),
                    phone.getText().trim(),address.getText().trim());
            JOptionPane.showMessageDialog(this,
                    "Registration successful. You can now sign in.",
                    "Registration",JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Registration failed:\n"+ex.getMessage(),
                    "Registration",JOptionPane.ERROR_MESSAGE);
        }
    }
}
