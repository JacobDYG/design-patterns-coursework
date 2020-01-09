package view.gui;

import controller.command.ApproveAppointmentRequest;
import controller.command.UpdateAppointmentRequestsList;
import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageAppointmentRequests {
    private JPanel pnlAppointmentRequests;
    private JLabel lblManageAppointmentRequests;
    private JScrollPane scrAppointmentRequests;
    private JList lstAppointmentRequests;
    private JLabel lblDate;
    private JTextField txtDate;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JLabel lblAppointmentRequestList;
    private JFrame frame;
    private JFrame oldForm;

    public ManageAppointmentRequests(JFrame oldForm) {
        this.oldForm = oldForm;
        //Initialize  this form
        frame = new JFrame("Manage Appointment Requests");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1000, 600);
        frame.setContentPane(pnlAppointmentRequests);
        frame.setVisible(true);
        oldForm.setVisible(false);

        updateList();

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int requestId;
                if (!lstAppointmentRequests.isSelectionEmpty()) {
                    String selectedRequest = (String) lstAppointmentRequests.getSelectedValue();
                    int endIndex = selectedRequest.indexOf(",");
                    requestId = Integer.parseInt(selectedRequest.substring(12, endIndex));

                    Date appointmentDate;
                    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh:mm");
                    try {
                        appointmentDate = format.parse((String) txtDate.getText());
                        //if date was parsed, create the appointment
                        ApproveAppointmentRequest approveAppointmentRequest = (ApproveAppointmentRequest)CurrentUser.getCommand("ApproveAppointmentRequest");
                        approveAppointmentRequest.prepare(requestId, appointmentDate);
                        approveAppointmentRequest.perform();
                        //write out and refresh
                        JSONWriter.writeUsers();
                        updateList();
                    }
                    catch (Exception parseException)
                    {
                        JOptionPane.showMessageDialog(null, "Check the date format and try again");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Select a request and try again");
                }
            }
        });
    }

    private void updateList()
    {
        UpdateAppointmentRequestsList updateAppointmentRequestsList = (UpdateAppointmentRequestsList)CurrentUser.getCommand("UpdateAppointmentRequestsList");
        updateAppointmentRequestsList.prepare(lstAppointmentRequests);
        updateAppointmentRequestsList.perform();
    }
}
