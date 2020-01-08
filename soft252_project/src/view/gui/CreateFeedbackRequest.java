package view.gui;

import controller.command.UpdateUsersCombo;
import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateFeedbackRequest {
    private JPanel pnlCreateFeedbackRequest;
    private JLabel lblCreateFeedbackRequest;
    private JComboBox cmbDoctor;
    private JLabel lblDoctor;
    private JComboBox cmbRating;
    private JLabel lblRating;
    private JLabel lblNotes;
    private JTextArea txtNotes;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JFrame frame;

    public CreateFeedbackRequest(JFrame oldForm) {
        //Initialize  this form
        frame = new JFrame("Create Feedback Request");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 600, 400);
        frame.setContentPane(pnlCreateFeedbackRequest);
        frame.setVisible(true);
        oldForm.setVisible(false);

        //populate doctors
        UpdateUsersCombo updateUsersCombo = (UpdateUsersCombo) CurrentUser.getCommand("UpdateUsersCombo");
        updateUsersCombo.prepare(cmbDoctor, false, false, true, false);
        updateUsersCombo.perform();

        //populate ratings
        DefaultComboBoxModel ratingsModel = new DefaultComboBoxModel();
        ratingsModel.addElement(1);
        ratingsModel.addElement(2);
        ratingsModel.addElement(3);
        ratingsModel.addElement(4);
        ratingsModel.addElement(5);
        cmbRating.setModel(ratingsModel);

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
                //get doctor id
                int doctorId;
                String selectedValue = (String) cmbDoctor.getSelectedItem();
                int endIndex = selectedValue.indexOf(",");
                doctorId = Integer.parseInt(selectedValue.substring(9, endIndex));

                //create feedback
                controller.command.CreateFeedbackRequest createFeedbackRequest = (controller.command.CreateFeedbackRequest)CurrentUser.getCommand("CreateFeedbackRequest");
                createFeedbackRequest.prepare(doctorId, (int)cmbRating.getSelectedItem(), txtNotes.getText());
                createFeedbackRequest.perform();
                //write out and quit
                JSONWriter.writeUsers();
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
    }
}
