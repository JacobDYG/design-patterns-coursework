package controller.role;

import controller.command.ICommand;

// A role determines what access a user has to the system.
// A class implementing this interface must return:
// 1) It's friendly name
// 2) It's instance of a requested action, if there is one.
//    Where no action is returned, the user does not have this permission.
public interface IRole {
    public String getName();
    public ICommand getCommand(String request);
}
