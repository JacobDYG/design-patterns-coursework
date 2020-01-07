package view.gui;

import controller.instance.Auth;
import controller.instance.JSONWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAdmin extends JFrame {
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JTextField txtFullName;
    private JTextField txtAddress;
    private JButton btnCreate;
    public JPanel panelStart;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblFullName;
    private JLabel lblAddress;
    private JLabel lblUsernameRequired;
    private JLabel lblPasswordRequired;
    private JLabel lblFullNameRequired;
    private JLabel lblAddressRequired;

    private JFrame thisFrame = this;

    public CreateAdmin(JFrame caller) {
        thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thisFrame.setBounds(100, 100, 450, 300);
        thisFrame.setContentPane(panelStart);
        thisFrame.setVisible(true);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtUsername.getText().length() > 0 && txtPassword.getText().length() > 0
                    && txtFullName.getText().length() > 0 && txtAddress.getText().length() > 0)
                {
                    Auth.createAdmin(txtUsername.getText(), txtPassword.getText(), txtFullName.getText(), txtAddress.getText());
                    if (Auth.adminCheck())
                    {
                        JSONWriter.writeUsers();
                        thisFrame.setVisible(false);
                        JOptionPane.showMessageDialog(null, "Admin \"" + txtUsername.getText() + "\" was added successfully!");
                        caller.setVisible(true);
                        thisFrame.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Admin was not added, please try again");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "All text fields must be populated to create an admin!");
                }
            }
        });
    }
}
