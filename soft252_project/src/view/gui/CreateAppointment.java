package view.gui;

import controller.command.UpdateAppointmentList;
import controller.command.UpdateUsersCombo;
import controller.instance.JSONWriter;
import model.stored.Appointment;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateAppointment {
    private JPanel panelCreateAppointment;
    private JLabel lblCreateAppointment;
    private JLabel lblDoctor;
    private JLabel lblPatient;
    private JComboBox cmbPatient;
    private JComboBox cmbDoctor;
    private JLabel lblDate;
    private JTextField txtDate;
    private JButton btnCreate;
    private JButton btnCancel;
    private JFrame frame;
    private int doctorId = -1;
    private int patientId = -1;
    private JList listToUpdate;

    public CreateAppointment(JFrame oldForm, JList listToUpdate) {
        runForm(oldForm, listToUpdate);
    }

    public CreateAppointment(JFrame oldForm, JList listToUpdate, int doctorId) {
        //Set up combo box/es with passed user/s
        DefaultComboBoxModel passedDoctor = new DefaultComboBoxModel();
        passedDoctor.addElement("Doctor ID: " + doctorId + ", (Current User)");
        cmbDoctor.setModel(passedDoctor);
        cmbDoctor.setEnabled(false);
        this.doctorId = doctorId;

        //initialise form
        runForm(oldForm, listToUpdate);
    }

    public CreateAppointment(JFrame oldForm, JList listToUpdate,int doctorId, int patientId) {
        //Set up combo box/es with passed user/s
        DefaultComboBoxModel passedDoctor = new DefaultComboBoxModel();
        passedDoctor.addElement("Doctor ID: " + doctorId + ", (Current User)");
        cmbDoctor.setModel(passedDoctor);
        cmbDoctor.setEnabled(false);
        DefaultComboBoxModel passedPatient = new DefaultComboBoxModel();
        passedPatient.addElement("Patient ID: " + patientId + ", (Passed Patient)");
        cmbPatient.setModel(passedPatient);
        cmbPatient.setEnabled(false);
        this.doctorId = doctorId;
        this.patientId = patientId;

        //initialise form
        runForm(oldForm, listToUpdate);
    }

    private void runForm(JFrame oldForm, JList listToUpdate)
    {
        //Initialize  this form
        frame = new JFrame("Create Appointment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.setContentPane(panelCreateAppointment);
        frame.setVisible(true);
        oldForm.setVisible(false);
        this.listToUpdate = listToUpdate;

        //update the doctor and patient combos
        updateUserCombos();

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get patient and doctor
                int patientId;
                //find the patient id
                String selectedPatient = (String) cmbPatient.getSelectedItem();
                int endIndexPatient = selectedPatient.indexOf(",");
                patientId = Integer.parseInt(selectedPatient.substring(9, endIndexPatient));
                int doctorId;
                //find the doctor id
                String selectedDoctor = (String) cmbDoctor.getSelectedItem();
                int endIndexDoctor = selectedDoctor.indexOf(",");
                doctorId = Integer.parseInt(selectedDoctor.substring(11, endIndexDoctor));
                //try to parse the date
                Date appointmentDate;
                SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh:mm");
                try {
                    appointmentDate = format.parse((String)txtDate.getText());
                    //if date was parsed, create the appointment
                    controller.command.CreateAppointment createAppointment = (controller.command.CreateAppointment)CurrentUser.getCommand("CreateAppointment");
                    createAppointment.prepare(doctorId, patientId, appointmentDate);
                    createAppointment.perform();
                    //update list in parent form, write and quit
                    UpdateAppointmentList updateAppointmentList = (UpdateAppointmentList)CurrentUser.getCommand("UpdateAppointmentList");
                    updateAppointmentList.setListToUpdate(listToUpdate);
                    updateAppointmentList.perform();
                    JSONWriter.writeUsers();
                    oldForm.setVisible(true);
                    frame.dispose();
                } catch (java.text.ParseException parseException) {
                    JOptionPane.showMessageDialog(null, "The date could not be processed! Check the format is DD-MM-YYYY hh:mm and try again!");
                    parseException.printStackTrace();
                }
            }
        });
    }

    private void updateUserCombos() {
        //Only update the combo box if a doctor hasn't been passed
        if (doctorId == -1) {
            UpdateUsersCombo updateDoctors = new UpdateUsersCombo();
            updateDoctors.prepare(cmbDoctor, false, false, true, false);
            updateDoctors.perform();
        }
        if (patientId == -1) {
            UpdateUsersCombo updatePatients = new UpdateUsersCombo();
            updatePatients.prepare(cmbPatient, false, false, false, true);
            updatePatients.perform();
        }
    }
}
