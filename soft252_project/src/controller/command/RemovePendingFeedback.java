package controller.command;

import model.stored.Feedback;
import model.stored.role.AdminRoleData;
import view.instance.CurrentUser;

import java.util.List;

public class RemovePendingFeedback implements ICommand {
    String name = "RemovePendingFeedback";
    List<Feedback> pendingFeedbackList;
    int feedbackId;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        pendingFeedbackList = ((AdminRoleData)CurrentUser.getCurrentUser().getRoleData()).getFeedbackList();
        pendingFeedbackList.removeIf(feedback -> feedback.getFeedbackId() == feedbackId);
    }

    public void setFeedbackId(int feedbackId)
    {
        this.feedbackId = feedbackId;
    }
}
