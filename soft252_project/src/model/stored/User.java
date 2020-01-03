package model.stored;

public class User {
    //Users role
    private String role;
    //Users private data
    private int userId;
    private String name;
    private String address;
    private String gender;
    private int age;

    //Constructor for patient
    public User(String role, int userId, String name, String address, String gender, int age) {
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
            this.role = role;
            this.userId = userId;
            this.name = name;
            this.address = address;
            System.out.println("Warning: Too many arguments were passed for this type of user. The user has been created with only the necessary data.");
        }

    }
    //Constructor for non patients
    public User(String role, int userId, String name, String address) {
        if (role != "Patient")
        {
            this.role = role;
            this.userId = userId;
            this.name = name;
            this.address = address;
        }
        else
        {
            System.out.println("Error: Essential arguments for this role were not provided, the user has not been created.");
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
