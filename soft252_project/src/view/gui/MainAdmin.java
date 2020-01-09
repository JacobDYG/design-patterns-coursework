package view.gui;

import controller.command.UpdatePendingFeedbackList;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainAdmin {
    private JFrame frame;
    private JPanel pnlAdmin;
    private JButton btnAddUser;
    private JButton btnRemoveUser;
    private JButton btnLogout;
    private JButton btnCreateFeedback;
    private JList lstPendingFeedback;
    private JScrollPane scrFeedback;
    private JLabel lblPendingFeedback;
    private CreateUser createUser;
    private RemoveUser removeUser;
    private CreateFeedback createFeedback;

    public MainAdmin(JFrame oldForm) {
        //Initialize  this form
        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1000, 600);
        frame.setContentPane(pnlAdmin);
        frame.setVisible(true);
        oldForm.setVisible(false);
        //update the feedback list
        updateList();

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                CurrentUser.clearCurrentUser();
                frame.dispose();
            }
        });
        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser = new CreateUser(frame);
            }
        });
        btnRemoveUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser = new RemoveUser(frame, false, true, true, false);
            }
        });
        btnCreateFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!lstPendingFeedback.isSelectionEmpty())
                {
                    //find the feedback id
                    String selectedValue = (String)lstPendingFeedback.getSelectedValue();
                    int endIndex = selectedValue.indexOf(",");
                    int feedbackId = Integer.parseInt(selectedValue.substring(13, endIndex));
                    //find the doctor id
                    String subStringDoctorId = selectedValue.substring(27);
                    endIndex = subStringDoctorId.indexOf(",");
                    int doctorId = Integer.parseInt(subStringDoctorId.substring(0, endIndex));
                    createFeedback = new CreateFeedback(frame, feedbackId, lstPendingFeedback, doctorId);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No feedback is selected!");
                }

            }
        });
    }

    private void updateList()
    {
        ((UpdatePendingFeedbackList)CurrentUser.getCommand("UpdatePendingFeedbackList")).setListToUpdate(lstPendingFeedback);
        CurrentUser.getCommand("UpdatePendingFeedbackList").perform();
    }
}
