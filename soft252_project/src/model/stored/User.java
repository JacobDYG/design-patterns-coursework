package model.stored;

import controller.role.PatientRole;
import model.stored.role.IRoleData;
import model.stored.role.PatientRoleData;

public class User {
    //Users role
    private String role;
    //Users private data
    private IRoleData roleData;
    private int userId;
    private String name;
    private String address;
    private String gender;
    private int age;

    //Constructor for patient
    public User(String role, PatientRole roleData, int userId, String name, String address, String gender, int age) {
        if (role == "Patient")
        {
            this.role = role;
            this.userId = userId;
            this.name = name;
            this.address = address;
            this.gender = gender;
            this.age = age;
        }
        else
        {
            System.out.println("Invalid parameters were provided for this type of user.");
        }

    }
    //Constructor for non patients
    public User(String role, IRoleData roleData, int userId, String name, String address) {
        if (role != "Patient" && !(roleData instanceof PatientRoleData))
        {
            this.role = role;
            this.userId = userId;
            this.name = name;
            this.address = address;
        }
        else
        {
            System.out.println("Invalid parameters were provided for this type of user.");
        }
    }

    //Getters for all attributes
    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    //Setters for all attributes except userId, as this does not need to change
    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void testSingleton()
    {
        System.out.println("Success!");
    }
}
