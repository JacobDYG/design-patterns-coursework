package controller.command;

import model.instance.CurrentData;
import model.stored.Medicine;
import model.stored.PrescribedMedicine;
import model.stored.Prescription;
import model.stored.User;
import model.stored.role.PatientRoleData;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UpdatePrescriptionCreator implements ICommand{
    String name = "UpdatePrescriptionCreator";
    List<PrescribedMedicine> prescribedMedicineList;
    DefaultListModel prescribedMedicineListModel;
    JList listToUpdate;

    @Override
    public String getName(){
        return name;
    }

    @Override
    public void perform()
    {
        listToUpdate.setModel(prescribedMedicineListModel);
    }

    public void prepare(JList listToUpdate)
    {
        this.listToUpdate = listToUpdate;
        //Clear any previous list
        prescribedMedicineListModel = new DefaultListModel();
        prescribedMedicineList = new ArrayList<>();
    }

    public void addPrescribedMedicine(int medicineId, int quantity, String dosage)
    {
        //find the medicine
        Medicine thisMedicine = null;
        for (Medicine medicine : CurrentData.getAllMedicines())
        {
            if (medicine.getMedicineId() == medicineId)
                thisMedicine = medicine;
        }
        PrescribedMedicine prescribedMedicine = null;
        if (thisMedicine != null)
            prescribedMedicine = new PrescribedMedicine(thisMedicine, quantity, dosage);
        if (prescribedMedicine != null){
            prescribedMedicineList.add(prescribedMedicine);
            prescribedMedicineListModel.addElement("Medicine ID: " + medicineId + ", Medicine Name: " + thisMedicine.getMedicineName() + ", Quantity: " + quantity + ", Dosage: " + dosage);
        }
    }

    public void confirmPrescription(int patientId, int doctorId, String doctorNotes)
    {
        //find prescription id
        int prescriptionId = -1;
        for (User user : CurrentData.getAllUsers())
        {
            if (user.getRole().equals("Patient"))
            {
                PatientRoleData patientRoleDataFindId = (PatientRoleData)user.getRoleData();
                for (Prescription prescription : patientRoleDataFindId.getPrescriptionList())
                {
                    if(prescription.getPrescriptionId() > prescriptionId)
                        prescriptionId = prescription.getPrescriptionId();
                }
            }
        }
        //create the prescription
        Prescription prescription = new Prescription(prescriptionId + 1, doctorId, patientId, prescribedMedicineList);
        if (doctorNotes != null)
        {
            if (doctorNotes.length() > 0)
            {
                prescription.setDoctorNotes(doctorNotes);
            }
        }
        //add the prescription to the patients list
        //find the patient
        for (User user : CurrentData.getAllUsers())
        {
            if (user.getRole().equals("Patient"))
            {
                if (user.getUserId() == patientId)
                {
                    PatientRoleData patientRoleDataFindPatient = (PatientRoleData)user.getRoleData();
                    patientRoleDataFindPatient.addPrescription(prescription);
                }
            }
        }
    }
}
