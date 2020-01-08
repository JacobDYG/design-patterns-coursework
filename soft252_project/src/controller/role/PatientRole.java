package controller.role;

import controller.command.*;
import model.stored.Appointment;
import model.stored.Prescription;

import java.util.ArrayList;
import java.util.List;

public class PatientRole implements IRole {
    //The friendly name of this Role and the list of its allowed actions
    String name = "Patient Role";
    private List<ICommand> allowedCommands = new ArrayList<ICommand>();

    //Constructor - adds default actions to the list
    public PatientRole(){
        addDefaultActions();
    }

    //Adds the default actions to the allowedActions list.
    private void addDefaultActions(){
        allowedCommands.add(new UpdateAppointmentList());
        allowedCommands.add(new UpdatePatientHistoryForm());
        allowedCommands.add(new CreateAppointmentRequest());
        allowedCommands.add(new CreateFeedbackRequest());
        allowedCommands.add(new UpdateUsersCombo());
        allowedCommands.add(new CreateAccountRequest());
    }

    //Get the friendly name of this role
    @Override
    public String getName(){
        return name;
    }

    //Get an action from the list for execution
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
