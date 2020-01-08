package controller.command;

import model.instance.CurrentData;
import model.stored.Feedback;
import model.stored.User;
import model.stored.role.DoctorRoleData;

import java.util.List;

public class CreateFeedback implements ICommand {
    String name = "CreateFeedback";
    int doctorId;
    int rating;
    String feedbackNotes;
    DoctorRoleData doctorRoleData;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform() {
        //Find the doctor
        List<User> userList = CurrentData.getAllUsers();
        for (User user : userList)
        {
            if (user.getUserId() == doctorId)
                doctorRoleData = (DoctorRoleData)user.getRoleData();
        }
        //Find a free feedback id
        int feedbackId = -1;
        for (User user : userList)
        {
            if (user.getRole().equals("Doctor"))
            {
                DoctorRoleData thisDoctorRoleData = (DoctorRoleData)user.getRoleData();
                List<Feedback> feedbackList = thisDoctorRoleData.getFeedbackList();
                for (Feedback feedback : feedbackList)
                {
                    if (feedback.getFeedbackId() > feedbackId)
                        feedbackId = feedback.getFeedbackId() + 1;
                }
            }
        }
        doctorRoleData.addFeedback(new Feedback(feedbackId, doctorId, rating, feedbackNotes));
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setFeedbackNotes(String feedbackNotes) {
        this.feedbackNotes = feedbackNotes;
    }
}
