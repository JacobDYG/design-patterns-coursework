package controller.command;

import model.instance.CurrentData;
import model.stored.Medicine;

public class CreateNewMedicine implements ICommand {
    String name = "CreateNewMedicine";
    Medicine newMedicine;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        CurrentData.getAllMedicines().add(newMedicine);
    }

    public void prepare(String medicineName)
    {
        //find a free medicine id
        int medicineId = -1;
        for (Medicine medicine : CurrentData.getAllMedicines())
        {
            if (medicine.getMedicineId() > medicineId)
                medicineId = medicine.getMedicineId();
        }
        //create the medicine
        newMedicine = new Medicine(medicineId + 1, medicineName, 0);
    }

}
