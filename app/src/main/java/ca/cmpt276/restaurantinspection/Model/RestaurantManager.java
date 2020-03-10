package ca.cmpt276.restaurantinspection.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class RestaurantManager implements Iterable<Restaurant> {
    private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static RestaurantManager INSTANCE;

    public RestaurantManager(InputStream file) {
        //Private to prevent anyone else from instantiating.
    }

    public static RestaurantManager getInstance() {
        if(INSTANCE == null) {
            throw new AssertionError(
                    "RestaurantManager.init(InputStream file must be called first.");
        }

        return INSTANCE;
    }

    public static RestaurantManager init(InputStream file) {
        if (INSTANCE != null) {
            throw new AssertionError("RestaurantManager has already been initialized.");
        }

        return new RestaurantManager(file);
    }

    public static ArrayList<Restaurant> getList() {
        return restaurants;
    }

    public void addNew(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public Restaurant getTheOneAt(int index) {
        return restaurants.get(index);
    }

    public int getsize() {
        return restaurants.size();
    }

    public void sortByAlphabet() {
        Arrays.sort(new ArrayList[]{restaurants});
    }

    public void readRestaurantData(InputStream file) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file, Charset.forName("UTF-8"))
        );

        String line = "";
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

        this.sortByAlphabet();
    }

    @Override
    public Iterator<Restaurant> iterator() {
        return restaurants.iterator();
    }
}
