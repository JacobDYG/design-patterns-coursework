package controller.command;

import model.stored.Appointment;
import model.stored.role.DoctorRoleData;
import view.instance.CurrentUser;

import javax.swing.*;
import java.util.List;

public class UpdateAppointmentNotes implements ICommand {
    //updates an appointment with data entered by a doctor in a form, from the passed JTextArea
    String name = "UpdateAppointmentNotes";
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
        appointment.setAppointmentNotes(txtAppointmentNotes.getText());
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setTxtAppointmentNotes(JTextArea txtAppointmentNotes) {
        this.txtAppointmentNotes = txtAppointmentNotes;
    }
}
