package controller.role;

import controller.command.*;
import model.stored.Appointment;
import model.stored.Feedback;

import java.util.ArrayList;
import java.util.List;

public class DoctorRole implements IRole {
    //The friendly name of this Role and the list of its allowed actions
    String name = "Doctor Role";
    private List<ICommand> allowedCommands = new ArrayList<ICommand>();

    //Constructor - adds default actions to the list
    public DoctorRole(){
        addDefaultActions();
    }

    //Adds the default actions to the allowedActions list.
    private void addDefaultActions(){
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public ICommand getCommand(String request) {
        for (ICommand command : allowedCommands) {
            if (command.getName() == request){
                return command;
            }
        }
        return null;
    }
}
