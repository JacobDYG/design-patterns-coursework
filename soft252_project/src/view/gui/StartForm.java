package view.gui;

import com.sun.tools.javac.Main;
import controller.instance.Auth;
import controller.instance.JSONReader;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartForm {
    private JPanel panelStart;
    private JLabel lblLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JFrame frame;
    private MainAdmin adminForm;
    private MainSecretary secretaryForm;
    private MainDoctor doctorForm;
    private MainPatient patientForm;

    public StartForm() {
        //Initialize  this form
        frame = new JFrame("Welcome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 450, 300);
        frame.setContentPane(panelStart);

        //Prepares the data for the system, and checks if there is an admin
        initializeData();
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auth.login(txtUsername.getText(), txtPassword.getText());
                if (CurrentUser.getCurrentUser() != null)
                {
                    switch(CurrentUser.getCurrentUser().getRole()) {
                        case "Admin": {
                            adminForm = new MainAdmin(frame);
                            break;
                        }
                        case "Secretary": {
                            secretaryForm = new MainSecretary(frame);
                            break;
                        }
                        case "Doctor": {
                            doctorForm = new MainDoctor(frame);
                            break;
                        }
                        case "Patient": {
                            patientForm = new MainPatient(frame);
                            break;
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "User not found! Check details and try again");
                }
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        //Apply system look - makes everything less ugly
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //Start the main form
        StartForm startForm = new StartForm();
    }

    private void initializeData()
    {
        boolean medicineFileCreated = false;
        boolean userFileCreated = false;
        File medicineFile = new File("medicines.json");
        File userFile = new File("users.json");
        try {
            medicineFileCreated = medicineFile.createNewFile();
            userFileCreated = userFile.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("A medicines file was created: " + medicineFileCreated + ", A users file was created: " + userFileCreated );

        if (medicineFile.length() > 0)
            JSONReader.readMedicines();
        if (userFile.length() > 0)
            JSONReader.readUsers();


        if(!Auth.adminCheck())
        {
            CreateAdmin createAdmin = new CreateAdmin(frame);
        }
        else
        {
            frame.setVisible(true);
        }
    }
}
