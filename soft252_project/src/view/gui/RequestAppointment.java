package view.gui;

import controller.command.CreateAppointmentRequest;
import controller.command.UpdateUsersCombo;
import controller.instance.JSONWriter;
import model.stored.AppointmentRequest;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestAppointment {
    private JPanel pnlRequestAppointment;
    private JLabel lblAppointmentRequest;
    private JButton btnRequest;
    private JButton btnCancel;
    private JCheckBox chkChooseDoctor;
    private JComboBox cmbChooseDoctor;
    private JLabel lblDoctor;
    private JTextField txtDateRangeEnd;
    private JTextField txtDateRangeStart;
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JLabel lblFormat1;
    private JLabel lblFormat2;
    private JFrame frame;

    public RequestAppointment(JFrame oldForm) {
        //Initialize  this form
        frame = new JFrame("Patient Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.setContentPane(pnlRequestAppointment);
        frame.setVisible(true);
        oldForm.setVisible(false);

        updateUserCombos();


        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
        chkChooseDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkChooseDoctor.isSelected())
                {
                    cmbChooseDoctor.setEnabled(true);
                }
                else
                {
                    cmbChooseDoctor.setEnabled(false);
                }
            }
        });
        btnRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAppointmentRequest createAppointmentRequest = (CreateAppointmentRequest) CurrentUser.getCommand("CreateAppointmentRequest");
                //check if doctor has been chosen
                int doctorId;
                if (chkChooseDoctor.isSelected())
                {
                    String selectedValue = (String) cmbChooseDoctor.getSelectedItem();
                    int endIndex = selectedValue.indexOf(",");
                    doctorId = Integer.parseInt(selectedValue.substring(9, endIndex));
                }
                else
                {
                    doctorId = -1;
                }
                //parse dates
                Date possibleDatesStart;
                Date possibleDatesEnd;
                SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
                try {
                    possibleDatesStart = format.parse(txtDateRangeStart.getText());
                    possibleDatesEnd = format.parse(txtDateRangeEnd.getText());
                    createAppointmentRequest.prepare(CurrentUser.getCurrentUser().getUserId(), doctorId, possibleDatesStart, possibleDatesEnd);
                    createAppointmentRequest.perform();
                    //write out and close this form
                    JSONWriter.writeUsers();
                    oldForm.setVisible(true);
                    frame.dispose();
                } catch (java.text.ParseException parseException) {
                    parseException.printStackTrace();
                    JOptionPane.showMessageDialog(null, "There is an issue with your date ranges - check they conform to DD-MM-YYYY and try again!");
                }
            }
        });
    }

    private void updateUserCombos() {
        //update doctor combo box
        UpdateUsersCombo updateDoctors = (UpdateUsersCombo)CurrentUser.getCommand("UpdateUsersCombo");
        updateDoctors.prepare(cmbChooseDoctor, false, false, true, false);
        updateDoctors.perform();
    }
}
