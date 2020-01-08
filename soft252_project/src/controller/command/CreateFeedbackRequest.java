package controller.command;

import model.instance.CurrentData;
import model.stored.Feedback;
import model.stored.Request;
import model.stored.User;
import model.stored.role.AdminRoleData;

public class CreateFeedbackRequest implements ICommand{
    String name = "CreateFeedbackRequest";
    int doctorId;
    int rating;
    String feedbackNotes;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        //find feedback id and admin
        int feedbackId = -1;
        AdminRoleData admin = null;
        int noRequests = Integer.MAX_VALUE;
        for (User user : CurrentData.getAllUsers())
        {
            if (user.getRole().equals("Admin"))
            {
                AdminRoleData adminRoleData = (AdminRoleData)user.getRoleData();
                for (Feedback feedback : adminRoleData.getFeedbackList())
                {
                    if (feedback.getFeedbackId() > feedbackId)
                        feedbackId = feedback.getFeedbackId();
                }
                //find the admin with the fewest requests
                if (adminRoleData.getFeedbackList().size() < noRequests)
                {
                    noRequests = adminRoleData.getFeedbackList().size();
                    admin = adminRoleData;
                }
            }
        }
        //create feedback
        Feedback requestedFeedback = new Feedback(feedbackId + 1, doctorId, rating, feedbackNotes);
        //assign to the admin
        if (admin != null)
            admin.addFeedback(requestedFeedback);
    }

    public void prepare(int doctorId, int rating, String feedbackNotes)
    {
        this.doctorId = doctorId;
        this.rating = rating;
        this.feedbackNotes = feedbackNotes;
    }
}
