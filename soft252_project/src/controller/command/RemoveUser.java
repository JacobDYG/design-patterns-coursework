package controller.command;

import model.instance.CurrentData;
import model.stored.Feedback;
import model.stored.User;
import model.stored.role.AdminRoleData;

public class RemoveUser implements ICommand {
    String name = "RemoveUser";
    int userId;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform()
    {
        CurrentData.removeUser(userId);
        //Check no feedback exists relating to this user
        for (User user: CurrentData.getAllUsers())
        {
            if(user.getRole().equals("Admin"))
            {
                AdminRoleData roleData = (AdminRoleData)user.getRoleData();
                roleData.getFeedbackList().removeIf(feedback -> feedback.getDoctorUserId() == userId);
            }
        }
    }

    public void setUser(int userId)
    {
        this.userId = userId;
    }
}
