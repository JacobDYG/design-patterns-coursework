package controller.role;

import controller.command.*;

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
        allowedCommands.add(new UpdateAccountRequestsList());
        allowedCommands.add(new UpdateMedicinesList());
        allowedCommands.add(new ApproveAccountRequest());
        allowedCommands.add(new UpdateMedicineQuantity());
        allowedCommands.add(new UpdateUsersList());
        allowedCommands.add(new RemoveUser());
        allowedCommands.add(new UpdateAppointmentRequestsList());
        allowedCommands.add(new ApproveAppointmentRequest());
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
