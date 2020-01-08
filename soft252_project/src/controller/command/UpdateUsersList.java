package controller.command;

import model.instance.CurrentData;
import model.stored.User;

import javax.swing.*;

public class UpdateUsersList implements ICommand {
    String name = "UpdateUsersList";
    DefaultListModel userList;
    JList listToUpdate;

    @Override
    public String getName() {
        return name;
    }

    public void perform()
    {
        listToUpdate.setModel(userList);
    }

    public void prepare(JList listToUpdate, boolean admin, boolean secretary, boolean doctor, boolean patient)
    {
        userList = new DefaultListModel();
        this.listToUpdate = listToUpdate;
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
