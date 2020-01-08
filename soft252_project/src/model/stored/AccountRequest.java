package model.stored;

public class AccountRequest extends Request {

    private User requestedUser;
    private boolean deletionRequest;

    public AccountRequest(int requestId, User requestedUser, boolean deletionRequest) {
        super(requestId);
        this.requestedUser = requestedUser;
        this.deletionRequest = deletionRequest;
    }

    public User getRequestedUser() {
        return requestedUser;
    }

    public boolean isDeletionRequest() {
        return deletionRequest;
    }
}
