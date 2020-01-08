package controller.command;

import controller.instance.Auth;
import model.instance.CurrentData;
import model.stored.User;
import model.stored.role.AdminRoleData;
import model.stored.role.DoctorRoleData;
import model.stored.role.PatientRoleData;
import model.stored.role.SecretaryRoleData;

public class CreateUser implements ICommand{
    private String name = "CreateUser";
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String address;
    private String gender;
    private int age;

    @Override
    public String getName() {
        return name;
    }

    public void perform()
    {
        if(role.equals("Patient"))
        {
            CurrentData.addUser(new User(role, username, password, new PatientRoleData(), Auth.findUserId(), fullName ,address, gender, age));
        }
        else if(role.equals("Secretary"))
        {
            CurrentData.addUser(new User(role, username, password, new SecretaryRoleData(), Auth.findUserId(), fullName ,address));
        }
        else if(role.equals("Doctor"))
        {
            CurrentData.addUser(new User(role, username, password, new DoctorRoleData(), Auth.findUserId(), fullName ,address));
        }
        else
        {
            CurrentData.addUser(new User(role, username, password, new AdminRoleData(), Auth.findUserId(), fullName ,address));
        }
    }

    public void setAttributes(String username, String password, String role, String fullName, String address, String gender, int age)
    {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.address = address;
        this.gender = gender;
        this.age = age;
    }
}
