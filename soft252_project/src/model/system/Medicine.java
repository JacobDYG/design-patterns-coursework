package model.system;

public class Medicine {
    private int medicineId;
    private String medicineName;

    public Medicine(int medicineId, String medicineName) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
    }

    public int getMedicineId()
    {
        return medicineId;
    }
    public String getMedicineName()
    {
        return medicineName;
    }
}
