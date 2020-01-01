package model.user;

import controller.*;
import model.system.Appointment;
import model.system.Prescription;

import java.util.ArrayList;
import java.util.List;

public class PatientRole implements IRole {
    //The friendly name of this Role and the list of its allowed actions
    String name = "Patient Role";
    private List<IAction> allowedActions = new ArrayList<IAction>();
    //Patient specific data
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Prescription> prescriptionList = new ArrayList<>();

    //Constructor - adds default actions to the list
    PatientRole(){
        addDefaultActions();
    }

    //Adds the default actions to the allowedActions list.
    void addDefaultActions(){
        allowedActions.add(new ExampleAction());
        allowedActions.add(new ExampleActionTwo());
    }

    //Get the friendly name of this role
    @Override
    public String getName(){
        return name;
    }

    //Get an action from the list for execution
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

    //Add a prescription
    public void addPrescription(Prescription inputPrescription)
    {
        prescriptionList.add(inputPrescription);
    }

    //Remove a prescription
    public boolean removePrescription(int prescriptionId)
    {
        return prescriptionList.removeIf(prescription -> prescription.getPrescriptionId() == prescriptionId);
    }
    
    //Return the prescriptions list for inspection
    public List<Prescription> getPrescriptionList()
    {
        return prescriptionList;
    }
}
