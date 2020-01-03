package model.instance;

import model.Medicine;
import model.User;

import java.util.ArrayList;
import java.util.List;

//This class contains all data represented in the system as most recently loaded
//Changes made by Command classes are reflected here, eg edit existing data, add
//new data using the model classes, delete data.
//Changes to this class update the view and the write-out to JSON also.
public class CurrentData {
    private static List<User> allUsers = new ArrayList<>();
    private static List<Medicine> allMedicines = new ArrayList<>();

    private CurrentData() { }

    //Getters for users and medicines
    public static List<User> getAllUsers() {
        return allUsers;
    }

    public static List<Medicine> getAllMedicines() {
        return allMedicines;
    }
    //Setters for users and medicines. Setters must be used, there is no constructor
    //As this class follows the singleton pattern
    public static void setAllUsers(List<User> inputAllUsers) {
        allUsers = inputAllUsers;
    }

    public static void setAllMedicines(List<Medicine> inputAllMedicines) {
        allMedicines = inputAllMedicines;
    }

    //Add a user
    public static void addUser(User inputUser)
    {
        allUsers.add(inputUser);
    }

    //Remove a user
    public static boolean removeUser(int userId)
    {
        return allUsers.removeIf(user -> user.getUserId() == userId);
    }

    //Return the user list for inspection
    public static List<User> getUserList()
    {
        return allUsers;
    }

    //Add a medicine
    public static void addMedicine(Medicine inputMedicine)
    {
        allMedicines.add(inputMedicine);
    }

    //Remove a medicine
    public static boolean removeMedicine(int medicineId)
    {
        return allMedicines.removeIf(medicine -> medicine.getMedicineId() == medicineId);
    }

    //Return the medicine list for inspection
    public static List<Medicine> getMedicineList()
    {
        return allMedicines;
    }
}
