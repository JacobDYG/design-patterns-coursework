package controller.role;

import controller.command.*;
import model.stored.Feedback;

import java.util.ArrayList;
import java.util.List;

public class AdminRole implements IRole {
    //The friendly name of this Role and the list of its allowed actions
    String name = "Admin Role";
    private List<ICommand> allowedCommands = new ArrayList<ICommand>();

    //Constructor - adds default actions to the list
    public AdminRole(){
        addDefaultActions();
    }

    //Adds the default actions to the allowedActions list.
    private void addDefaultActions(){
        allowedCommands.add(new UpdatePendingFeedbackList());
        allowedCommands.add(new CreateUser());
        allowedCommands.add(new UpdateUsersList());
        allowedCommands.add(new RemoveUser());
        allowedCommands.add(new RemovePendingFeedback());
        allowedCommands.add(new UpdatePendingFeedbackForm());
        allowedCommands.add(new CreateFeedback());
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
