package view.gui;

import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUser {
    private JLabel lblCreateUser;
    private JLabel lblUserRole;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblFullName;
    private JLabel lblAddress;
    private JTextField txtUsername;
    private JTextField txtFullName;
    private JTextArea txtAddress;
    private JComboBox cmbRole;
    private JPasswordField txtPassword;
    private JButton btnCreateUser;
    private JButton btnCancel;
    private JPanel frmCreateUser;
    private JFrame frame;

    public CreateUser(JFrame oldForm)
    {
        //Initialize  this form
        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.setContentPane(frmCreateUser);
        frame.setVisible(true);
        oldForm.setVisible(false);

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("Secretary");
        model.addElement("Doctor");
        cmbRole.setModel(model);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
        btnCreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check all forms were filled
                if (txtUsername.getText().length() > 0 && txtPassword.getText().length() > 0 && txtFullName.getText().length() > 0 && txtAddress.getText().length() > 0) {
                    //Set the command attributes
                    ((controller.command.CreateUser) CurrentUser.getCommand("CreateUser")).setAttributes(txtUsername.getText(),
                            txtPassword.getText(), (String) cmbRole.getSelectedItem(), txtFullName.getText(), txtAddress.getText(), null, -1);
                    //Perform the command with above attributes
                    CurrentUser.getCommand("CreateUser").perform();
                    //Write to file and close this form
                    JSONWriter.writeUsers();
                    oldForm.setVisible(true);
                    frame.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "One of the forms was not filled - fill them all and try again!");
                }
            }
        });
    }
}
