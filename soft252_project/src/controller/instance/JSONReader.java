package controller.instance;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.instance.CurrentData;
import model.stored.Medicine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {

    public static void readMedicines() {
        //Create a new JSON parser to read the object
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("medicines.json")) {
            //Read JSON file
            JSONArray readJson = (JSONArray) parser.parse(reader);

            System.out.println(readJson);

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

            System.out.println(readJson);

            for (Object user : readJson)
            {
                parseUserObject((JSONObject) user);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void parseUserObject(JSONObject user)
    {
        System.out.println("bruh");
    }
}
