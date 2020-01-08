package view.gui;

import controller.command.RemovePendingFeedback;
import controller.command.UpdatePendingFeedbackForm;
import controller.command.UpdatePendingFeedbackList;
import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateFeedback {
    private JLabel lblCreateFeedback;
    private JLabel lblPatientRating;
    private JTextField txtRating;
    private JLabel lblNotes;
    private JTextArea txtFeedbackNotes;
    private JButton btnCreateFeedback;
    private JButton btnCancel;
    private JButton btnDelete;
    private JPanel pnlCreateFeedback;
    private JFrame frame;
    private UpdatePendingFeedbackList updatePendingFeedbackList;
    private JList pendingFeedbackList;
    private JFrame oldForm;
    private int feedbackId;

    public CreateFeedback(JFrame oldForm, int feedbackId, JList pendingFeedbackList, int doctorId) {
        //Initialize  this form
        frame = new JFrame("Create feedback");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.setContentPane(pnlCreateFeedback);
        frame.setVisible(true);
        oldForm.setVisible(false);
        this.oldForm = oldForm;
        this.feedbackId = feedbackId;

        //for updating previous form after adding/deleting feedback
        this.pendingFeedbackList = pendingFeedbackList;

        //Populate data
        UpdatePendingFeedbackForm updatePendingFeedbackForm = (UpdatePendingFeedbackForm) CurrentUser.getCommand("UpdatePendingFeedbackForm");
        updatePendingFeedbackForm.setFeedbackId(feedbackId);
        updatePendingFeedbackForm.setTxtRating(txtRating);
        updatePendingFeedbackForm.setTxtFeedbackNotes(txtFeedbackNotes);
        updatePendingFeedbackForm.perform();

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePendingFeedback();
            }
        });
        btnCreateFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.command.CreateFeedback createFeedback = (controller.command.CreateFeedback)CurrentUser.getCommand("CreateFeedback");
                createFeedback.setDoctorId(doctorId);
                createFeedback.setRating(Integer.parseInt(txtRating.getText()));
                createFeedback.setFeedbackNotes(txtFeedbackNotes.getText());
                createFeedback.perform();

                removePendingFeedback();
            }
        });
    }

    private void updateParent()
    {
        updatePendingFeedbackList = (UpdatePendingFeedbackList)CurrentUser.getCommand("UpdatePendingFeedbackList");
        updatePendingFeedbackList.setListToUpdate(pendingFeedbackList);
        updatePendingFeedbackList.perform();
    }

    private void removePendingFeedback()
    {
        RemovePendingFeedback removePendingFeedback = (RemovePendingFeedback)CurrentUser.getCommand("RemovePendingFeedback");
        removePendingFeedback.setFeedbackId(feedbackId);
        removePendingFeedback.perform();
        //Update parent form
        updateParent();

        JSONWriter.writeUsers();

        oldForm.setVisible(true);
        frame.dispose();
    }
}
