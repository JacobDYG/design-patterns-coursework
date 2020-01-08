package view.gui;

import controller.command.UpdatePatientHistoryForm;
import controller.command.UpdateUsersCombo;
import controller.command.UpdateUsersList;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPatientHistory {
    private JLabel lblViewPatientHistory;
    private JLabel lblSelectPatient;
    private JComboBox cmbSelectPatient;
    private JLabel lblPrescriptions;
    private JLabel lblPastAppointments;
    private JPanel panelViewPatientHistory;
    private JButton btnBack;
    private JScrollPane scrollPrescriptions;
    private JScrollPane scrollPastAppointments;
    private JList lstPrescriptions;
    private JList lstPastAppointments;
    private JFrame frame;

    public ViewPatientHistory(JFrame oldForm) {
        //Initialize  this form
        frame = new JFrame("View Patient History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1000, 700);
        frame.setContentPane(panelViewPatientHistory);
        frame.setVisible(true);
        oldForm.setVisible(false);

        //Populate the patient list
        updatePatientList();
        //Update patient history
        updatePatientHistory();

        cmbSelectPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Update patient history again if selection changed
                updatePatientHistory();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void updatePatientList()
    {
        UpdateUsersCombo updateUsersCombo = (UpdateUsersCombo) CurrentUser.getCommand("UpdateUsersCombo");
        updateUsersCombo.prepare(cmbSelectPatient, false, false, false, true);
        updateUsersCombo.perform();
    }

    private void updatePatientHistory()
    {
        UpdatePatientHistoryForm updatePatientHistoryForm = (UpdatePatientHistoryForm)CurrentUser.getCommand("UpdatePatientHistoryForm");
        //get user id
        int userId;
        String selectedValue = (String) cmbSelectPatient.getSelectedItem();
        int endIndex = selectedValue.indexOf(",");
        userId = Integer.parseInt(selectedValue.substring(9, endIndex));

        //update lists
        updatePatientHistoryForm.prepare(userId, lstPrescriptions, lstPastAppointments);
        updatePatientHistoryForm.perform();
    }
}
