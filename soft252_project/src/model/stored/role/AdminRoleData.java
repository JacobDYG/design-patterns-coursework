package model.stored.role;

import model.instance.CurrentData;
import model.stored.Feedback;
import model.stored.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRoleData implements IRoleData {

    //Admin specific data
    private List<Feedback> pendingFeedbackList = new ArrayList<>();

    public AdminRoleData() {
    }

    //Add a feedback
    public void addFeedback(Feedback inputFeedback)
    {
        pendingFeedbackList.add(inputFeedback);
    }

    //Remove a feedback
    public boolean removeFeedback(int feedbackId)
    {
        return pendingFeedbackList.removeIf(feedback -> feedback.getFeedbackId() == feedbackId);
    }

    //Return the feedback list for inspection
    public List<Feedback> getFeedbackList()
    {
        return pendingFeedbackList;
    }

    public void updatePendingFeedbackView(JList lstPendingFeedback)
    {
        DefaultListModel listModel = new DefaultListModel<>();
        for (Feedback feedback : pendingFeedbackList)
        {
            User doctor = null;
            for (User user : CurrentData.getAllUsers())
            {
                if (user.getUserId() == feedback.getDoctorUserId())
                    doctor = user;
            }
            listModel.addElement("Feedback ID: " + feedback.getFeedbackId() + ", Doctor ID: "  + feedback.getDoctorUserId() + ", Doctor Name: " + doctor.getName() + ", Rating: " + feedback.getRating() + ", Notes: " + feedback.getFeedbackNotes());
        }
        lstPendingFeedback.setModel(listModel);
    }
}
