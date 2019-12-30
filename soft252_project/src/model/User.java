package model;

import java.sql.SQLOutput;

public class User {
    private IRole role;
    private String userName;

    public User(String userType, String inputUserName)
    {
        switch(userType)
        {
            case "Admin":
                role = new AdminRole();
                break;
            case "Secretary":
                role = new SecretaryRole();
                break;
            case "Patient":
                role = new PatientRole();
                break;
            case "Doctor":
                role = new DoctorRole();
                break;
        }
        userName = inputUserName;
        System.out.println("User " + userName + " created with role: " + role.getName());
    }

    public IRole getRole()
    {
        return role;
    }
}
