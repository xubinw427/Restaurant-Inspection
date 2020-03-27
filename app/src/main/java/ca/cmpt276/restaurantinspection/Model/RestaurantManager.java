package ca.cmpt276.restaurantinspection.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class RestaurantManager implements Iterable<Restaurant> {
    private ArrayList<Restaurant> restaurantsList = new ArrayList<>();
    private static RestaurantManager instance;
    private ViolationsMap violationsMap;
    private int currRestaurantPosition;
    private int currInspectionPosition;
    private int fromMap = 0;
    private int fromList = 0;
    private String TAG = "Degug";
    /** Private to prevent anyone else from instantiating. **/
    private RestaurantManager(InputStream restaurantFile,
                              InputStream inspectionsFile) {

        readRestaurantData(restaurantFile);
        violationsMap = ViolationsMap.getInstance();
        populateInspections(inspectionsFile);
    }

    public static RestaurantManager getInstance() {
        if(instance == null) {
            throw new AssertionError(
                    "RestaurantManager.init(InputStream file) must be called first.");
        }

        return instance;
    }

    public static void init(InputStream restaurantFile,
                            InputStream inspectionsFile) {
        if (instance != null) {
            return;
        }

        instance = new RestaurantManager(restaurantFile, inspectionsFile);
    }

    public void reset()
    {
        instance = null;
    }

    public ArrayList<Restaurant> getList() {
        return restaurantsList;
    }

    public Restaurant getRestaurantAt(int index) {
        return restaurantsList.get(index);
    }

    public int getSize() {
        return restaurantsList.size();
    }

    public int getCurrRestaurantPosition() {
        return currRestaurantPosition;
    }

    public int getCurrInspectionPosition() {
        return currInspectionPosition;
    }

    public void setCurrRestaurantPosition(int position) {
        currRestaurantPosition = position;
    }

    public void setCurrInspectionPosition(int position) {
        currInspectionPosition = position;
    }

    public int getFromList() {
        return this.fromList;
    }

    public int getFromMap() {
        return this.fromMap;
    }

    public void setFromList(int i) {
        this.fromList = i;
    }

    public void setFromMap(int i) {
        this.fromMap = i;
    }

    private void addNew(Restaurant restaurant) {
        restaurantsList.add(restaurant);
    }

    private void readRestaurantData(InputStream file) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file, Charset.forName("UTF-8"))
        );

        String line;

        try {
            /** Step over header **/
            reader.readLine();
            int count = 1;
            while (((line = reader.readLine()) != null)) {
//                Log.d(TAG, "The " + count + " Restaurant is: " + line);
                count++;
                Restaurant newRestaurant = new Restaurant(line);
//                System.out.println(newRestaurant.getName());
                this.addNew(newRestaurant);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            file.close();
        }
        catch (IOException ex) {
            throw new RuntimeException("ERROR: Failed to close " + file);
        }
    }

    private void populateInspections(InputStream file) {
        BufferedReader input = new BufferedReader(new InputStreamReader(file));

        String line;

        try {
            /** Step over header **/
            input.readLine();

            while ((line = input.readLine()) != null) {
                String[] inspectionLump = line.split(",\"");

                if (inspectionLump[0].contains(",,,")) {
                    continue;
                }

                String[] firstHalf = inspectionLump[0].split(",");
                String currRestaurantID = firstHalf[0].trim();

                Inspection currInspection = new Inspection(inspectionLump, violationsMap);

                for (Restaurant restaurant : restaurantsList) {
                    if (restaurant.getId().equals(currRestaurantID)) {
                        restaurant.addInspection(currInspection);
                        break;
                    }
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("ERROR: Failed to read in " + file);
        }

        try {
            file.close();
        }
        catch (IOException ex) {
            throw new RuntimeException("ERROR: Failed to close " + file);
        }
    }

    @Override
    @NonNull
    public Iterator<Restaurant> iterator() {
        return restaurantsList.iterator();
    }
}
