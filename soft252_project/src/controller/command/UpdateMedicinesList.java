package controller.command;

import model.instance.CurrentData;
import model.stored.Medicine;

import javax.swing.*;

//updates a list with current medicines
public class UpdateMedicinesList implements ICommand {
    String name = "UpdateMedicinesList";
    DefaultListModel medicinesList;
    JList listToUpdate;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        listToUpdate.setModel(medicinesList);
    }

    public void prepare(JList listToUpdate) {
        medicinesList = new DefaultListModel();
        this.listToUpdate = listToUpdate;
        //add all medicines to the list
        for (Medicine medicine : CurrentData.getAllMedicines()) {
            medicinesList.addElement("Medicine ID: " + medicine.getMedicineId() + ", Medicine Name: " + medicine.getMedicineName() + ", Quantity in stock: " + medicine.getQuantityInStock());
        }
    }
}

