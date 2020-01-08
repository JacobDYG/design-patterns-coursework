package controller.command;

import model.stored.role.AdminRoleData;
import model.stored.role.DoctorRoleData;
import view.instance.CurrentUser;

import javax.swing.*;

public class UpdateAppointmentList implements ICommand {
    String name = "UpdateAppointmentList";
    JList listToUpdate;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform() {
        ((DoctorRoleData) CurrentUser.getCurrentUser().getRoleData()).updateAppointmentList(listToUpdate);
    }

    public void setListToUpdate(JList listToUpdate) {
        this.listToUpdate = listToUpdate;
    }
}
