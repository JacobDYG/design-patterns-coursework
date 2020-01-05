package model.stored;

import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private int prescriptionId;
    private int doctorId;
    private int patientId;
    private String doctorNotes;
    private List<PrescribedMedicine> prescribedMedicineList = new ArrayList<>();

    public Prescription(int prescriptionId, int doctorId, int patientId, List<PrescribedMedicine> prescribedMedicineList) {
        this.prescriptionId = prescriptionId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.prescribedMedicineList = prescribedMedicineList;
    }

    public int getPrescriptionId()
    {
        return prescriptionId;
    }

    public List<PrescribedMedicine> getPrescribedMedicineList() {
        return prescribedMedicineList;
    }

    public boolean addMedicine(Medicine inputMedicine, int inputQuantity, String inputDosage)
    {
        return prescribedMedicineList.add(new PrescribedMedicine(inputMedicine, inputQuantity, inputDosage));
    }

    public boolean removeMedicine(int inputMedicineId)
    {
        return prescribedMedicineList.removeIf(prescribedMedicine -> prescribedMedicine.getMedicine().getMedicineId() == inputMedicineId);
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }
}
