package controller.command;

import model.stored.Feedback;
import model.stored.role.AdminRoleData;
import view.instance.CurrentUser;

import javax.swing.*;
import java.util.List;

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
