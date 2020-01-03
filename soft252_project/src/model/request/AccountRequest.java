package model.request;

import model.User;

public class AccountRequest extends Request {

    private User requestedUser;

    public AccountRequest(int requestId, User requestedUser) {
        super(requestId);
        this.requestedUser = requestedUser;
    }
}
