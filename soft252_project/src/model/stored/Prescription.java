package model.stored;

import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private int prescriptionId;
    private int doctorId;
    private int patientId;
    private String doctorNotes;
    private List<PrescribedMedicine> medicineList = new ArrayList<>();

    public Prescription(int prescriptionId, int doctorId, int patientId, List<PrescribedMedicine> medicineList) {
        this.prescriptionId = prescriptionId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicineList = medicineList;
    }

    public int getPrescriptionId()
    {
        return prescriptionId;
    }

    public List<PrescribedMedicine> getMedicineList() {
        return medicineList;
    }

    public boolean addMedicine(Medicine inputMedicine, int inputQuantity, String inputDosage)
    {
        return medicineList.add(new PrescribedMedicine(inputMedicine, inputQuantity, inputDosage));
    }

    public boolean removeMedicine(int inputMedicineId)
    {
        return medicineList.removeIf(prescribedMedicine -> prescribedMedicine.getMedicine().getMedicineId() == inputMedicineId);
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
