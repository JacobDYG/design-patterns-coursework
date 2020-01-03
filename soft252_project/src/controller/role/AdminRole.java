package controller.role;

import controller.command.*;
import model.stored.Feedback;

import java.util.ArrayList;
import java.util.List;

public class AdminRole implements IRole {
    //The friendly name of this Role and the list of its allowed actions
    String name = "Admin Role";
    private List<ICommand> allowedCommands = new ArrayList<ICommand>();
    //Admin specific data
    private List<Feedback>  pendingFeedbackList = new ArrayList<>();

    //Constructor - adds default actions to the list
    public AdminRole(){
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

    //Add a feedback
    public void addFeedback(Feedback inputFeedback)
    {
        pendingFeedbackList.add(inputFeedback);
    }

    //Remove a feedback
    public boolean removeFeedback(int feedbackId)
    {
        return pendingFeedbackList.removeIf(feedback -> feedback.getFeedbackId() == feedbackId);
    }

    //Return the feedback list for inspection
    public List<Feedback> getFeedbackList()
    {
        return pendingFeedbackList;
    }
}
