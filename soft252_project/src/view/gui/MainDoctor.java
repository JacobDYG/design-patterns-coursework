package view.gui;

import controller.command.CreateNewMedicine;
import controller.command.UpdateAppointmentList;
import controller.command.UpdatePendingFeedbackList;
import controller.instance.JSONWriter;
import model.instance.CurrentData;
import model.stored.role.DoctorRoleData;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDoctor {
    private JFrame frame;
    private JPanel doctorPanel;
    private JButton btnLogout;
    private JScrollPane scrollAppointment;
    private JList lstAppointment;
    private JButton btnAddNotes;
    private JButton btnViewHistory;
    private JButton btnCreateAppointment;
    private JButton btnCreatePrescription;
    private JButton btnCreateMedicine;
    private JLabel lblAppointments;
    private AddConsultationNotes addConsultationNotes;


    public MainDoctor(JFrame oldForm) {
        //Initialize  this form
        frame = new JFrame("Doctor Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1000, 600);
        frame.setContentPane(doctorPanel);
        frame.setVisible(true);
        oldForm.setVisible(false);

        updateList();
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                CurrentUser.clearCurrentUser();
                frame.dispose();
            }
        });
        btnAddNotes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!lstAppointment.isSelectionEmpty()) {
                    int appointmentId;
                    //find the appointment id
                    String selectedValue = (String) lstAppointment.getSelectedValue();
                    int endIndex = selectedValue.indexOf(",");
                    appointmentId = Integer.parseInt(selectedValue.substring(16, endIndex));

                    addConsultationNotes = new AddConsultationNotes(frame, appointmentId);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No appointment was selected!!!");
                }
            }
        });
        btnViewHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPatientHistory viewPatientHistory = new ViewPatientHistory(frame);
            }
        });
        btnCreateAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAppointment createAppointment = new CreateAppointment(frame, lstAppointment ,CurrentUser.getCurrentUser().getUserId());
            }
        });
        btnCreatePrescription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreatePrescription createPrescription = new CreatePrescription(frame);
            }
        });
        btnCreateMedicine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get medicine name from dialog box
                String medicineName = JOptionPane.showInputDialog("Please type the medicine name: ");
                //create the medicine
                CreateNewMedicine createNewMedicine = (CreateNewMedicine) CurrentUser.getCommand("CreateNewMedicine");
                createNewMedicine.prepare(medicineName);
                createNewMedicine.perform();
                JSONWriter.writeMedicines();
                JOptionPane.showMessageDialog(null, "Medicine \"" + medicineName + "\" created");
            }
        });
    }

    private void updateList()
    {
        ((UpdateAppointmentList) CurrentUser.getCommand("UpdateAppointmentList")).setListToUpdate(lstAppointment);
        CurrentUser.getCommand("UpdateAppointmentList").perform();
    }
}
