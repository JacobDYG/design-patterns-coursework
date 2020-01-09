package controller.command;

import model.instance.CurrentData;
import model.stored.User;

import javax.swing.*;

//updates a provided list with the all users of specified type(s)
public class UpdateUsersList implements ICommand {
    String name = "UpdateUsersList";
    DefaultListModel userList;
    JList listToUpdate;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform()
    {
        listToUpdate.setModel(userList);
    }

    public void prepare(JList listToUpdate, boolean admin, boolean secretary, boolean doctor, boolean patient)
    {
        userList = new DefaultListModel();
        this.listToUpdate = listToUpdate;
        //go through all users to find ones that meet the specified criteria and add them to the list
        for (User user: CurrentData.getAllUsers())
        {
             if (user.getRole().equals("Admin") && admin)
                 addToList(user);
             else if (user.getRole().equals("Secretary") && secretary)
                 addToList(user);
             else if (user.getRole().equals("Doctor") && doctor)
                 addToList(user);
             else if (user.getRole().equals("Patient") && patient)
                 addToList(user);
        }
    }

    private void addToList(User user)
    {
        userList.addElement("User ID: " + user.getUserId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole() + ", Full Name: " + user.getName());
    }
}
