package view.gui;

import controller.command.UpdateMedicinesCombo;
import controller.command.UpdatePrescriptionCreator;
import controller.command.UpdateUsersCombo;
import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePrescription {
    private JLabel lblCreatePrescription;
    private JLabel lblPatient;
    private JComboBox cmbPatient;
    private JLabel lblNotes;
    private JTextField txtNotes;
    private JLabel lblAddMedicine;
    private JComboBox cmbMedicines;
    private JLabel lblQuantity;
    private JTextField txtQuantity;
    private JLabel lblDosage;
    private JTextField txtDosage;
    private JButton lblAdd;
    private JButton lblConfirm;
    private JButton lblCancel;
    private JPanel pnlCreatePrescription;
    private JScrollPane scrMedicineList;
    private JList lstPrescribedMedicines;
    private JFrame frame;
    UpdatePrescriptionCreator updatePrescriptionCreator;

    public CreatePrescription(JFrame oldForm) {
        prepare(oldForm);

        lblCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                frame.dispose();
            }
        });

        lblAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //extract medicine id
                int medicineId;
                String selectedValue = (String) cmbMedicines.getSelectedItem();
                int endIndexMedicine = selectedValue.indexOf(",");
                medicineId = Integer.parseInt(selectedValue.substring(13, endIndexMedicine));
                //add to list
                try
                {
                    updatePrescriptionCreator.addPrescribedMedicine(medicineId, Integer.parseInt(txtQuantity.getText()), txtDosage.getText());
                    updatePrescriptionCreator.perform();
                }
                catch (Exception noParse)
                {
                    noParse.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Quantity can contain integers only!");
                }

            }
        });
        lblConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //extract patient id
                int patientId;
                String selectedValue = (String) cmbPatient.getSelectedItem();
                int endIndexPatient = selectedValue.indexOf(",");
                patientId = Integer.parseInt(selectedValue.substring(9, endIndexPatient));
                //confirm prescription
                updatePrescriptionCreator.confirmPrescription(patientId, CurrentUser.getCurrentUser().getUserId(), txtNotes.getText());
                //write out and return to main form
                JSONWriter.writeUsers();
                oldForm.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void prepare(JFrame oldForm)
    {
        //Initialize  this form
        frame = new JFrame("Create prescription");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 900, 600);
        frame.setContentPane(pnlCreatePrescription);
        frame.setVisible(true);
        oldForm.setVisible(false);
        updatePrescriptionCreator  = (UpdatePrescriptionCreator)CurrentUser.getCommand("UpdatePrescriptionCreator");
        updatePrescriptionCreator.prepare(lstPrescribedMedicines);

        //populate patient list
        UpdateUsersCombo updateUsersCombo = (UpdateUsersCombo) CurrentUser.getCommand("UpdateUsersCombo");
        updateUsersCombo.prepare(cmbPatient, false, false, false, true);
        updateUsersCombo.perform();
        //populate medicine list
        UpdateMedicinesCombo updateMedicinesCombo = (UpdateMedicinesCombo) CurrentUser.getCommand("UpdateMedicinesCombo");
        updateMedicinesCombo.prepare(cmbMedicines);
        updateMedicinesCombo.perform();
    }
}
