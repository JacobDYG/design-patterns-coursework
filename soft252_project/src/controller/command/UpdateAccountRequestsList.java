package controller.command;

import model.stored.AccountRequest;
import model.stored.Request;
import model.stored.role.SecretaryRoleData;
import view.instance.CurrentUser;

import javax.swing.*;

//Updates a JList ui element with the current users account requests
public class UpdateAccountRequestsList implements ICommand {
    String name = "UpdateAccountRequestsList";
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
        SecretaryRoleData secretaryRoleData = (SecretaryRoleData)CurrentUser.getCurrentUser().getRoleData();
        //iterate through all requests to get account requests only
        for (Request request : secretaryRoleData.getRequestList())
        {
            if (request instanceof AccountRequest)
            {
                //add them to list model in friendly way
                listModel.addElement("Request ID: " + request.getRequestId() + ", Is deletion request? " + ((AccountRequest) request).isDeletionRequest() + ", User ID: " + ((AccountRequest) request).getRequestedUser().getUserId());
            }
        }
        listToUpdate.setModel(listModel);
    }

    public void prepare(JList listToUpdate)
    {
        this.listToUpdate = listToUpdate;
    }
}
