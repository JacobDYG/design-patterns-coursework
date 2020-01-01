package model.user;

public class User {
    //Users role - contains the actions they can perform, and their role specific data
    private IRole role;
    //Users private data
    private int userId;
    private String name;
    private String address;
    private String gender;
    private int age;

    public User(String userType, int inputUserId, String inputName, String inputAddress)
    {
        switch(userType)
        {
            case "Admin":
                role = new AdminRole();
                break;
            case "Secretary":
                role = new SecretaryRole();
                break;
            case "Doctor":
                role = new DoctorRole();
                break;
        }
        userId = inputUserId;
        name = inputName;
        address = inputAddress;
        System.out.println("User " + name + " created with role: " + role.getName());
    }

    public User(String userType, int inputUserId, String inputName, String inputAddress, String inputGender, int inputAge)
    {
        role = new PatientRole();
        userId = inputUserId;
        name = inputName;
        address = inputAddress;
        gender = inputGender;
        age = inputAge;
        System.out.println("User " + name + " created with role: " + role.getName());
    }

    public IRole getRole()
    {
        return role;
    }
}
