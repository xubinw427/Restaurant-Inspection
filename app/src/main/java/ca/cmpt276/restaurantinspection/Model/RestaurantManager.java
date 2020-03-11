package ca.cmpt276.restaurantinspection.Model;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class RestaurantManager implements Iterable<Restaurant> {
    private ArrayList<Restaurant> restaurantsList = new ArrayList<>();
    private static RestaurantManager INSTANCE;
    private ViolationsMap violationsMap;

    //Private to prevent anyone else from instantiating.
    private RestaurantManager(InputStream restaurantFile,
                              InputStream inspectionsFile) {

        readRestaurantData(restaurantFile);

        violationsMap = ViolationsMap.getInstance();

        populateInspections(inspectionsFile);
    }

    public static RestaurantManager getInstance() {
        if(INSTANCE == null) {
            throw new AssertionError(
                    "RestaurantManager.init(InputStream file) must be called first.");
        }

        return INSTANCE;
    }

    public static RestaurantManager init(InputStream restaurantFile,
                                         InputStream inspectionsFile) {
        if (INSTANCE != null) {
            return null;
        }

        INSTANCE = new RestaurantManager(restaurantFile, inspectionsFile);
        return INSTANCE;
    }

    public ArrayList<Restaurant> getList() {
        return restaurantsList;
    }

    private void addNew(Restaurant restaurant) {
        restaurantsList.add(restaurant);
    }

    public Restaurant getTheOneAt(int index) {
        return restaurantsList.get(index);
    }

    public int getsize() {
        return restaurantsList.size();
    }

//    private void sortByAlphabet() {
//        Arrays.sort(new ArrayList[]{restaurantsList});
//    }

    private void readRestaurantData(InputStream file) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file, Charset.forName("UTF-8"))
        );

        String line;
        try {
            // Step over header
            reader.readLine();

            while (((line = reader.readLine()) != null)) {
                // Read the data
                Restaurant newRestaurant = new Restaurant(line);
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

        //this.sortByAlphabet();
    }

    private void populateInspections(InputStream file) {
        BufferedReader input = new BufferedReader(new InputStreamReader(file));

        try {
            String line;

            // Step over header
            input.readLine();

            while ((line = input.readLine()) != null) {
                String[] inspectionData = line.split("\\*");

                Inspection currInspection = new Inspection(inspectionData, violationsMap);

                String currRestaurantID = inspectionData[0];
                /** PRINT OUT THE ID AND MAKE SURE ITS RIGHT!!!!!!!!! **/

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
