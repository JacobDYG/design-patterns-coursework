package view.gui;

import controller.instance.Auth;
import controller.instance.JSONReader;
import controller.instance.JSONWriter;
import model.instance.CurrentData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class StartForm {
    private JButton btnShow;
    private JPanel panelStart;

    public StartForm() {
        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "TestMessage");
            }
        });
    }

    public static void main(String[] args) throws ParseException {
        JFrame frame = new JFrame("Welcome");
        frame.setContentPane(new StartForm().panelStart);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        System.out.println("Form started");

        //Prepares the data for the system, and checks if there is an admin
        initializeData();

        /*JFrame createAdmin = new JFrame("Create an admin account");
        createAdmin.setContentPane(new CreateAdmin().panelStart);
        createAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        createAdmin.setVisible(true);*/

        CreateAdmin createAdmin = new CreateAdmin();
        //createAdmin.setVisible(true);


        //JSONWriter.writeMedicines();
        //JSONWriter.writeUsers();

    }

    private static void initializeData()
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

        if (!Auth.adminCheck())
        {
            Auth.createAdmin("admin1", "adminpassword", "Dave", "3 Fake Road");
            JSONWriter.writeUsers();
        }
    }
}
