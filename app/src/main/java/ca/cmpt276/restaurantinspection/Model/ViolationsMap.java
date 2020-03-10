package ca.cmpt276.restaurantinspection.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ViolationsMap {
    private static ViolationsMap INSTANCE = null;
    private static Map<String, String[]> violationsLookup;

    private ViolationsMap(InputStream file) {
        violationsLookup = this.readViolationsData(file);
    }

    public static ViolationsMap getInstance(InputStream file) {
        if (INSTANCE == null) {
            INSTANCE = new ViolationsMap(file);
        }

        return INSTANCE;
    }

    /** Returning Violation Details from MAP **/
    public String[] getViolationFromMap(String key) {
        return violationsLookup.get(key);
    }

    /** Returning ENTIRE MAP **/
    public Map<String, String[]> getMap() {
        return violationsLookup;
    }

    /** REFERENCE: https://stackoverflow.com/questions/38415680/how-to-parse-csv-file-into-an-array-in-android-studio **/
    private Map<String, String[]> readViolationsData(InputStream file) {
        Map<String, String[]> violationsLookup = new HashMap<>();

        BufferedReader input = new BufferedReader(new InputStreamReader(file));

        try {
            String violation;
//            final String TAG = "MyActivity";
            
            while ((violation = input.readLine()) != null) {
                String[] line = violation.split(",");
                /** For some reason there's a pre-trailing " in front of key so I had to trim it below **/
                line[0] = line[0].substring(1);
//                Log.v(TAG, line[0]);
//                Log.v(TAG, line[1]);
//                Log.v(TAG, line[2]);
//                Log.v(TAG, line[3]);
//                Log.v(TAG, line[4]);
                violationsLookup.put(line[0], line);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("ERROR: Failed to read in " + file);
        }
        finally {
            try {
                file.close();
            }
            catch (IOException ex) {
                throw new RuntimeException("ERROR: Failed to close " + file);
            }
        }
        return violationsLookup;
    }
}
