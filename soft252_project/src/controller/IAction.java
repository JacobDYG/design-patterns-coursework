package controller;

//Interface for actions. Every action has a name, and a function for performing the action
//Actions are independent from any particular role, as they may be used by multiple roles.
public interface IAction {
    String getName();
    void perform();
}
