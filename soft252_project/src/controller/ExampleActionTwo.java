package controller;

//An example action, will be removed in final project.
public class ExampleActionTwo implements IAction {
    public String getName(){
        return "Example Action Two";
    }
    public void perform()
    {
        System.out.println("Performed the Second Example Action");
    }
}
