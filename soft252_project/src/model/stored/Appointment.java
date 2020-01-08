package model.stored;

import java.util.Date;

public class Appointment {
    private int appointmentId;
    private int doctorId;
    private int patientId;
    private Date appointmentDate;
    private String appointmentNotes;

    public Appointment(int appointmentId, int doctorId, int patientId, Date appointmentDate) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
    }

    public Appointment(int appointmentId, int doctorId, int patientId, Date appointmentDate, String appointmentNotes) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
        this.appointmentNotes = appointmentNotes;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentNotes() {
        return appointmentNotes;
    }

    public void setAppointmentNotes(String appointmentNotes) {
        this.appointmentNotes = appointmentNotes;
    }
}
