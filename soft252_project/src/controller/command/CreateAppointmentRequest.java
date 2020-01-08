package controller.command;

import model.instance.CurrentData;
import model.stored.AppointmentRequest;
import model.stored.User;
import model.stored.role.DoctorRoleData;
import model.stored.role.SecretaryRoleData;

import java.util.Date;

public class CreateAppointmentRequest implements ICommand {
    String name = "CreateAppointmentRequest";
    int patientId;
    int doctorId;
    Date possibleDatesStart;
    Date possibleDatesEnd;

    public String getName()
    {
        return name;
    }

    public void perform()
    {
        //find a request id and secretary
        int requestId = -1;
        SecretaryRoleData secretary = null;
        int noRequests = Integer.MAX_VALUE;
        for (User user : CurrentData.getAllUsers())
        {
            if (user.getRole().equals("Secretary"))
            {
                SecretaryRoleData secretaryRoleData = (SecretaryRoleData)user.getRoleData();
                for (model.stored.Request request : secretaryRoleData.getRequestList())
                {
                    if (request.getRequestId() > requestId)
                        requestId = request.getRequestId();
                }
                //find the secretary with the fewest requests
                if (secretaryRoleData.getRequestList().size() < noRequests)
                {
                    noRequests = secretaryRoleData.getRequestList().size();
                    secretary = secretaryRoleData;
                }
            }
        }
        //create appointment request
        AppointmentRequest appointmentRequest = new AppointmentRequest(requestId + 1, patientId, doctorId, possibleDatesStart, possibleDatesEnd);
        //assign to the secretary
        if (secretary != null)
            secretary.addRequest(appointmentRequest);
    }

    public void prepare(int patientId, int doctorId, Date possibleDatesStart, Date possibleDatesEnd)
    {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.possibleDatesStart = possibleDatesStart;
        this.possibleDatesEnd = possibleDatesEnd;
        if (doctorId == -1)
        {
            //the patient did not choose a doctor, one must be selected automatically
            int noAppointments = Integer.MAX_VALUE;
            for (User user : CurrentData.getAllUsers())
            {
                if (user.getRole().equals("Doctor"))
                {
                    DoctorRoleData doctorRoleData = (DoctorRoleData) user.getRoleData();
                    //find the doctor with the fewest appointments
                    if (doctorRoleData.getAppointmentList().size() < noAppointments)
                    {
                        noAppointments = doctorRoleData.getAppointmentList().size();
                        doctorId = user.getUserId();
                    }
                }
            }
        }
    }
}
