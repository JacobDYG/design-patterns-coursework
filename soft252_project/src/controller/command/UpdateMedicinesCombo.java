package controller.command;

import model.instance.CurrentData;
import model.stored.Medicine;

import javax.swing.*;

//updates a combo box with current medicines for creating prescriptions
public class UpdateMedicinesCombo implements ICommand{
    String name = "UpdateMedicinesCombo";
    DefaultComboBoxModel medicinesCombo;
    JComboBox comboToUpdate;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        comboToUpdate.setModel(medicinesCombo);
    }

    public void prepare(JComboBox comboToUpdate)
    {
        medicinesCombo = new DefaultComboBoxModel();
        this.comboToUpdate = comboToUpdate;
        //add all medicines to the list
        for (Medicine medicine : CurrentData.getAllMedicines())
        {
            medicinesCombo.addElement("Medicine ID: " + medicine.getMedicineId() + ", Medicine Name: " + medicine.getMedicineName());
        }
    }
}
