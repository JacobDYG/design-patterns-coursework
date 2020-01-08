package model.stored.role;

import model.instance.CurrentData;
import model.stored.Appointment;
import model.stored.Feedback;
import model.stored.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctorRoleData implements IRoleData {
    //Doctor specific data
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Feedback> feedbackList = new ArrayList<>();

    public DoctorRoleData() {
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

    public void updateAppointmentList(JList lstAppointment)
    {
        DefaultListModel listModel = new DefaultListModel<>();
        for (Appointment appointment : appointmentList)
        {
            User patient = null;
            for (User user : CurrentData.getAllUsers())
            {
                if (user.getUserId() == appointment.getPatientId())
                    patient = user;
            }
            Date now = new Date();
            if (appointment.getAppointmentDate().compareTo(now) >= 0)
            listModel.addElement("Appointment ID: " + appointment.getAppointmentId() + ", Patient ID: "  + appointment.getPatientId() + ", Patient Name: " + patient.getName() + ", Date/Time: " + appointment.getAppointmentDate());
        }
        lstAppointment.setModel(listModel);
    }
}
