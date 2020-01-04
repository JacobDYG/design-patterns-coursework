package view.gui;

import controller.instance.JSONReader;
import controller.instance.JSONWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartForm");
        frame.setContentPane(new StartForm().panelStart);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        System.out.println("Form started");

        JSONReader.readMedicines();
        JSONWriter.WriteMedicines();

        JSONReader.readUsers();
    }
}
