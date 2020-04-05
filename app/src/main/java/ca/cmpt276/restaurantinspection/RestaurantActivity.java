package ca.cmpt276.restaurantinspection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Adapters.RestaurantAdapter;

/** List of Restaurants **/
public class RestaurantActivity extends AppCompatActivity implements RestaurantAdapter.OnRestaurantListener {
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setElevation(0);

        extractRestaurants();

        mapView();
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

    @Override
    public void onRestaurantClick(int position) {
        restaurantManager.setCurrRestaurantPosition(position);
        restaurantManager.setFromList(1);
        restaurantManager.setFromMap(0);

        Intent intent = new Intent(this, RestaurantInfoActivity.class);
        startActivity(intent);
    }

    public static Intent makeRestaurantMapIntent(Context c) {
        return new Intent(c, RestaurantMapActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
