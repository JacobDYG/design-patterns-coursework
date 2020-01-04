package model.stored.role;

import model.stored.Request;

import java.util.ArrayList;
import java.util.List;

public class SecretaryRoleData {
    //Secretary specific data
    private List<Request> requestList = new ArrayList<>();

    //Add a request
    public void addRequest(Request inputRequest)
    {
        requestList.add(inputRequest);
    }

    //Remove a request
    public boolean removeRequest(int requestId)
    {
        return requestList.removeIf(request -> request.getRequestId() == requestId);
    }

    //Return the request list for inspection
    public List<Request> getRequestList()
    {
        return requestList;
    }
}
