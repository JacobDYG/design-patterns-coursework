package controller.command;

import model.stored.AccountRequest;
import model.stored.AppointmentRequest;
import model.stored.Request;
import model.stored.role.SecretaryRoleData;
import view.instance.CurrentUser;

import javax.swing.*;

//updates a list of appointment requests
public class UpdateAppointmentRequestsList implements ICommand {
    String name = "UpdateAppointmentRequestsList";
    JList listToUpdate;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void perform()
    {
        DefaultListModel listModel = new DefaultListModel();
        //cycle through all requests to find appointment requests, and add these to the list
        for (Request request : ((SecretaryRoleData)CurrentUser.getCurrentUser().getRoleData()).getRequestList())
        {
            if (request instanceof AppointmentRequest)
            {
                listModel.addElement("Request ID: " + request.getRequestId() + ", Patient ID: " + ((AppointmentRequest) request).getPatientId() + ", Doctor ID: " + ((AppointmentRequest) request).getDoctorId()
                + ", Possible start date: " + ((AppointmentRequest) request).getPossibleDatesStart() + ", Possible end date: " + ((AppointmentRequest) request).getPossibleDatesEnd());
            }
        }
        listToUpdate.setModel(listModel);
    }

    public void prepare(JList listToUpdate)
    {
        this.listToUpdate = listToUpdate;
    }
}
