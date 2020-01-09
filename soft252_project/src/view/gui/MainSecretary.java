package view.gui;

import controller.command.ApproveAccountRequest;
import controller.command.UpdateMedicineQuantity;
import controller.command.UpdateAccountRequestsList;
import controller.command.UpdateMedicinesList;
import controller.instance.JSONWriter;
import view.instance.CurrentUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainSecretary {
    private JPanel pnlMainSecretary;
    private JButton btnLogout;
    private JButton btnApprove;
    private JScrollPane scrRequests;
    private JScrollPane scrMedicines;
    private JButton btnDispense;
    private JButton btnStock;
    private JButton btnAppointment;
    private JButton btnRemovePatient;
    private JList lstAccountRequests;
    private JList lstMedicines;
    private JLabel lblMedicines;
    private JLabel lblAccountRequests;
    private JFrame frame;
    private JFrame oldForm;

    public MainSecretary(JFrame oldForm) {
        this.oldForm = oldForm;
        //Initialize  this form
        frame = new JFrame("Secretary Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1000, 600);
        frame.setContentPane(pnlMainSecretary);
        frame.setVisible(true);
        oldForm.setVisible(false);

        updateLists();

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldForm.setVisible(true);
                CurrentUser.clearCurrentUser();
                frame.dispose();
            }
        });
        btnApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApproveAccountRequest approveAccountRequest = (ApproveAccountRequest)CurrentUser.getCommand("ApproveAccountRequest");
                //get the request id
                if (!lstAccountRequests.isSelectionEmpty()) {
                    int accountRequestId;
                    String selectedValueAccountRequest = (String) lstAccountRequests.getSelectedValue();
                    int endIndex = selectedValueAccountRequest.indexOf(",");
                    accountRequestId = Integer.parseInt(selectedValueAccountRequest.substring(12, endIndex));

                    approveAccountRequest.prepare(accountRequestId);
                    approveAccountRequest.perform();

                    //Write out and refresh
                    JSONWriter.writeUsers();
                    updateLists();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Select an account related request and try again");
                }
            }
        });
        btnDispense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get the medicine id
                if (!lstMedicines.isSelectionEmpty()) {
                    int medicineId;
                    String selectedValueMedicine = (String) lstMedicines.getSelectedValue();
                    int endIndex = selectedValueMedicine.indexOf(",");
                    medicineId = Integer.parseInt(selectedValueMedicine.substring(13, endIndex));

                    int dispenseAmount;
                    try {
                        dispenseAmount = Integer.parseInt(JOptionPane.showInputDialog("How many to dispense?"));
                        //get the current quantity
                        int availableQuantity;
                        int quantityStartIndex = selectedValueMedicine.lastIndexOf(" ") + 1;
                        int quantityEndIndex = selectedValueMedicine.length();
                        availableQuantity = Integer.parseInt(selectedValueMedicine.substring(quantityStartIndex, quantityEndIndex));

                        if (dispenseAmount < availableQuantity)
                        {
                            if (dispenseAmount > 0) {
                                //dispense
                                UpdateMedicineQuantity updateMedicineQuantity = (UpdateMedicineQuantity) CurrentUser.getCommand("UpdateMedicineQuantity");
                                updateMedicineQuantity.prepare(medicineId, dispenseAmount);
                                updateMedicineQuantity.perform();
                                //write out and update lists
                                JSONWriter.writeMedicines();
                                updateLists();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "You must dispense more than 0");
                            }
                        }
                        else
                        {
                            //there is not enough
                            JOptionPane.showMessageDialog(null, "There is not enough of this medicine in stock, order more first");
                        }
                    }
                    catch (Exception parseEx)
                    {
                        JOptionPane.showMessageDialog(null, "Input integer numbers only");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Select a medicine and try again");
                }
            }
        });
        btnStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get the medicine id
                if (!lstMedicines.isSelectionEmpty()) {
                    int medicineId;
                    String selectedValueMedicine = (String) lstMedicines.getSelectedValue();
                    int endIndex = selectedValueMedicine.indexOf(",");
                    medicineId = Integer.parseInt(selectedValueMedicine.substring(13, endIndex));

                    int stockAmount;
                    try {
                        stockAmount = Integer.parseInt(JOptionPane.showInputDialog("How many to stock?"));
                        if (stockAmount > 0) {
                            //restock
                            UpdateMedicineQuantity updateMedicineQuantity = (UpdateMedicineQuantity) CurrentUser.getCommand("UpdateMedicineQuantity");
                            updateMedicineQuantity.prepare(medicineId, - stockAmount);
                            updateMedicineQuantity.perform();
                            //write out and update lists
                            JSONWriter.writeMedicines();
                            updateLists();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Value must be higher than 0");
                        }
                    }
                    catch (Exception parseEx)
                    {
                        JOptionPane.showMessageDialog(null, "Input integer numbers only");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Select a medicine and try again");
                }
            }
        });
        btnRemovePatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveUser removeUser = new RemoveUser(frame, false, false, false, true);
            }
        });
        btnAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageAppointmentRequests manageAppointmentRequests = new ManageAppointmentRequests(frame);
            }
        });
    }

    private void updateLists()
    {
        //populate account request list
        UpdateAccountRequestsList updateAccountRequestsList = (UpdateAccountRequestsList) CurrentUser.getCommand("UpdateAccountRequestsList");
        updateAccountRequestsList.prepare(lstAccountRequests);
        updateAccountRequestsList.perform();
        //populate medicines list
        UpdateMedicinesList updateMedicinesList = (UpdateMedicinesList) CurrentUser.getCommand("UpdateMedicinesList");
        updateMedicinesList.prepare(lstMedicines);
        updateMedicinesList.perform();
    }
}
