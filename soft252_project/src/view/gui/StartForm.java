package view.gui;

import controller.instance.Auth;
import controller.instance.JSONReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartForm {
    private JButton btnShow;
    private JPanel panelStart;
    private JFrame frame;

    public StartForm() {
        //Initialize  this form
        frame = new JFrame("Welcome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 450, 300);
        frame.setContentPane(panelStart);

        //Prepares the data for the system, and checks if there is an admin
        initializeData();

        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "TestMessage");
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
