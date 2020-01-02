package model.system.request;

//The request system must be able to handle: Appointments, account creation/deletion
abstract public class Request {
    private int requestId;

    public int getRequestId()
    {
        return requestId;
    }

    public abstract void approve();
    public abstract void disapprove();
}
