package view.gui;

import controller.instance.Auth;
import controller.instance.JSONWriter;
import model.stored.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePatientAccountRequest {
    private JPanel pnlCreatePatientAccount;
    private JLabel lblRegister;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblFullName;
    private JLabel lblGender;
    private JLabel lblAge;
    private JLabel lblAddress;
    private JTextField txtUsername;
    private JTextField txtName;
    private JTextField txtGender;
    private JTextField txtAge;
    private JPasswordField txtPassword;
    private JTextArea txtAddress;
    private JButton txtRegister;
    private JButton txtCancel;
    private JFrame frame;
    private JFrame oldForm;

    public CreatePatientAccountRequest(JFrame oldForm) {
        this.oldForm = oldForm;
        //Initialize  this form
        frame = new JFrame("Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.setContentPane(pnlCreatePatientAccount);
        frame.setVisible(true);
        oldForm.setVisible(false);



        txtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
        txtRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check age is a number
                int age;
                try
                {
                    age = Integer.parseInt(txtAge.getText());
                    Auth.createPatientRequest(txtUsername.getText(), txtPassword.getText(), txtName.getText(), txtAddress.getText(), txtGender.getText(), age);
                    JSONWriter.writeUsers();
                    oldForm.setVisible(true);
                    frame.dispose();
                }
                catch (Exception patientCreateException)
                {
                    JOptionPane.showMessageDialog(null, "You did not enter a valid age!");
                }
            }
        });
    }
}
