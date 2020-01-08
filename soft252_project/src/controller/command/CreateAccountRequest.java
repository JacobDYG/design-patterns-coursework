package controller.command;

import model.instance.CurrentData;
import model.stored.AccountRequest;
import model.stored.AppointmentRequest;
import model.stored.User;
import model.stored.role.SecretaryRoleData;

public class CreateAccountRequest implements ICommand{
    String name = "CreateAccountRequest";
    User requestedUser;
    boolean deletionRequest;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        //find a request id and secretary
        int requestId = -1;
        SecretaryRoleData secretary = null;
        int noRequests = Integer.MAX_VALUE;
        for (User user : CurrentData.getAllUsers())
        {
            if (user.getRole().equals("Secretary"))
            {
                SecretaryRoleData secretaryRoleData = (SecretaryRoleData)user.getRoleData();
                for (model.stored.Request request : secretaryRoleData.getRequestList())
                {
                    if (request.getRequestId() > requestId)
                        requestId = request.getRequestId();
                }
                //find the secretary with the fewest requests
                if (secretaryRoleData.getRequestList().size() < noRequests)
                {
                    noRequests = secretaryRoleData.getRequestList().size();
                    secretary = secretaryRoleData;
                }
            }
        }
        //create account request
        AccountRequest accountRequest = new AccountRequest(requestId + 1, requestedUser, deletionRequest);
        //assign to the secretary
        if (secretary != null)
            secretary.addRequest(accountRequest);
    }


    public void prepare(User requestedUser, boolean deletionRequest)
    {
        this.requestedUser = requestedUser;
        this.deletionRequest = deletionRequest;
    }
}
