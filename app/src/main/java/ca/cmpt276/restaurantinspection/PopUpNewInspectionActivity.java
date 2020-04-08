package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Adapters.FavoriteAdapter;
import ca.cmpt276.restaurantinspection.Adapters.ViolationAdapter;
import ca.cmpt276.restaurantinspection.Model.Inspection;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.Violation;

public class PopUpNewInspectionActivity extends AppCompatActivity implements FavoriteAdapter.OnFavoriteListener {

    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_new_inspection);

        int restaurantIndex = restaurantManager.getCurrRestaurantPosition();
        restaurant = restaurantManager.getRestaurantAt(restaurantIndex);

        setFinishOnTouchOutside(false);

        setScreenSize();
        extractInspection();
        cancelBtnPressed();
    }

    private void setScreenSize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
    }

    private void cancelBtnPressed(){
        Button btn = findViewById(R.id.btn_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void extractInspection() {
        RecyclerView favoriteRecyclerView;
        RecyclerView.Adapter favoriteAdapter;
        RecyclerView.LayoutManager favoriteLayoutManager;

        ArrayList<Restaurant> favoriteList = restaurantManager.getFavoriteList();
        ArrayList<Restaurant> restaurantList = restaurantManager.getRestaurantsList();

        favoriteRecyclerView = findViewById(R.id.rv4);
        favoriteRecyclerView.setHasFixedSize(true);

        favoriteLayoutManager = new LinearLayoutManager(this);
        favoriteRecyclerView.setLayoutManager(favoriteLayoutManager);

        favoriteAdapter = new FavoriteAdapter(favoriteList, restaurantList, this);
        favoriteRecyclerView.setAdapter(favoriteAdapter);


    }

    @Override
    public void onViolationClick(int position) {

    }

}
