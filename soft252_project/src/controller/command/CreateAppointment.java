package controller.command;

import model.instance.CurrentData;
import model.stored.Appointment;
import model.stored.User;
import model.stored.role.DoctorRoleData;
import model.stored.role.PatientRoleData;
import view.instance.CurrentUser;

import java.util.Date;
import java.util.List;

public class CreateAppointment implements ICommand {
    String name = "CreateAppointment";
    int doctorId;
    int patientId;
    Date appointmentDate;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform() {
        List<User> allUsers = CurrentData.getAllUsers();
        DoctorRoleData doctorRoleData = null;
        PatientRoleData patientRoleData = null;
        //Find relevant users
        for (User user : allUsers)
        {
            if(user.getUserId() == doctorId)
                doctorRoleData = (DoctorRoleData)user.getRoleData();
            else if(user.getUserId() == patientId)
                patientRoleData = (PatientRoleData)user.getRoleData();
        }
        //Find free appointment id
        int appointmentId = -1;
        for (User user : allUsers)
        {
            if (user.getRole().equals("Doctor"))
            {
                for(Appointment appointment : ((DoctorRoleData)user.getRoleData()).getAppointmentList())
                {
                    if (appointment.getAppointmentId() > appointmentId)
                        appointmentId = appointment.getAppointmentId();
                }
            }
        }
        Appointment appointment = new Appointment(appointmentId + 1, doctorId, patientId, appointmentDate);
        if (doctorRoleData != null && patientRoleData != null)
        {
            doctorRoleData.addAppointment(appointment);
            patientRoleData.addAppointment(appointment);
        }
    }

    public void prepare(int doctorId, int patientId, Date appointmentDate)
    {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
    }
}
