package ca.cmpt276.restaurantinspection.Model;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RestaurantManager implements Iterable<Restaurant> {
    private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static RestaurantManager instance;

    public RestaurantManager()
    {
        //Private to prevent anyone else from instantiating.
    }

    public static RestaurantManager getInstance()
    {
        if(instance == null)
        {
            instance = new RestaurantManager();
        }
        return instance;
    }

    public static ArrayList<Restaurant> getList()
    {
        return restaurants;
    }

    public void addNew(Restaurant restaurant)
    {
        restaurants.add(restaurant);
    }

    public Restaurant getTheOneAt(int index)
    {
        return restaurants.get(index);
    }

    public int getsize()
    {
        return restaurants.size();
    }
    public void sortByAlphabet()
    {
        Arrays.sort(new ArrayList[]{restaurants});

    }
    @Override
    public Iterator<Restaurant> iterator() {
        return restaurants.iterator();
    }
}
