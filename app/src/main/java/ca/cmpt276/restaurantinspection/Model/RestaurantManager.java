package ca.cmpt276.restaurantinspection.Model;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private RestaurantManager(InputStream restaurantFile, FileInputStream serverRestaurantFile,
                              InputStream inspectionsFile, FileInputStream serverInspectionFile) {

        readRestaurantData(restaurantFile);
        readRestaurantData(serverRestaurantFile);

        violationsMap = ViolationsMap.getInstance();

        populateInspections(inspectionsFile);
        populateInspections(serverInspectionFile);
    }

    public static RestaurantManager getInstance() {
        if(instance == null) {
            throw new AssertionError(
                    "RestaurantManager.init(InputStream file) must be called first.");
        }

        return instance;
    }

    public static void init(InputStream restaurantFile, FileInputStream serverRestaurantFile,
                            InputStream inspectionsFile, FileInputStream serverInspectionFile) {
        if (instance != null) {
            return;
        }

        instance = new RestaurantManager(restaurantFile, serverRestaurantFile,
                                            inspectionsFile, serverInspectionFile);
    }

    public void reset() {
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
        /** Don't add duplicate restaurants across the two data sources **/
        for (Restaurant restaurantInList : restaurantsList) {
            if (restaurant.getId().equals(restaurantInList.getId())) {
                return;
            }
        }

        restaurantsList.add(restaurant);
    }

    private void readRestaurantData(InputStream file) {
        if (file == null) { return; }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file, Charset.forName("UTF-8"))
        );

        String line;

        try {
            /** Step over header **/
            reader.readLine();

            while (((line = reader.readLine()) != null)) {
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

        this.sortRestaurants();
        this.sortInspectionsForEveryRestaurant();
    }


    private  void sortInspectionsForEveryRestaurant()
    {
        int size = this.restaurantsList.size();
        for(int i = 0; i < size; i++)
        {
            Collections.sort(restaurantsList.get(i).getInspectionsList(), new SortInspectionsByDate());
        }
    }

    private void populateInspections(InputStream file) {
        if (file == null) { return; }
        
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

    private void sortRestaurants() {
        Collections.sort(restaurantsList, new SortRestaurantsByNameAplhabet());
    }

    @Override
    @NonNull
    public Iterator<Restaurant> iterator() {
        return restaurantsList.iterator();
    }
}

class SortRestaurantsByNameAplhabet implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        return o1.compareTo(o2);
    }
}

class SortInspectionsByDate implements  Comparator<Inspection> {
    @Override
    public int compare(Inspection o1, Inspection o2) {
        return o1.compareTo(o2);
    }
}
