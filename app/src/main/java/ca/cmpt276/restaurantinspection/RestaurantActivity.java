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

import java.io.InputStream;

import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.ViolationsMap;

import ca.cmpt276.restaurantinspection.Adapters.RestaurantAdapter;

public class RestaurantActivity extends AppCompatActivity implements RestaurantAdapter.OnRestaurantListener {
    private RestaurantManager restaurantManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setElevation(0);

        InputStream restaurantsIn = getResources().openRawResource(R.raw.restaurants_itr1);
        InputStream inspectionsIn = getResources().openRawResource(R.raw.inspectionreports_itr1);
        InputStream violationsIn = getResources().openRawResource(R.raw.all_violations);

        ViolationsMap.init(violationsIn);
        RestaurantManager.init(restaurantsIn, inspectionsIn);
        restaurantManager = RestaurantManager.getInstance();

        extractRestaurants();

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

    @Override
    public void onRestaurantClick(int position) {
        restaurantManager.setCurrRestaurantPosition(position);

        Intent intent = new Intent(this, RestaurantInfoActivity.class);
        startActivity(intent);
    }

    public static Intent makeRestaurantMapIntent(Context c){
        return new Intent(c, RestaurantMapActivity.class);
    }
}
