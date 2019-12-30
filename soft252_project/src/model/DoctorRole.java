package model;

import controller.*;

import java.util.ArrayList;
import java.util.List;

public class DoctorRole implements IRole {
    //The friendly name of this Role and the list of its allowed actions
    String name = "Doctor Role";
    private List<IAction> allowedActions = new ArrayList<IAction>();

    //Constructor - adds default actions to the list
    DoctorRole(){
        addDefaultActions();
    }

    //Adds the default actions to the allowedActions list.
    void addDefaultActions(){
        allowedActions.add(new ExampleAction());
        allowedActions.add(new ExampleActionTwo());
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public IAction getAction(String request) {
        for (IAction action : allowedActions) {
            if (action.getName() == request){
                return action;
            }
        }
        return null;
    }
}
