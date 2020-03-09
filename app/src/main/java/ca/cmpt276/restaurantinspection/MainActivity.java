package ca.cmpt276.restaurantinspection;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;

public class MainActivity extends AppCompatActivity {
    private RestaurantManager restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        restaurants = RestaurantManager.getInstance();
        readRestaurantDate();

    }

    private void readRestaurantDate() {
        InputStream in = getResources().openRawResource(R.raw.restaurants_itr1);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, Charset.forName("UTF-8"))
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
                restaurants.addNew(temp);
                //Log.d("MyActivity", "Just Created" + temp);
            }
        } catch (IOException e) {
            //Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
        restaurants.sortByAlphabet();
        int size = restaurants.getsize();
        for(int i = 0; i < size; i++)
        {
            Log.d("MyActivity", "Restaurant" + i +": " + restaurants.getTheOneAt(i).getName());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
