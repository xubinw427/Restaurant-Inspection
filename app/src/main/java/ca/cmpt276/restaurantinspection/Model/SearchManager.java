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

public class SearchManager implements Iterable<Restaurant>{

    private ArrayList<Restaurant> restaurantsList = new ArrayList<>();
    private static SearchManager instance;
    private ViolationsMap violationsMap;
    private int currRestaurantPosition;
    private int currInspectionPosition;
    private int fromMap = 0;
    private int fromList = 0;


    public SearchManager(String search, String hazardLevel, int lessNumCrit, int greaterNumCrit, ArrayList<Restaurant> restaurantsList, InputStream inspectionsFile) {
        violationsMap = ViolationsMap.getInstance();
        populateInspections(inspectionsFile);

        for (Restaurant restaurant : restaurantsList) {
            if (!search.equals("")){
                if (lessNumCrit>0) {
                    if (greaterNumCrit<Integer.MAX_VALUE) {
                        if (!hazardLevel.equals("")) {
                            if (restaurant.getName().contains(search) &&
                                restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                restaurant.getNumCriticalIssues()>=greaterNumCrit &&
                                restaurant.getHazard().equals(hazardLevel)){

                                restaurantsList.add(restaurant);
                            }
                        }
                        else {
                            if (restaurant.getName().contains(search) &&
                                    restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                    restaurant.getNumCriticalIssues()>=greaterNumCrit ){

                                restaurantsList.add(restaurant);
                            }
                        }
                    }
                    else {
                        if (!hazardLevel.equals("")) {
                            if (restaurant.getName().contains(search) &&
                                    restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                    restaurant.getHazard().equals(hazardLevel)){

                                restaurantsList.add(restaurant);
                            }
                        }
                        else {
                            if (restaurant.getName().contains(search) &&
                                    restaurant.getNumCriticalIssues()<=lessNumCrit){

                                restaurantsList.add(restaurant);
                            }
                        }
                    }
                }
                else {
                    if (greaterNumCrit<Integer.MAX_VALUE) {
                        if (!hazardLevel.equals("")) {
                            if (restaurant.getName().contains(search) &&
                                    restaurant.getNumCriticalIssues()>=greaterNumCrit &&
                                    restaurant.getHazard().equals(hazardLevel)){

                                restaurantsList.add(restaurant);
                            }
                        }
                        else {
                            if (restaurant.getName().contains(search) &&
                                    restaurant.getNumCriticalIssues()>=greaterNumCrit ){

                                restaurantsList.add(restaurant);
                            }
                        }
                    }
                    else {
                        if (!hazardLevel.equals("")) {
                            if (restaurant.getName().contains(search) &&
                                    restaurant.getHazard().equals(hazardLevel)){

                                restaurantsList.add(restaurant);
                            }
                        }
                        else {
                            if (restaurant.getName().contains(search)){
                                restaurantsList.add(restaurant);
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

                                restaurantsList.add(restaurant);
                            }
                        }
                        else {
                            if (restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                    restaurant.getNumCriticalIssues()>=greaterNumCrit ){

                                restaurantsList.add(restaurant);
                            }
                        }
                    }
                    else {
                        if (!hazardLevel.equals("")) {
                            if (restaurant.getNumCriticalIssues()<=lessNumCrit &&
                                    restaurant.getHazard().equals(hazardLevel)){

                                restaurantsList.add(restaurant);
                            }
                        }
                        else {
                            if (restaurant.getNumCriticalIssues()<=lessNumCrit){
                                restaurantsList.add(restaurant);
                            }
                        }
                    }
                }
                else {
                    if (greaterNumCrit<Integer.MAX_VALUE) {
                        if (!hazardLevel.equals("")) {
                            if (restaurant.getNumCriticalIssues()>=greaterNumCrit &&
                                    restaurant.getHazard().equals(hazardLevel)){

                                restaurantsList.add(restaurant);
                            }
                        }
                        else {
                            if (restaurant.getNumCriticalIssues()>=greaterNumCrit ){
                                restaurantsList.add(restaurant);
                            }
                        }
                    }
                    else {
                        if (!hazardLevel.equals("")) {
                            if (restaurant.getHazard().equals(hazardLevel)){
                                restaurantsList.add(restaurant);
                            }
                        }
                        else {
                            restaurantsList.add(restaurant);
                        }
                    }

                }
            }
        }
    }

    public static SearchManager getInstance() {
        if(instance == null) {
            throw new AssertionError(
                    "SearchManager.init(InputStream file) must be called first.");
        }

        return instance;
    }

    public static void init(String search, String hazardLevel, int lessNumCrit, int greaterNumCrit, ArrayList<Restaurant> restaurantsList, InputStream inspectionsFile) {
        if (instance != null) {
            return;
        }

        instance = new SearchManager(search, hazardLevel, lessNumCrit, greaterNumCrit, restaurantsList, inspectionsFile);
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


    private void sortInspectionsForEveryRestaurant() {
        int size = this.restaurantsList.size();

        for (int i = 0; i < size; i++) {
            Collections.sort(restaurantsList.get(i).getInspectionsList(), new SearchSortInspectionsByDate());
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

class SearchSortInspectionsByDate implements  Comparator<Inspection> {
    @Override
    public int compare(Inspection o1, Inspection o2) {
        return o1.compareTo(o2);
    }
}
