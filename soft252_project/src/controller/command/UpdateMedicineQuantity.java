package controller.command;

import model.instance.CurrentData;
import model.stored.Medicine;

public class UpdateMedicineQuantity implements ICommand {
    String name = "UpdateMedicineQuantity";
    int medicineId;
    int quantity;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        //find the medicine
        for (Medicine medicine : CurrentData.getAllMedicines())
        {
            if (medicine.getMedicineId() == medicineId)
            {
                //change the quantity
                medicine.setQuantityInStock(medicine.getQuantityInStock() - quantity);
            }
        }
    }

    public void prepare(int medicineId, int quantity)
    {
        this.medicineId = medicineId;
        this.quantity = quantity;
    }
}
