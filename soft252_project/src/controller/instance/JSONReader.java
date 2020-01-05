package controller.instance;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.instance.CurrentData;
import model.stored.*;
import model.stored.role.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {

    private static List<User> allUsers = new ArrayList<>();

    public static void readMedicines() {
        //Create a new JSON parser to read the object
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("medicines.json")) {
            //Read JSON file
            JSONArray readJson = (JSONArray) parser.parse(reader);

            for (Object medicine : readJson)
            {
                parseMedicineObject((JSONObject) medicine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseMedicineObject(JSONObject medicine)
    {
        JSONObject medicineObject = (JSONObject) medicine.get("medicine");

        int medicineId = (int)(long) medicineObject.get("medicineId");
        String medicineName = (String) medicineObject.get("medicineName");

        CurrentData.addMedicine(new Medicine(medicineId, medicineName));
    }

    public static void readUsers()
    {
        //Create a new JSON parser to read the object
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("users.json")) {
            //Read JSON file
            JSONArray readJson = (JSONArray) parser.parse(reader);

            for (Object user : readJson)
            {
                allUsers.add(parseUserObject((JSONObject) user));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CurrentData.setAllUsers(allUsers);
    }

    public static User parseUserObject(JSONObject user)
    {
        JSONObject userObject = (JSONObject) user.get("user");

        User thisUser;

        String role = (String) userObject.get("role");
        String username = (String) userObject.get("username");
        String password = (String) userObject.get("password");
        int userId = (int)(long) userObject.get("userId");
        String name = (String) userObject.get("name");
        String address = (String) userObject.get("address");
        String gender;
        int age;

        IRoleData roleData;

        //Role specific - gets the role specific data for each type, and
        //adds gender and age if the user is a patient
        if (role.equals("Admin"))
        {
            roleData = parseAdminLists(userObject);
            thisUser = new User(role, username, password, roleData, userId, name, address);
        }
        else if (role.equals("Secretary"))
        {
            roleData = parseSecretaryLists(userObject);
            thisUser = new User(role, username, password, roleData, userId, name, address);
        }
        else if (role.equals("Doctor"))
        {
            roleData = parseDoctorLists(userObject);
            thisUser = new User(role, username, password, roleData, userId, name, address);
        }
        else //patient
        {
            gender = (String) userObject.get("gender");
            age = (int)(long) userObject.get("age");
            roleData = parsePatientLists(userObject);
            thisUser = new User(role, username, password, roleData, userId, name, address, gender, age);
        }

        return thisUser;

    }

    private static AdminRoleData parseAdminLists(JSONObject userObject)
    {
        AdminRoleData roleData = new AdminRoleData();

        //The user may not have any specific data - return null if it does not
        if (!userObject.containsKey("pendingFeedbackList"))
        {
            return roleData;
        }
        JSONArray readPendingFeedbackList = (JSONArray) userObject.get("pendingFeedbackList");

        for (Object feedback : readPendingFeedbackList)
        {
            JSONObject feedbackObject = (JSONObject)((JSONObject) feedback).get("feedback");
            roleData.addFeedback(parseFeedbackObject(feedbackObject));
        }

        return roleData;
    }

    private static SecretaryRoleData parseSecretaryLists(JSONObject userObject)
    {
        //The user may not have any specific data - return null if it does not
        if (!userObject.containsKey("requestList"))
        {
            return null;
        }
        JSONArray readRequestList = (JSONArray) userObject.get("requestList");

        SecretaryRoleData roleData = new SecretaryRoleData();

        for (Object request : readRequestList)
        {
            JSONObject requestObject = (JSONObject) ((JSONObject) request).get("request");
            int requestId = (int)(long) requestObject.get("requestId");
            Request thisRequest = null;

            //Checks the type of request, different process required for accounts and appointments
            if(requestObject.containsKey("user"))
            {
                User thisUser = parseUserObject(requestObject);
                thisRequest = new AccountRequest(requestId, thisUser);
            }
            else
            {
                thisRequest = parseAppointmentRequestObject(requestId, requestObject);
            }
            roleData.addRequest(thisRequest);
        }

        return roleData;
    }

    private static DoctorRoleData parseDoctorLists(JSONObject userObject)
    {
        DoctorRoleData roleData = new DoctorRoleData();

        //The user may not have any specific data - return null if it does not
        if (!(userObject.containsKey("appointmentList") && userObject.containsKey("feedbackList")))
        {
            return roleData;
        }
        JSONArray readAppointmentList = (JSONArray) userObject.get("appointmentList");
        JSONArray readFeedbackList = (JSONArray) userObject.get("feedbackList");

        for (Object feedback : readFeedbackList)
        {
            JSONObject feedbackObject = (JSONObject) ((JSONObject) feedback).get("feedback");
            roleData.addFeedback(parseFeedbackObject(feedbackObject));
        }
        for (Object appointment : readAppointmentList)
        {
            JSONObject appointmentObject = (JSONObject) ((JSONObject) appointment).get("appointment");
            roleData.addAppointment(parseAppointmentObject(appointmentObject));
        }

        return roleData;
    }

    private static PatientRoleData parsePatientLists(JSONObject userObject)
    {
        //The user may not have any specific data - return null if it does not
        if (!(userObject.containsKey("appointmentList") && userObject.containsKey("prescriptionList")))
        {
            return null;
        }
        JSONArray readAppointmentList = (JSONArray) userObject.get("appointmentList");
        JSONArray readPrescriptionList = (JSONArray) userObject.get("prescriptionList");

        PatientRoleData roleData = new PatientRoleData();

        for (Object appointment : readAppointmentList)
        {
            JSONObject appointmentObject = (JSONObject) ((JSONObject) appointment).get("appointment");
            roleData.addAppointment(parseAppointmentObject(appointmentObject));
        }
        for (Object prescription : readPrescriptionList)
        {
            JSONObject prescriptionObject = (JSONObject) ((JSONObject) prescription).get("prescription");
            roleData.addPrescription(parsePrescriptionObject(prescriptionObject));
        }

        return roleData;
    }

    private static Feedback parseFeedbackObject(JSONObject feedbackObject)
    {
        int feedbackId = (int)(long) feedbackObject.get("feedbackId");
        int doctorUserId = (int)(long) feedbackObject.get("doctorUserId");
        int rating = (int)(long) feedbackObject.get("rating");
        String feedbackNotes = (String) feedbackObject.get("feedbackNotes");

        Feedback thisFeedback = new Feedback(feedbackId, doctorUserId, rating, feedbackNotes);
        return thisFeedback;
    }

    private static AppointmentRequest parseAppointmentRequestObject(int requestId, JSONObject requestObject)
    {
        int patientId = (int)(long) requestObject.get("patientId");
        int doctorId = (int)(long) requestObject.get("doctorId");
        Date possibleDatesStart;
        Date possibleDatesEnd;
        AppointmentRequest thisRequest = null;

        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        try {
            possibleDatesStart = format.parse((String)requestObject.get("possibleDatesStart"));
            possibleDatesEnd = format.parse((String)requestObject.get("possibleDatesEnd"));

            thisRequest = new AppointmentRequest(requestId, patientId, doctorId, possibleDatesStart, possibleDatesEnd);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return thisRequest;
    }

    private static Appointment parseAppointmentObject(JSONObject appointmentObject)
    {
        int appointmentId = (int)(long) appointmentObject.get("appointmentId");
        //Check if this appointment already exists, appointments are duplicated in doctors and
        //patients, so it may have already been created if the other user was made first
        for (User thisUser : allUsers)
        {
            if (thisUser.getRoleData() instanceof DoctorRoleData
                || thisUser.getRoleData() instanceof PatientRoleData)
            {
                List<Appointment> usersAppointments = null;
                if (thisUser.getRoleData() instanceof DoctorRoleData) {
                    usersAppointments = ((DoctorRoleData) thisUser.getRoleData()).getAppointmentList();
                }
                if (thisUser.getRoleData() instanceof PatientRoleData) {
                    usersAppointments = ((PatientRoleData) thisUser.getRoleData()).getAppointmentList();
                }
                assert usersAppointments != null;
                for (Appointment thisAppointment : usersAppointments)
                {
                    if (thisAppointment.getAppointmentId() == appointmentId)
                        return thisAppointment;
                }
            }
        }
        int doctorId = (int)(long) appointmentObject.get("doctorId");
        int patientId = (int)(long) appointmentObject.get("patientId");
        Date appointmentDate;

        Appointment thisAppointment = null;

        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        try {
            appointmentDate = format.parse((String)appointmentObject.get("appointmentDate"));
            thisAppointment = new Appointment( appointmentId, doctorId, patientId, appointmentDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return thisAppointment;
    }

    private static Prescription parsePrescriptionObject(JSONObject prescriptionObject)
    {
        int prescriptionId = (int)(long) prescriptionObject.get("prescriptionId");
        int doctorId = (int)(long) prescriptionObject.get("doctorId");
        int patientId = (int)(long) prescriptionObject.get("patientId");
        List<PrescribedMedicine> prescribedMedicineList = new ArrayList<>();

        Prescription thisPrescription = null;

        JSONArray readPrescribedMedicineList = (JSONArray) prescriptionObject.get("prescribedMedicineList");

        for (Object prescribedMedicine : readPrescribedMedicineList)
        {
            JSONObject prescribedMedicineObject = (JSONObject) ((JSONObject) prescribedMedicine).get("prescribedMedicine");
            prescribedMedicineList.add(parsePrescribedMedicineObject(prescribedMedicineObject));
        }

        thisPrescription = new Prescription(prescriptionId, doctorId, patientId, prescribedMedicineList);

        //Set doctor notes if there aren't any - doctor may leave this empty
        if (prescriptionObject.containsKey("doctorNotes"))
        {
            String doctorNotes = (String) prescriptionObject.get("doctorNotes");
            thisPrescription.setDoctorNotes(doctorNotes);
        }

        return thisPrescription;
    }

    private static PrescribedMedicine parsePrescribedMedicineObject(JSONObject prescribedMedicineObject)
    {
        int medicineId = (int)(long) prescribedMedicineObject.get("medicineId");
        int quantity = (int)(long) prescribedMedicineObject.get("quantity");
        String dosage = (String) prescribedMedicineObject.get("dosage");

        //Find the medicine in the current medicine list. Existing medicines should be
        //in this list, if they are not no prescribedMedicine object will be returned
        Medicine medicine = null;
        for (Medicine currentMedicine : CurrentData.getAllMedicines())
        {
            if (currentMedicine.getMedicineId() == medicineId)
            {
                medicine = currentMedicine;
                break;
            }
        }
        if (medicine == null)
        {
            return null;
        }
        else
        {
            return new PrescribedMedicine(medicine, quantity, dosage);
        }
    }
}
