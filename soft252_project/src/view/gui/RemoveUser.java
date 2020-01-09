package view.gui;

import controller.command.UpdateUsersList;
import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveUser {
    private JLabel lblRemoveUser;
    private JButton btnRemoveUser;
    private JButton btnCancel;
    private JList lstUsers;
    private JPanel pnlRemoveUser;
    private JScrollPane scrUsers;
    private JFrame frame;
    boolean admin = false;
    boolean secretary = false;
    boolean doctor = false;
    boolean patient = false;

    public RemoveUser(JFrame oldForm, boolean admin, boolean secretary, boolean doctor, boolean patient)
    {
        frame = new JFrame("Remove user");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.setContentPane(pnlRemoveUser);
        frame.setVisible(true);
        oldForm.setVisible(false);

        this.admin = admin;
        this.secretary = secretary;
        this.doctor = doctor;
        this.patient = patient;

        updateList();

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
        btnRemoveUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String)lstUsers.getSelectedValue();
                int endIndex = selectedValue.indexOf(",");
                int userId = Integer.parseInt(selectedValue.substring(9, endIndex));
                ((controller.command.RemoveUser)CurrentUser.getCommand("RemoveUser")).setUser(userId);
                CurrentUser.getCommand("RemoveUser").perform();
                updateList();
                JSONWriter.writeUsers();
            }
        });
    }

    private void updateList()
    {
        UpdateUsersList updateUsersList = (UpdateUsersList)CurrentUser.getCommand("UpdateUsersList");
        updateUsersList.prepare(lstUsers, admin, secretary, doctor, patient);
        updateUsersList.perform();
    }
}
