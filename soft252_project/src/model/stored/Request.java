package model.stored;

//The request system must be able to handle: Appointments, account creation/deletion
abstract public class Request {
    private int requestId;

    public Request(int requestId) {
        this.requestId = requestId;
    }

    public int getRequestId()
    {
        return requestId;
    }
}
