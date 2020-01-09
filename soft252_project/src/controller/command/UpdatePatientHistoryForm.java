package controller.command;

import model.instance.CurrentData;
import model.stored.Appointment;
import model.stored.PrescribedMedicine;
import model.stored.Prescription;
import model.stored.User;
import model.stored.role.PatientRoleData;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public class UpdatePatientHistoryForm implements ICommand {
    String name = "UpdatePatientHistoryForm";
    int userId;
    JList prescriptionListToUpdate;
    JList appointmentListToUpdate;
    DefaultListModel prescriptionListModel;
    DefaultListModel appointmentListModel;
    List<Prescription> prescriptionList;
    List<Appointment> appointmentList;
    User patient;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform()
    {
        //find the patient
        for (User user : CurrentData.getAllUsers())
        {
            if (user.getUserId() == userId)
                patient = user;
        }
        //populate the list models
        prescriptionListModel = new DefaultListModel();
        appointmentListModel = new DefaultListModel();
        prescriptionList = ((PatientRoleData)patient.getRoleData()).getPrescriptionList();
        appointmentList = ((PatientRoleData)patient.getRoleData()).getAppointmentList();
        for (Appointment appointment : appointmentList)
        {
            if (appointment.getAppointmentDate().compareTo(new Date()) < 0)
            {
                appointmentListModel.addElement(
                        "Appointment ID: " + appointment.getAppointmentId() + ", Date/Time: " + appointment.getAppointmentDate() + ", Appointment Notes: " + appointment.getAppointmentNotes()
                );
            }
        }
        for (Prescription prescription : prescriptionList)
        {
            List<PrescribedMedicine> prescribedMedicineList = prescription.getPrescribedMedicineList();
            if (prescription.getDoctorNotes() != null)
                prescriptionListModel.addElement("Prescription ID: " + prescription.getPrescriptionId() + ", Doctor Notes: " + prescription.getDoctorNotes());
            else
                prescriptionListModel.addElement("Prescription ID: " + prescription.getPrescriptionId() + ", No notes have been made");
            for (PrescribedMedicine prescribedMedicine : prescribedMedicineList)
            {
                prescriptionListModel.addElement(
                        "Medicine: " + prescribedMedicine.getMedicine().getMedicineName() + ", Dosage: " + prescribedMedicine.getDosage() + ", Quantity: " + prescribedMedicine.getQuantity()
                );
            }
        }
        //set the lists
        prescriptionListToUpdate.setModel(prescriptionListModel);
        appointmentListToUpdate.setModel(appointmentListModel);
    }

    public void prepare(int userId, JList prescriptionList, JList appointmentList)
    {
        this.userId = userId;
        this.prescriptionListToUpdate = prescriptionList;
        this.appointmentListToUpdate = appointmentList;
    }

}
