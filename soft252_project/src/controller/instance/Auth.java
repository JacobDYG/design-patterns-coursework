package controller.instance;

import model.instance.CurrentData;
import model.stored.AccountRequest;
import model.stored.Request;
import model.stored.User;
import model.stored.role.AdminRoleData;
import model.stored.role.PatientRoleData;
import model.stored.role.SecretaryRoleData;
import view.instance.CurrentUser;

public class Auth {
    public static void login(String username, String password)
    {
        //Checks all the users in the system for a match, and returns the user if so.
        for (User thisUser : CurrentData.getAllUsers())
        {
            if (thisUser.getUsername().equals(username)  && thisUser.getPassword().equals(password))
            {
                CurrentUser.setCurrentUser(thisUser);
                break;
            }
        }
    }
    public static boolean adminCheck()
    {
        for (User thisUser : CurrentData.getAllUsers())
        {
            //Check every user to see if there is an admin
            if (thisUser.getRole().equals("Admin"))
                return true;
        }
        return false;
    }
    public static void createAdmin(String username, String password, String name, String address)
    {
        User newAdmin = new User("Admin", username, password, new AdminRoleData(), findUserId(),  name, address);
        CurrentData.addUser(newAdmin);
    }
    public static void createPatientRequest(String username, String password, String name, String address, String gender, int age)
    {
        User newPatient = new User("Patient", username, password, new PatientRoleData(), findUserId(), name, address, gender, age);
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
        AccountRequest accountRequest = new AccountRequest(requestId + 1, newPatient, false);
        //assign to the secretary
        if (secretary != null)
            secretary.addRequest(accountRequest);
    }
    public static int findUserId()
    {
        int userId = -1;
        //Checks all the users in the system to find the biggest user id.
        for (User thisUser : CurrentData.getAllUsers())
        {
            if (thisUser.getUserId() > userId)
                userId = thisUser.getUserId();
            if (thisUser.getRole().equals("Secretary"))
            {
                SecretaryRoleData secretaryRoleData = (SecretaryRoleData)thisUser.getRoleData();
                for (Request request : secretaryRoleData.getRequestList())
                {
                    if (request instanceof AccountRequest)
                    {
                        if (((AccountRequest) request).getRequestedUser().getUserId() > userId)
                            userId = ((AccountRequest) request).getRequestedUser().getUserId();
                    }
                }
            }
        }
        return userId + 1;
    }
}
