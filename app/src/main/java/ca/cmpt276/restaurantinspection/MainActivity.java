package ca.cmpt276.restaurantinspection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Adapters.RestaurantAdapter;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.TestRestaurant;
import ca.cmpt276.restaurantinspection.Model.Violation;
import ca.cmpt276.restaurantinspection.Model.ViolationsMap;

public class MainActivity extends AppCompatActivity implements RestaurantAdapter.OnRestaurantListener {
    /** == TESTING == **/
    private RecyclerView restaurantRecyclerView;
    private RecyclerView.Adapter restaurantAdapter;
    private RecyclerView.LayoutManager restaurantLayoutManager;
    ArrayList<TestRestaurant> tester;
    /** == END TESTING == **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Restaurants List");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setElevation(0);


        /** == TESTING == **/
        tester = new ArrayList<>();
        tester.add(new TestRestaurant("LEE YUEN SEAFOOD RESTAURANT", "May 5th, 2018", "low", "3"));
        tester.add(new TestRestaurant("RESTAURANT B", "August 28th, 2018", "mod", "5"));
        tester.add(new TestRestaurant("RESTAURANT C", "January 6th, 2018", "high", "11"));
        tester.add(new TestRestaurant("RESTAURANT D", "May 02nd, 2018", "mod", "6"));
        tester.add(new TestRestaurant("RESTAURANT E", "June 30th, 2018", "low", "2"));
        tester.add(new TestRestaurant("RESTAURANT F", "December 15th, 2018", "high", "9"));
        tester.add(new TestRestaurant("RESTAURANT G", "February 10th, 2018", "low", "1"));

        restaurantRecyclerView = findViewById(R.id.rv);
        restaurantRecyclerView.setHasFixedSize(true);
        restaurantLayoutManager = new LinearLayoutManager(this);
        restaurantAdapter = new RestaurantAdapter(tester, this);

        restaurantRecyclerView.setLayoutManager(restaurantLayoutManager);
        restaurantRecyclerView.setAdapter(restaurantAdapter);
        /** == END TESTING == **/
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

    /** == TESTING == **/
    @Override
    public void onRestaurantClick(int position) {
        /** Should use the position below to store clicked Restaurant as currRestaurant **/
        tester.get(position);
        Intent intent = new Intent(this, TESTActivity.class);
        startActivity(intent);
    }
    /** == END TESTING == **/
}
