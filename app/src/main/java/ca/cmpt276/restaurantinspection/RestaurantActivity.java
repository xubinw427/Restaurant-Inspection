package ca.cmpt276.restaurantinspection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import android.widget.Toast;

import java.io.InputStream;

import ca.cmpt276.restaurantinspection.Model.Inspection;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.ViolationsMap;

import ca.cmpt276.restaurantinspection.Adapters.RestaurantAdapter;

public class RestaurantActivity extends AppCompatActivity implements RestaurantAdapter.OnRestaurantListener {
    private RestaurantManager restaurantManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Restaurants List");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setElevation(0);

        InputStream restaurantsIn = getResources().openRawResource(R.raw.restaurants_itr1);
        InputStream inspectionsIn = getResources().openRawResource(R.raw.inspectionreports_itr1);
        InputStream violationsIn = getResources().openRawResource(R.raw.all_violations);

        ViolationsMap.init(violationsIn);
        RestaurantManager.init(restaurantsIn, inspectionsIn);

        restaurantManager = RestaurantManager.getInstance();

        extractRestaurants();

        /** ================ TEST ===============**/
        for (Restaurant res : restaurantManager.getList()) {
            ArrayList<Inspection> ins = res.getInspectionsList();
            System.out.println(res.getName() + ": " + ins.size());
            for(Inspection inspections : ins) {
                System.out.println("\t\t" + inspections.getDateDisplay());
            }
        }
        /** ================ END TEST ===============**/
    }

    public void extractRestaurants() {
        RecyclerView restaurantRecyclerView = findViewById(R.id.rv);
        restaurantRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager restaurantLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter restaurantAdapter = new RestaurantAdapter
                (restaurantManager, this);

        restaurantRecyclerView.setLayoutManager(restaurantLayoutManager);
        restaurantRecyclerView.setAdapter(restaurantAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
////
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
////
////        return super.onOptionsItemSelected(item);
////    }

    /** == TESTING == **/
    @Override
    public void onRestaurantClick(int position) {
        Toast toast = Toast.makeText(this, "YOU CLICKED " + position +
                    " Restaurant" , Toast.LENGTH_SHORT);
        toast.show();

        restaurantManager.setCurrRestaurantPosition(position);

        Intent intent = new Intent(this, RestaurantInfoActivity.class);
        startActivity(intent);
    }
    /** == END TESTING == **/
}
