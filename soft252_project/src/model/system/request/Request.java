package model.system.request;

//The request system must be able to handle: Appointments, account creation/deletion
abstract public class Request {
    abstract void approve();
    abstract void disapprove();
}
