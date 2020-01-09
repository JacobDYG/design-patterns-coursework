package controller.command;

import model.instance.CurrentData;
import model.stored.Appointment;
import model.stored.AppointmentRequest;
import model.stored.Request;
import model.stored.User;
import model.stored.role.DoctorRoleData;
import model.stored.role.PatientRoleData;
import model.stored.role.SecretaryRoleData;
import view.instance.CurrentUser;

import java.lang.reflect.AccessibleObject;
import java.util.Date;

public class ApproveAppointmentRequest implements ICommand {
    String name = "ApproveAppointmentRequest";
    int requestId;
    Date appointmentDate;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        //find the request
        AppointmentRequest appointmentRequest = null;
        for (Request request : ((SecretaryRoleData)CurrentUser.getCurrentUser().getRoleData()).getRequestList())
        {
            if (request.getRequestId() == requestId)
            {
                appointmentRequest = (AppointmentRequest)request;
            }
        }
        //find appointment id
        int appointmentId = -1;
        for (User user : CurrentData.getAllUsers())
        {
            if (user.getRoleData().equals("Doctor"))
            {
                DoctorRoleData doctorRoleData = (DoctorRoleData)user.getRoleData();
                for (Appointment appointment : doctorRoleData.getAppointmentList())
                {
                    if (appointment.getAppointmentId() > appointmentId)
                        appointmentId = appointment.getAppointmentId();
                }
            }
        }
        //create appointment
        if(appointmentRequest != null) {
            Appointment newAppointment = new Appointment(appointmentId, appointmentRequest.getDoctorId(), appointmentRequest.getPatientId(), appointmentDate);
            //add appointment
            for (User user : CurrentData.getAllUsers())
            {
                if (user.getUserId() == appointmentRequest.getDoctorId())
                {
                    ((DoctorRoleData)user.getRoleData()).addAppointment(newAppointment);
                }
                if (user.getUserId() == appointmentRequest.getPatientId())
                {
                    ((PatientRoleData)user.getRoleData()).addAppointment(newAppointment);
                }
            }
        }
        //remove the request
        ((SecretaryRoleData) CurrentUser.getCurrentUser().getRoleData()).removeRequest(appointmentRequest.getRequestId());
    }

    public void prepare(int requestId, Date appointmentDate)
    {
        this.requestId = requestId;
        this.appointmentDate = appointmentDate;
    }
}
