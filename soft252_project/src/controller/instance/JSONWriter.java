package controller.instance;

import model.instance.CurrentData;
import model.stored.*;
import model.stored.role.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

@SuppressWarnings("unchecked")
//The json.simple library does not use generics, so generates many warnings despite
//correct usage. This annotation hides these warnings, removing it has no effect on functionality
public class JSONWriter {
    public static void writeMedicines()
    {
        JSONArray medicineArray = new JSONArray();

        for (Medicine medicine : CurrentData.getAllMedicines())
        {
            JSONObject thisMedicine = new JSONObject();
            JSONObject thisMedicineDetails = new JSONObject();

            thisMedicineDetails.put("medicineId", medicine.getMedicineId());
            thisMedicineDetails.put("medicineName", medicine.getMedicineName());
            thisMedicineDetails.put("quantityInStock", medicine.getQuantityInStock());

            thisMedicine.put("medicine", thisMedicineDetails);
            medicineArray.add(thisMedicine);
        }

        try (PrintWriter writer = new PrintWriter("medicines.json")) {
            writer.write(medicineArray.toJSONString());

            writer.flush();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeUsers()
    {
        JSONArray userArray = new JSONArray();

        for (User user : CurrentData.getAllUsers())
        {
            JSONObject thisUser = new JSONObject();
            thisUser.put("user", putUserDetails(user));
            userArray.add(thisUser);
        }

        try (PrintWriter writer = new PrintWriter("users.json")) {
            writer.write(userArray.toJSONString());

            writer.flush();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject putUserDetails(User user)
    {
        JSONObject thisUserDetails = new JSONObject();

        thisUserDetails.put("role", user.getRole());
        thisUserDetails.put("userId", user.getUserId());
        thisUserDetails.put("name", user.getName());
        thisUserDetails.put("address", user.getAddress());
        thisUserDetails.put("username", user.getUsername());
        thisUserDetails.put("password", user.getPassword());

        //Role specific - puts the role specific data for each type, and
        //puts gender and age if the user is a patient
        if (user.getRole().equals("Admin"))
        {
            putAdminLists(thisUserDetails, user);
        }
        else if (user.getRole().equals("Secretary"))
        {
            putSecretaryLists(thisUserDetails, user);
        }
        else if (user.getRole().equals("Doctor"))
        {
            putDoctorLists(thisUserDetails, user);
        }
        else //patient
        {
            thisUserDetails.put("gender", user.getGender());
            thisUserDetails.put("age", user.getAge());
            putPatientLists(thisUserDetails, user);
        }

        return thisUserDetails;
    }

    private static void putAdminLists(JSONObject thisUser, User user)
    {
        JSONArray pendingFeedbackList = new JSONArray();

        for (Feedback feedback : ((AdminRoleData) user.getRoleData()).getFeedbackList())
        {
            pendingFeedbackList.add(putFeedback(feedback));
        }

        thisUser.put("pendingFeedbackList", pendingFeedbackList);
    }

    private static JSONObject putFeedback(Feedback feedback)
    {
        JSONObject thisFeedback = new JSONObject();
        JSONObject thisFeedbackDetails = new JSONObject();

        thisFeedbackDetails.put("feedbackId", feedback.getFeedbackId());
        thisFeedbackDetails.put("doctorUserId", feedback.getDoctorUserId());
        thisFeedbackDetails.put("rating", feedback.getRating());
        thisFeedbackDetails.put("feedbackNotes", feedback.getFeedbackNotes());

        thisFeedback.put("feedback", thisFeedbackDetails);
        return thisFeedback;
    }

    private static void putSecretaryLists(JSONObject thisUser, User user)
    {
        JSONArray requestList = new JSONArray();

        for (Request request : ((SecretaryRoleData) user.getRoleData()).getRequestList())
        {
            if (request instanceof AppointmentRequest)
            {
                requestList.add(putAppointmentRequest(request));
            }
            else
            {
                requestList.add(putAccountRequest(request));
            }
        }

        thisUser.put("requestList", requestList);
    }

    private static JSONObject putAppointmentRequest(Request request)
    {
        JSONObject thisRequest = new JSONObject();
        JSONObject thisRequestDetails = new JSONObject();

        thisRequestDetails.put("requestId", ((AppointmentRequest)request).getRequestId());
        thisRequestDetails.put("patientId", ((AppointmentRequest)request).getPatientId());
        thisRequestDetails.put("doctorId", ((AppointmentRequest)request).getDoctorId());

        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        String possibleDatesStart = format.format(((AppointmentRequest)request).getPossibleDatesStart());
        String possibleDatesEnd = format.format(((AppointmentRequest)request).getPossibleDatesEnd());

        thisRequestDetails.put("possibleDatesStart", possibleDatesStart);
        thisRequestDetails.put("possibleDatesEnd", possibleDatesEnd);

        thisRequest.put("request", thisRequestDetails);
        return thisRequest;
    }

    private static JSONObject putAccountRequest(Request request)
    {
        JSONObject thisRequest = new JSONObject();
        JSONObject thisRequestDetails = new JSONObject();

        thisRequestDetails.put("requestId", request.getRequestId());
        thisRequestDetails.put("user", putUserDetails(((AccountRequest)request).getRequestedUser()));
        thisRequestDetails.put("deletionRequest", ((AccountRequest) request).isDeletionRequest());

        thisRequest.put("request", thisRequestDetails);
        return thisRequest;
    }

    private static void putDoctorLists(JSONObject thisUser, User user)
    {
        JSONArray appointmentList = new JSONArray();
        JSONArray feedbackList = new JSONArray();

        for (Appointment appointment : ((DoctorRoleData) user.getRoleData()).getAppointmentList())
        {
            appointmentList.add(putAppointment(appointment));
        }
        for (Feedback feedback : ((DoctorRoleData) user.getRoleData()).getFeedbackList())
        {
            feedbackList.add(putFeedback(feedback));
        }

        thisUser.put("appointmentList", appointmentList);
        thisUser.put("feedbackList", feedbackList);
    }

    private static JSONObject putAppointment(Appointment appointment)
    {
        JSONObject thisAppointment = new JSONObject();
        JSONObject thisAppointmentDetails = new JSONObject();

        thisAppointmentDetails.put("appointmentId", appointment.getAppointmentId());
        thisAppointmentDetails.put("doctorId", appointment.getDoctorId());
        thisAppointmentDetails.put("patientId", appointment.getPatientId());

        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh:mm");
        String appointmentDate = format.format(appointment.getAppointmentDate());

        thisAppointmentDetails.put("appointmentDate", appointmentDate);

        if (!(appointment.getAppointmentNotes() == null)) {
            thisAppointmentDetails.put("appointmentNotes", appointment.getAppointmentNotes());
        }

        thisAppointment.put("appointment", thisAppointmentDetails);

        return thisAppointment;
    }

    private static void putPatientLists(JSONObject thisUser, User user)
    {
        JSONArray appointmentList = new JSONArray();
        JSONArray prescriptionList = new JSONArray();

        for (Appointment appointment : ((PatientRoleData) user.getRoleData()).getAppointmentList())
        {
            appointmentList.add(putAppointment(appointment));
        }
        for (Prescription prescription : ((PatientRoleData) user.getRoleData()).getPrescriptionList())
        {
            prescriptionList.add(putPrescription(prescription));
        }

        thisUser.put("appointmentList", appointmentList);
        thisUser.put("prescriptionList", prescriptionList);
    }

    private static JSONObject putPrescription(Prescription prescription)
    {
        JSONObject thisPrescription = new JSONObject();
        JSONObject thisPrescriptionDetails = new JSONObject();

        thisPrescriptionDetails.put("prescriptionId", prescription.getPrescriptionId());
        thisPrescriptionDetails.put("doctorId", prescription.getDoctorId());
        thisPrescriptionDetails.put("patientId", prescription.getPatientId());
        thisPrescriptionDetails.put("doctorNotes", prescription.getDoctorNotes());

        JSONArray prescribedMedicineList = new JSONArray();

        for (PrescribedMedicine prescribedMedicine : prescription.getPrescribedMedicineList())
        {
            prescribedMedicineList.add(putPrescribedMedicine(prescribedMedicine));
        }

        thisPrescriptionDetails.put("prescribedMedicineList", prescribedMedicineList);

        thisPrescription.put("prescription", thisPrescriptionDetails);
        return thisPrescription;
    }

    private static JSONObject putPrescribedMedicine(PrescribedMedicine prescribedMedicine)
    {
        JSONObject thisPrescribedMedicine = new JSONObject();
        JSONObject thisPrescribedMedicineDetails = new JSONObject();

        thisPrescribedMedicineDetails.put("medicineId", prescribedMedicine.getMedicine().getMedicineId());
        thisPrescribedMedicineDetails.put("quantity", prescribedMedicine.getQuantity());
        thisPrescribedMedicineDetails.put("dosage", prescribedMedicine.getDosage());

        thisPrescribedMedicine.put("prescribedMedicine", thisPrescribedMedicineDetails);
        return thisPrescribedMedicine;
    }
}
