package view;

import model.user.User;

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

        User dave = new User("Admin", 1, "Dave", "1 ARoad, ATown");

        dave.getRole().getAction("Example Action").perform();
        dave.getRole().getAction("Example Action Two").perform();

        User bill = new User("Patient", 2, "Bill", "2 ARoad, ATown", "Male", 25);
        User henry = new User("Secretary", 3, "Henry", "3 ARoad, ATown");
        User sarah = new User("Doctor", 4, "Sarah", "4 ARoad, ATown");
    }
}
