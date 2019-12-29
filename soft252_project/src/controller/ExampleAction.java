package controller;

//An example action, will be removed in final project.
public class ExampleAction implements IAction {
    public String getName(){
        return "Example Action";
    }
    public void perform()
    {
        System.out.println("Performed the Example Action");
    }
}
