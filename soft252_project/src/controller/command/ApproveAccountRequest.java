package controller.command;

import model.instance.CurrentData;
import model.stored.AccountRequest;
import model.stored.AppointmentRequest;
import model.stored.Request;
import model.stored.User;
import model.stored.role.SecretaryRoleData;
import view.instance.CurrentUser;

public class ApproveAccountRequest implements ICommand {
    String name = "ApproveAccountRequest";
    int requestId;
    AccountRequest accountRequest;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        //find the request
        SecretaryRoleData secretaryRoleData = (SecretaryRoleData) CurrentUser.getCurrentUser().getRoleData();
        for (Request request : secretaryRoleData.getRequestList())
        {
            if(request instanceof  AccountRequest)
            {
                if (request.getRequestId() == requestId)
                    accountRequest = (AccountRequest)request;
            }
        }
        //if this is a deletion request
        if (accountRequest.isDeletionRequest())
        {
            CurrentData.removeUser(accountRequest.getRequestedUser().getUserId());
            //clean delete
            for(User user : CurrentData.getAllUsers())
            {
                if (user.getRole().equals("Secretary"))
                {
                    SecretaryRoleData thisSecretary = (SecretaryRoleData)user.getRoleData();
                    for (Request request : thisSecretary.getRequestList())
                    {
                        if (request instanceof AppointmentRequest)
                        {
                            if (((AppointmentRequest) request).getPatientId() == accountRequest.getRequestedUser().getUserId())
                            {
                                thisSecretary.removeRequest(accountRequest.getRequestId());
                            }
                        }
                        else if (request instanceof AccountRequest)
                        {
                            if (((AccountRequest)request).getRequestedUser() == accountRequest.getRequestedUser() && request.getRequestId() != requestId)
                            {
                                thisSecretary.removeRequest(request.getRequestId());
                            }
                        }
                    }
                }
            }
        }
        //if this is a creation request
        else
        {
            CurrentData.addUser(accountRequest.getRequestedUser());
        }
        //delete the request
        secretaryRoleData.removeRequest(requestId);
    }

    public void prepare(int requestId)
    {
        this.requestId = requestId;
    }
}
