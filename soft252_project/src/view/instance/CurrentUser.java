package view.instance;

import controller.command.ICommand;
import controller.role.*;
import model.User;

    //Singleton implementation for running commands as a user.
    //Singleton is used as there should only be one user logged in at a time.
public class CurrentUser {
    //The logged in user
    private static User currentUser;
    //Users role - contains the actions they can perform, and their role specific data
    private static IRole userRole;

    //Constructor is private to stop instantiation
    private CurrentUser() { }

    public static User getCurrentUser()
    {
        return currentUser;
    }

    public String getRoleName()
    {
        return userRole.getName();
    }

    public ICommand getCommand(String request)
    {
        return userRole.getCommand(request);
    }

    public static void setCurrentUser(User newUser)
    {
        currentUser = newUser;
        String role = currentUser.getRole();
        switch(role)
        {
            case "Admin":
                userRole = new AdminRole();
            case "Secretary":
                userRole = new SecretaryRole();
            case "Patient":
                userRole = new PatientRole();
            case "Doctor":
                userRole = new DoctorRole();
        }
    }
}
