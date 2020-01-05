package model.stored;

public class AccountRequest extends Request {

    private User requestedUser;

    public AccountRequest(int requestId, User requestedUser) {
        super(requestId);
        this.requestedUser = requestedUser;
    }

    public User getRequestedUser() {
        return requestedUser;
    }
}
