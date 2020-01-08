package view.gui;

import controller.command.UpdateAppointmentNotes;
import controller.command.UpdateAppointmentNotesForm;
import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddConsultationNotes {

    private JFrame frame;
    private JPanel panelAddNotes;
    private JLabel lblAddNotes;
    private JTextArea txtAppointmentNotes;
    private JButton btnCancel;
    private JButton btnAddNotes;
    private int appointmentId;

    public AddConsultationNotes(JFrame oldForm, int appointmentId) {
        //Initialize  this form
        frame = new JFrame("Add consultation notes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.setContentPane(panelAddNotes);
        frame.setVisible(true);
        oldForm.setVisible(false);

        this.appointmentId = appointmentId;

        //populate notes if they already exist
        updateNotes(appointmentId);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
        btnAddNotes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateAppointmentNotes updateAppointmentNotes = (UpdateAppointmentNotes)CurrentUser.getCommand("UpdateAppointmentNotes");
                updateAppointmentNotes.setAppointmentId(appointmentId);
                updateAppointmentNotes.setTxtAppointmentNotes(txtAppointmentNotes);
                updateAppointmentNotes.perform();

                JSONWriter.writeUsers();
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void updateNotes(int appointmentId)
    {
        ((UpdateAppointmentNotesForm) CurrentUser.getCommand("UpdateAppointmentNotesForm")).setAppointmentId(appointmentId);
        ((UpdateAppointmentNotesForm) CurrentUser.getCommand("UpdateAppointmentNotesForm")).setTxtAppointmentNotes(txtAppointmentNotes);
        CurrentUser.getCommand("UpdateAppointmentNotesForm").perform();
    }
}
