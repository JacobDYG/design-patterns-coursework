package controller.command;

import model.stored.role.AdminRoleData;
import view.instance.CurrentUser;

import javax.swing.*;

public class UpdatePendingFeedbackList implements ICommand {
    String name = "UpdatePendingFeedbackList";
    JList listToUpdate;

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void perform()
    {
        ((AdminRoleData)CurrentUser.getCurrentUser().getRoleData()).updatePendingFeedbackView(listToUpdate);
    }

    public void setListToUpdate(JList listToUpdate)
    {
        this.listToUpdate = listToUpdate;
    }
}
