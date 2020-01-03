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
    //Doctor specific data
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Feedback> feedbackList = new ArrayList<>();

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

    //Add an appointment
    public void addAppointment(Appointment inputAppointment)
    {
        appointmentList.add(inputAppointment);
    }

    //Remove an appointment
    public boolean removeAppointment(int appointmentId)
    {
        return appointmentList.removeIf(appointment -> appointment.getAppointmentId() == appointmentId);
    }

    //Return the appointments list for inspection
    public List<Appointment> getAppointmentList()
    {
        return appointmentList;
    }

    //Add a feedback
    public void addFeedback(Feedback inputFeedback)
    {
        feedbackList.add(inputFeedback);
    }

    //Remove a feedback
    public boolean removeFeedback(int feedbackId)
    {
        return feedbackList.removeIf(feedback -> feedback.getFeedbackId() == feedbackId);
    }

    //Return the feedback list for inspection
    public List<Feedback> getFeedbackList()
    {
        return feedbackList;
    }
}
