package controller.command;

import model.stored.role.AdminRoleData;
import model.stored.role.DoctorRoleData;
import model.stored.role.PatientRoleData;
import view.instance.CurrentUser;

import javax.swing.*;

//Updates a provided JList element with a users appointments
//logic is contained within the roles as it the process is role specific
public class UpdateAppointmentList implements ICommand {
    String name = "UpdateAppointmentList";
    JList listToUpdate;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform() {
        if (CurrentUser.getCurrentUser().getRole().equals("Doctor"))
        {
            ((DoctorRoleData) CurrentUser.getCurrentUser().getRoleData()).updateAppointmentList(listToUpdate);
        }
        else if (CurrentUser.getCurrentUser().getRole().equals("Patient"))
        {
            ((PatientRoleData) CurrentUser.getCurrentUser().getRoleData()).updateAppointmentList(listToUpdate);
        }

    }

    public void setListToUpdate(JList listToUpdate) {
        this.listToUpdate = listToUpdate;
    }
}
