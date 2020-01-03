package model;

public class PrescribedMedicine {
    private Medicine medicine;
    private int quantity;
    private String dosage;

    public PrescribedMedicine(Medicine medicine, int quantity, String dosage) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.dosage = dosage;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDosage() {
        return dosage;
    }
}
