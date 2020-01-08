package model.stored.role;

import model.instance.CurrentData;
import model.stored.Appointment;
import model.stored.Prescription;
import model.stored.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientRoleData implements IRoleData {
    //Patient specific data
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Prescription> prescriptionList = new ArrayList<>();

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

    public void updateAppointmentList(JList lstAppointment)
    {
        DefaultListModel listModel = new DefaultListModel<>();
        for (Appointment appointment : appointmentList)
        {
            User doctor = null;
            for (User user : CurrentData.getAllUsers())
            {
                if (user.getUserId() == appointment.getDoctorId())
                    doctor = user;
            }
            Date now = new Date();
            if (appointment.getAppointmentDate().compareTo(now) >= 0)
                listModel.addElement("Appointment ID: " + appointment.getAppointmentId() + ", Doctor ID: "  + appointment.getDoctorId() + ", Doctor Name: " + doctor.getName() + ", Date/Time: " + appointment.getAppointmentDate());
        }
        lstAppointment.setModel(listModel);
    }
}
