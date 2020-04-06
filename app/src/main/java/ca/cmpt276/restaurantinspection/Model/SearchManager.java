package ca.cmpt276.restaurantinspection.Model;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/** Singleton to Keep Track of All Restaurants in Data **/
public class SearchManager implements Iterable<Restaurant> {
    private ArrayList<Restaurant> restaurantsList = new ArrayList<>();
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private static SearchManager instance;
    private ViolationsMap violationsMap;
    private int currRestaurantPosition;
    private int currInspectionPosition;
    private int filter = 0;
    private int fromMap = 0;
    private int fromList = 0;

    /** Private to prevent anyone else from instantiating. **/
    private SearchManager() {
        violationsMap = ViolationsMap.getInstance();
    }

    public static SearchManager getInstance() {
        if (instance == null) {
            return null;
        }

        return instance;
    }

    public static void init() {
        if (instance != null) {
            return;
        }

        instance = new SearchManager();
    }

    public void reset() {
        instance = null;
        filter = 0;
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

    public int getFilter() {
        return filter;
    }

    public void populateSearchManager(InputStream restaurantFile, InputStream inspectionsFile,
                                       String search, String hazardLevel, int lessNumCrit, int greaterNumCrit) {

        readRestaurantData(restaurantFile, search, hazardLevel, lessNumCrit, greaterNumCrit);
        populateInspections(inspectionsFile);

        filter = 1;
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

    private void readRestaurantData(InputStream file, String search, String hazardLevel,
                                    int lessNumCrit, int greaterNumCrit) {
        if (file == null) { return; }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file, Charset.forName("UTF-8"))
        );

        String line;

        try {
            /** Step over header **/
            reader.readLine();

            while (((line = reader.readLine()) != null)) {
                Restaurant restaurant = new Restaurant(line);
                if (!search.equals("")){
                    if (lessNumCrit>0) {
                        if (greaterNumCrit<Integer.MAX_VALUE) {
                            if (!hazardLevel.equals("")) {
                                if (restaurant.getName().toLowerCase().contains(search.toLowerCase()) &&
                                        restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                        restaurant.getNumCriticalIssues()>=greaterNumCrit &&
                                        restaurant.getHazard().equals(hazardLevel)){

                                    this.addNew(restaurant);
                                }
                            }
                            else {
                                if (restaurant.getName().toLowerCase().contains(search.toLowerCase()) &&
                                        restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                        restaurant.getNumCriticalIssues()>=greaterNumCrit ){

                                    this.addNew(restaurant);
                                }
                            }
                        }
                        else {
                            if (!hazardLevel.equals("")) {
                                if (restaurant.getName().toLowerCase().contains(search.toLowerCase()) &&
                                        restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                        restaurant.getHazard().equals(hazardLevel)){

                                    this.addNew(restaurant);
                                }
                            }
                            else {
                                if (restaurant.getName().toLowerCase().contains(search.toLowerCase()) &&
                                        restaurant.getNumCriticalIssues()<=lessNumCrit){

                                    this.addNew(restaurant);
                                }
                            }
                        }
                    }
                    else {
                        if (greaterNumCrit<Integer.MAX_VALUE) {
                            if (!hazardLevel.equals("")) {
                                if (restaurant.getName().toLowerCase().contains(search.toLowerCase()) &&
                                        restaurant.getNumCriticalIssues()>=greaterNumCrit &&
                                        restaurant.getHazard().equals(hazardLevel)){

                                    this.addNew(restaurant);
                                }
                            }
                            else {
                                if (restaurant.getName().toLowerCase().contains(search.toLowerCase()) &&
                                        restaurant.getNumCriticalIssues()>=greaterNumCrit ){

                                    this.addNew(restaurant);
                                }
                            }
                        }
                        else {
                            if (!hazardLevel.equals("")) {
                                if (restaurant.getName().toLowerCase().contains(search.toLowerCase()) &&
                                        restaurant.getHazard().equals(hazardLevel)){

                                    this.addNew(restaurant);
                                }
                            }
                            else {
                                if (restaurant.getName().toLowerCase().contains(search.toLowerCase())){
                                    this.addNew(restaurant);
                                }
                            }
                        }

                    }
                }
                else {
                    if (lessNumCrit>0) {
                        if (greaterNumCrit<Integer.MAX_VALUE) {
                            if (!hazardLevel.equals("")) {
                                if (restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                        restaurant.getNumCriticalIssues()>=greaterNumCrit &&
                                        restaurant.getHazard().equals(hazardLevel)){

                                    this.addNew(restaurant);
                                }
                            }
                            else {
                                if (restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                        restaurant.getNumCriticalIssues()>=greaterNumCrit ){

                                    this.addNew(restaurant);
                                }
                            }
                        }
                        else {
                            if (!hazardLevel.equals("")) {
                                if (restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                        restaurant.getHazard().equals(hazardLevel)){

                                    this.addNew(restaurant);
                                }
                            }
                            else {
                                if (restaurant.getNumCriticalIssues()<=lessNumCrit){
                                    this.addNew(restaurant);
                                }
                            }
                        }
                    }
                    else {
                        if (greaterNumCrit<Integer.MAX_VALUE) {
                            if (!hazardLevel.equals("")) {
                                if (restaurant.getNumCriticalIssues()>=greaterNumCrit &&
                                        restaurant.getHazard().equals(hazardLevel)){

                                    this.addNew(restaurant);
                                }
                            }
                            else {
                                if (restaurant.getNumCriticalIssues()>=greaterNumCrit ){
                                    this.addNew(restaurant);
                                }
                            }
                        }
                        else {
                            if (!hazardLevel.equals("")) {
                                if (restaurant.getHazard().equals(hazardLevel)){
                                    this.addNew(restaurant);
                                }
                            }
                            else {
                                this.addNew(restaurant);
                            }
                        }

                    }
                }

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


    private void sortInspectionsForEveryRestaurant() {
        int size = this.restaurantsList.size();

        for (int i = 0; i < size; i++) {
            Collections.sort(restaurantsList.get(i).getInspectionsList(), new NewSortInspectionsByDate());
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
        Collections.sort(restaurantsList, new SortRestaurantsByNameAlphabet());
    }

    @Override
    @NonNull
    public Iterator<Restaurant> iterator() {
        return restaurantsList.iterator();
    }
}

class SortRestaurantsByNameAlphabet implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        return o1.compareTo(o2);
    }
}

class NewSortInspectionsByDate implements  Comparator<Inspection> {
    @Override
    public int compare(Inspection o1, Inspection o2) {
        return o1.compareTo(o2);
    }
}
