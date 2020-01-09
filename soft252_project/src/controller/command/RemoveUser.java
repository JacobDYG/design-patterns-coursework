package controller.command;

import model.instance.CurrentData;
import model.stored.*;
import model.stored.role.AdminRoleData;
import model.stored.role.SecretaryRoleData;

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
        //Check no requests
        for (User user: CurrentData.getAllUsers())
        {
            if (user.getRole().equals("Secretary"))
            {
                SecretaryRoleData thisSecretary = (SecretaryRoleData)user.getRoleData();
                for (Request request : thisSecretary.getRequestList())
                {
                    if (request instanceof AppointmentRequest)
                    {
                        int thisUserId = ((AppointmentRequest) request).getPatientId();
                        if (thisUserId == userId)
                            thisSecretary.removeRequest(request.getRequestId());
                    }
                    else if (request instanceof AccountRequest)
                    {
                        if (((AccountRequest)request).getRequestedUser().getUserId() == userId)
                        {
                            thisSecretary.removeRequest(request.getRequestId());
                        }
                    }
                }
            }
        }
    }

    public void setUser(int userId)
    {
        this.userId = userId;
    }
}
