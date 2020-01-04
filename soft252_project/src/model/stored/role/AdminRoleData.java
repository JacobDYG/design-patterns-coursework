package model.stored.role;

import model.stored.Feedback;

import java.util.ArrayList;
import java.util.List;

public class AdminRoleData {

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
}
