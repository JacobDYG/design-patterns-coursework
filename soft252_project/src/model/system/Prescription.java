package model.system;

import java.util.ArrayList;
import java.util.List;
import model.system.Medicine;

public class Prescription {
    private int prescriptionId;
    private List<PrescribedMedicine> medicineList = new ArrayList<>();

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
}
