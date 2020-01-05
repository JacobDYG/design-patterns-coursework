package view.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateAdmin extends JFrame {
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton createButton;
    public JPanel panelStart;

    public CreateAdmin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        //panelStart.setBorder(new EmptyBorder(5, 5, 5, 5));
        //panelStart.setLayout(new BorderLayout(0,0));
        setContentPane(panelStart);
        setVisible(true);

    }


}
