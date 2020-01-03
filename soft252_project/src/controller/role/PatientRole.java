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
    //Patient specific data
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Prescription> prescriptionList = new ArrayList<>();

    //Constructor - adds default actions to the list
    public PatientRole(){
        addDefaultActions();
    }

    //Adds the default actions to the allowedActions list.
    private void addDefaultActions(){
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
