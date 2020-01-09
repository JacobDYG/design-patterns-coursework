package controller.command;

import model.instance.CurrentData;
import model.stored.User;

import javax.swing.*;

//updates a passed combo box with all the users of the specified type
public class UpdateUsersCombo implements ICommand {
    String name = "UpdateUsersCombo";
    DefaultComboBoxModel userCombo;
    JComboBox comboToUpdate;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform()
    {
        comboToUpdate.setModel(userCombo);
    }

    public void prepare(JComboBox comboToUpdate, boolean admin, boolean secretary, boolean doctor, boolean patient)
    {
        userCombo = new DefaultComboBoxModel();
        this.comboToUpdate = comboToUpdate;
        //go through every user and add them to the list if they meet the specified criterea
        for (User user: CurrentData.getAllUsers())
        {
            if (user.getRole().equals("Admin") && admin)
                addToCombo(user);
            else if (user.getRole().equals("Secretary") && secretary)
                addToCombo(user);
            else if (user.getRole().equals("Doctor") && doctor)
                addToCombo(user);
            else if (user.getRole().equals("Patient") && patient)
                addToCombo(user);
        }
    }

    private void addToCombo(User user)
    {
        userCombo.addElement("User ID: " + user.getUserId() + ", Full Name: " + user.getName());
    }
}
