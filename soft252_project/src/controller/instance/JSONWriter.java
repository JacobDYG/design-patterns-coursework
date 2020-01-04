package controller.instance;

import model.instance.CurrentData;
import model.stored.Medicine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONWriter {
    public static void WriteMedicines()
    {
        JSONArray medicineArray = new JSONArray();

        for (Medicine medicine : CurrentData.getAllMedicines())
        {
            JSONObject thisMedicine = new JSONObject();

            thisMedicine.put("medicineId", medicine.getMedicineId());
            thisMedicine.put("medicineName", medicine.getMedicineName());

            System.out.println(thisMedicine);

            medicineArray.add(thisMedicine);
        }

        try (PrintWriter writer = new PrintWriter("medicines2.json")) {
            writer.write(medicineArray.toJSONString());

            writer.flush();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
