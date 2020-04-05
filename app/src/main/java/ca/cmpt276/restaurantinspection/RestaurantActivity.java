package ca.cmpt276.restaurantinspection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Adapters.RestaurantAdapter;
import ca.cmpt276.restaurantinspection.Model.SearchManager;

/** List of Restaurants **/
public class RestaurantActivity extends AppCompatActivity implements RestaurantAdapter.OnRestaurantListener {
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private SearchManager searchManager;
    private final String RESTAURANT_FILENAME = "update_restaurant";
    private final String INSPECTION_FILENAME = "update_inspection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setElevation(0);

        if (SearchManager.getInstance() == null) {
            extractRestaurants();
        }
        else{
            searchManager= SearchManager.getInstance();
            extractSearchedRestaurants();
        }
        filterRestaurants();


        mapView();
    }

    private void filterRestaurants() {

        searchManager = SearchManager.getInstance();
        String search = "";
        String hazardLevel= "";
        int lessNumCrit = -1;
        int greatNumCrit = Integer.MAX_VALUE;

        InputStream restaurantsIn = getResources().openRawResource(R.raw.restaurants_itr1);
        InputStream inspectionsIn = getResources().openRawResource(R.raw.inspectionreports_itr1);

        FileInputStream internalRestaurants = null;
        FileInputStream internalInspections = null;
        InputStream restaurantInput = null;
        InputStream inspectionsInput = null;

        try {
            internalRestaurants = openFileInput(RESTAURANT_FILENAME);
            internalInspections = openFileInput(INSPECTION_FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (internalRestaurants == null && internalInspections == null) {
            restaurantInput = restaurantsIn;
            inspectionsInput = inspectionsIn;
        }
        else {
            restaurantInput = internalRestaurants;
            inspectionsInput = internalInspections;
        }

        //Put buttonOnClicks etc here
        boolean searchBtnPushed = true;
        if (searchBtnPushed==true){

            //for testing
            search = "bar";
            hazardLevel="Low";
//            lessNumCrit = 2;

            SearchManager.init(restaurantInput, inspectionsInput, search,hazardLevel,lessNumCrit,greatNumCrit);
            searchManager = SearchManager.getInstance();
            extractSearchedRestaurants();
        }
        boolean clearBtnPushed = false;
        if (clearBtnPushed==true){
            searchManager.reset();
        }

    }

    private void mapView() {
        Button btn = findViewById(R.id.map_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = makeRestaurantMapIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
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

    private void extractSearchedRestaurants() {
        RecyclerView restaurantRecyclerView = findViewById(R.id.rv);
        restaurantRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager restaurantLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter restaurantAdapter = new RestaurantAdapter
                (searchManager, this);

        restaurantRecyclerView.setLayoutManager(restaurantLayoutManager);
        restaurantRecyclerView.setAdapter(restaurantAdapter);
    }
    @Override
    public void onRestaurantClick(int position) {
        if (SearchManager.getInstance() == null) {
            restaurantManager.setCurrRestaurantPosition(position);
            restaurantManager.setFromList(1);
            restaurantManager.setFromMap(0);
        }
        else {
            searchManager.setCurrRestaurantPosition(position);
            searchManager.setFromList(1);
            searchManager.setFromMap(0);
        }

        Intent intent = new Intent(this, RestaurantInfoActivity.class);
        startActivity(intent);
    }

    public static Intent makeRestaurantMapIntent(Context c) {
        return new Intent(c, RestaurantMapActivity.class);
    }
}
