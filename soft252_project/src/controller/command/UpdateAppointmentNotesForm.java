package controller.command;

import model.stored.Appointment;
import model.stored.Feedback;
import model.stored.role.AdminRoleData;
import model.stored.role.DoctorRoleData;
import view.instance.CurrentUser;

import javax.swing.*;
import java.util.List;

public class UpdateAppointmentNotesForm implements ICommand {
    //updates the appointment notes form text area with any notes that may have already been written
    String name = "UpdateAppointmentNotesForm";
    Appointment appointment;
    int appointmentId;
    JTextArea txtAppointmentNotes;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform() {
        List<Appointment> appointmentList = ((DoctorRoleData) CurrentUser.getCurrentUser().getRoleData()).getAppointmentList();
        //iterate through all appointments to find the relevant one
        for (Appointment appointment : appointmentList)
        {
            if(appointment.getAppointmentId() == appointmentId)
                this.appointment = appointment;
        }
        if (appointment.getAppointmentNotes() != null)
        {
            txtAppointmentNotes.setText(appointment.getAppointmentNotes());
        }
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setTxtAppointmentNotes(JTextArea txtAppointmentNotes)
    {
        this.txtAppointmentNotes = txtAppointmentNotes;
    }
}
