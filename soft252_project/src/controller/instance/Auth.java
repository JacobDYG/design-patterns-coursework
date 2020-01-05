package controller.instance;

import model.instance.CurrentData;
import model.stored.User;
import model.stored.role.AdminRoleData;

public class Auth {
    public static User login(String username, String password)
    {
        //Checks all the users in the system for a match, and returns the user if so.
        for (User thisUser : CurrentData.getAllUsers())
        {
            if (thisUser.getUsername() == username && thisUser.getPassword() == password)
                return thisUser;
        }
        return null;
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
    public static int findUserId()
    {
        int userId = -1;
        //Checks all the users in the system to find the biggest user id.
        for (User thisUser : CurrentData.getAllUsers())
        {
            if (thisUser.getUserId() > userId)
                userId = thisUser.getUserId();
        }
        return userId + 1;
    }
}
