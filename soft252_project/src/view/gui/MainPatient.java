package view.gui;

import controller.command.CreateAccountRequest;
import controller.command.UpdateAppointmentList;
import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPatient {
    private JPanel pnlMainPatient;
    private JButton btnLogout;
    private JScrollPane scrollAppointment;
    private JList lstAppointment;
    private JLabel lblAppointments;
    private JButton btnViewPrescriptionsAndHistory;
    private JButton btnRequestAppointment;
    private JButton btnCreatePendingFeedback;
    private JButton btnTerminate;
    private JFrame frame;

    public MainPatient(JFrame oldForm) {
        //Initialize  this form
        frame = new JFrame("Patient Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1000, 600);
        frame.setContentPane(pnlMainPatient);
        frame.setVisible(true);
        oldForm.setVisible(false);

        //update appointments
        updateList();

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                CurrentUser.clearCurrentUser();
                frame.dispose();
            }
        });
        btnViewPrescriptionsAndHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPatientHistory viewPatientHistory = new ViewPatientHistory(oldForm, CurrentUser.getCurrentUser().getUserId());
            }
        });
        btnRequestAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestAppointment requestAppointment = new RequestAppointment(frame);
            }
        });
        btnCreatePendingFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateFeedbackRequest createFeedbackRequest = new CreateFeedbackRequest(frame);
            }
        });
        btnTerminate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check the user wants to request termination
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to request account termination?", "Request account termination?", JOptionPane.YES_NO_OPTION);
                if (confirm == 0)
                {
                    CreateAccountRequest createAccountRequest = (CreateAccountRequest)CurrentUser.getCommand("CreateAccountRequest");
                    createAccountRequest.prepare(CurrentUser.getCurrentUser(), true);
                    createAccountRequest.perform();
                    //write out and confirm
                    JSONWriter.writeUsers();
                    JOptionPane.showMessageDialog(null, "Account deletion request sent");
                }
            }
        });
    }

    private void updateList()
    {
        ((UpdateAppointmentList) CurrentUser.getCommand("UpdateAppointmentList")).setListToUpdate(lstAppointment);
        CurrentUser.getCommand("UpdateAppointmentList").perform();
    }
}
