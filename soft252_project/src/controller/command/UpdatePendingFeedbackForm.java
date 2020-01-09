package controller.command;

import model.stored.Feedback;
import model.stored.role.AdminRoleData;
import view.instance.CurrentUser;

import javax.swing.*;
import java.util.List;

//loads patient feedback into a provided JTextField and JTextArea for sending feedback onwards to a doctor
//populates the fields with notes from the patient which can be modified by the admin
public class UpdatePendingFeedbackForm implements ICommand {
    String name = "UpdatePendingFeedbackForm";
    Feedback feedback;
    int feedbackId;
    JTextField txtRating;
    JTextArea txtFeedbackNotes;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform() {
        List<Feedback> feedbackList = ((AdminRoleData) CurrentUser.getCurrentUser().getRoleData()).getFeedbackList();
        //iterate through all feedback to find the feedback
        for (Feedback feedback : feedbackList)
        {
            if(feedback.getFeedbackId() == feedbackId)
                this.feedback = feedback;
        }
        txtRating.setText(Integer.toString(feedback.getRating()));
        txtFeedbackNotes.setText(feedback.getFeedbackNotes());
    }

    public void setFeedbackId(int feedbackId)
    {
        this.feedbackId = feedbackId;
    }

    public void setTxtRating(JTextField txtRating) {
        this.txtRating = txtRating;
    }

    public void setTxtFeedbackNotes(JTextArea txtFeedbackNotes) {
        this.txtFeedbackNotes = txtFeedbackNotes;
    }
}
