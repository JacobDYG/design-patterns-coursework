package model.stored;

import java.util.Date;

public class AppointmentRequest extends Request {

    private int patientId;
    private int doctorId;
    private Date possibleDatesStart;
    private Date possibleDatesEnd;

    public AppointmentRequest(int requestId, int patientId, int doctorId, Date possibleDatesStart, Date possibleDatesEnd) {
        super(requestId);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.possibleDatesStart = possibleDatesStart;
        this.possibleDatesEnd = possibleDatesEnd;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public Date getPossibleDatesStart() {
        return possibleDatesStart;
    }

    public Date getPossibleDatesEnd() {
        return possibleDatesEnd;
    }

    public void approve(Date inputAppointmentDate)
    {

    }
}
