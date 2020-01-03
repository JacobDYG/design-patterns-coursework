package model.stored;

public class Feedback {
    private int feedbackId;
    private int doctorUserId;
    private int rating;
    private String feedbackNotes;

    public Feedback(int feedbackId, int doctorUserId, int rating, String feedbackNotes) {
        this.feedbackId = feedbackId;
        this.doctorUserId = doctorUserId;
        this.rating = rating;
        this.feedbackNotes = feedbackNotes;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public int getDoctorUserId() {
        return doctorUserId;
    }

    public int getRating() {
        return rating;
    }

    public String getFeedbackNotes() {
        return feedbackNotes;
    }
}
