package controller.role;

import controller.command.*;
import model.stored.Request;

import java.util.ArrayList;
import java.util.List;

public class SecretaryRole implements IRole {
    //The friendly name of this Role and the list of its allowed actions
    String name = "Secretary Role";
    private List<ICommand> allowedCommands = new ArrayList<ICommand>();

    //Constructor - adds default actions to the list
    public SecretaryRole(){
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
