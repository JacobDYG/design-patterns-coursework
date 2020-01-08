package model.stored;

public class Medicine {
    private int medicineId;
    private String medicineName;
    private int quantityInStock;

    public Medicine(int medicineId, String medicineName, int quantityInStock) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.quantityInStock = quantityInStock;
    }

    public int getMedicineId()
    {
        return medicineId;
    }
    public String getMedicineName()
    {
        return medicineName;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
