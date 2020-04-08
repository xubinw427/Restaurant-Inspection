package ca.cmpt276.restaurantinspection.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** Singleton to Keep Track of All Restaurants in Data **/
public class RestaurantManager implements Iterable<Restaurant> {
    private ArrayList<Restaurant> restaurantsList = new ArrayList<>();
    private ArrayList<Restaurant> favoriteList = new ArrayList<>();
    private static RestaurantManager instance;
    private ViolationsMap violationsMap;
    private int currRestaurantPosition;
    private int currInspectionPosition;
    private int fromMap = 0;
    private int fromList = 0;
    private int loadFaves = 0;

    /** Private to prevent anyone else from instantiating. **/
    private RestaurantManager(InputStream restaurantFile, InputStream inspectionsFile) {
        readRestaurantData(restaurantFile);
        violationsMap = ViolationsMap.getInstance();
        populateInspections(inspectionsFile);
    }

    public static RestaurantManager getInstance() {
        if (instance == null) {
            throw new AssertionError(
                    "RestaurantManager.init(InputStream file) must be called first.");
        }

        return instance;
    }

    public static void init(InputStream restaurantFile, InputStream inspectionsFile) {
        if (instance != null) {
            return;
        }

        instance = new RestaurantManager(restaurantFile, inspectionsFile);
    }

    public void reset() {
        instance = null;
    }

    public ArrayList<Restaurant> getList() {
        return restaurantsList;
    }

    public ArrayList<Restaurant> getFavoriteList() {
        return favoriteList;
    }

    public ArrayList<Restaurant> getRestaurantsList() {
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

    public void setFavoriteList(ArrayList<Restaurant> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public void saveFavoriteList(ArrayList<Restaurant> favoriteList, Context context){
//        try {
            Set<String> faveStrings = new HashSet<>();
            SharedPreferences pref = context.getSharedPreferences("favourites_list", 0);

            for (Restaurant restaurant: favoriteList) {
                String id = restaurant.getId();
                int size = restaurant.getInspectionsList().size();
                String fav = id + "," + size;

                faveStrings.add(fav);
            }

            pref.edit().putStringSet("favourites_list", faveStrings).apply();

//            String filename = "favorite_list";
//
//            FileOutputStream outputStream;
//            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
//
//            for (Restaurant restaurant: favoriteList) {
//                String id = restaurant.getId();
//                int size = restaurant.getInspectionsList().size();
//                String fav = id + "," + size + "\n";
//
//                try {
//                    outputStream.write(fav.getBytes());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            outputStream.close();



//            File file = Environment.getExternalStorageDirectory();
//            File filename = new File(file, "favoritelist");
//            FileOutputStream fos = new FileOutputStream(filename);
//            ObjectOutputStream out = new ObjectOutputStream(fos);
//            out.writeObject(favoriteList);
//            out.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    public void readFavoriteList(Context context){
        if (loadFaves == 0) {
            SharedPreferences pref = context.getSharedPreferences("favourites_list", 0);
            Set<String> newSet = new HashSet<>(pref.getStringSet("favourites_list", new HashSet<String>()));

            for (String str : newSet) {
                System.out.println(str);
                String[] data = str.split(",");

                System.out.println(str);

                for (Restaurant restaurant : restaurantsList) {
                    if (restaurant.getId().equals(data[0])) {
                        this.favoriteList.add(restaurant);
                        System.out.println(data[1].trim());
                        System.out.println("****************");
                        restaurant.setOldNumInspections(Integer.parseInt(data[1].trim()));
                        restaurant.setFavorite(true);
                    }
                }
            }

            loadFaves = 1;
        }

//        if (file == null) { return; }
//
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(file, Charset.forName("UTF-8"))
//        );
//
//        String line;
//
//        try {
//            while (((line = reader.readLine()) != null)) {
//                System.out.println(line);
//                String[] data = line.split(",");
//
//                for (Restaurant restaurant : restaurantsList) {
//                    if (restaurant.getId() == data[0]) {
//                            this.favoriteList.add(restaurant);
//                            restaurant.setOldNumInspections(Integer.parseInt(data[1]));
//                    }
//                }
//            }



//            File file = Environment.getExternalStorageDirectory();
//            File filename = new File(file, "favoritelist");
//            FileInputStream fis = new FileInputStream(filename);
//            ObjectInputStream in = new ObjectInputStream(fis);
//            setFavoriteList((ArrayList<Restaurant>) in.readObject());
//            in.close();




//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        try {
//            file.close();
//        }
//        catch (IOException ex) {
//            throw new RuntimeException("ERROR: Failed to close " + file);
//        }
    }

    public void addFaveToInternal(Restaurant restaurant, Context context) {
        SharedPreferences pref = context.getSharedPreferences("favourites_list", 0);
        Set<String> newSet = new HashSet<>(pref.getStringSet("favourites_list", new HashSet<String>()));

        String id = restaurant.getId();
        int size = restaurant.getInspectionsList().size();
        String fav = id + "," + size;

        newSet.add(fav);

        pref.edit().putStringSet("favourites_list", newSet).apply();
    }

    public void removeFaveFromInternal (Restaurant restaurant, Context context) {
        SharedPreferences pref = context.getSharedPreferences("favourites_list", 0);
        Set<String> newSet = new HashSet<>(pref.getStringSet("favourites_list", new HashSet<String>()));

        for (String str : newSet) {
            String[] data = str.split(",");
            if (restaurant.getId().equals(data[0])) {
                System.out.println("REMOVING!!!!");
                System.out.println(str);
                newSet.remove(str);
            }
        }

        pref.edit().putStringSet("favourites_list", newSet).apply();
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
