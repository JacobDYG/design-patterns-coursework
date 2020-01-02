package model.user;

import controller.*;
import model.system.Appointment;
import model.system.Feedback;
import model.system.Prescription;

import java.util.ArrayList;
import java.util.List;

public class DoctorRole implements IRole {
    //The friendly name of this Role and the list of its allowed actions
    String name = "Doctor Role";
    private List<IAction> allowedActions = new ArrayList<IAction>();
    //Doctor specific data
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Feedback> feedbackList = new ArrayList<>();

    //Constructor - adds default actions to the list
    DoctorRole(){
        addDefaultActions();
    }

    //Adds the default actions to the allowedActions list.
    void addDefaultActions(){
        allowedActions.add(new ExampleAction());
        allowedActions.add(new ExampleActionTwo());
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public IAction getAction(String request) {
        for (IAction action : allowedActions) {
            if (action.getName() == request){
                return action;
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
        /*
        This code block was replaced with the above, is kept here pending testing of above implementation.
        for (Appointment appointment : appointmentList)
        {
            if (appointment.getAppointmentId() == appointmentId)
            {
                appointmentList.remove(appointment);
                return true;
            }
        }
        return false;*/
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

    //Return the prescriptions list for inspection
    public List<Feedback> getFeedbackList()
    {
        return feedbackList;
    }
}
