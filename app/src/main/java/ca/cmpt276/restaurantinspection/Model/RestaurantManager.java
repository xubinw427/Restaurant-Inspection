package ca.cmpt276.restaurantinspection.Model;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ca.cmpt276.restaurantinspection.R;

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
    public void readRestaurantDate(InputStream file) {


        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            // Step over header
            reader.readLine();

            while (((line = reader.readLine()) != null))
            {
                // Split by ','
                //String[] tokens = line.split(",");

                // Read the data
                Restaurant temp = new Restaurant(line);
                this.addNew(temp);
                //Log.d("MyActivity", "Just Created" + temp);
            }
        } catch (IOException e) {
            //Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
        this.sortByAlphabet();
    }
    @Override
    public Iterator<Restaurant> iterator() {
        return restaurants.iterator();
    }
}
