package ca.cmpt276.restaurantinspection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;

/** Basic Info Page of Restaurant (Address & Coordinates) **/
public class RestaurantInfoActivity extends AppCompatActivity {
    private static final int LAUNCH_MAP_ACTIVITY = 1;
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private Restaurant restaurant;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        index = restaurantManager.getCurrRestaurantPosition();
        restaurant = restaurantManager.getRestaurantAt(index);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(restaurant.getName());
            actionBar.setElevation(0);
        }
        extractRestaurantInfo();
        setFavoriteBtn();
        startRestaurantInspectionActivityBtn();
    }

    public void setFavoriteBtn(){
        final Button btn = findViewById(R.id.button_favorite);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (restaurantManager.getRestaurantAt(index).isFavorite()) {
                        restaurantManager.getRestaurantAt(index).setFavorite(false);
                        btn.setBackgroundResource(R.drawable.button_not_favorite);
                    }else{
                        restaurantManager.getRestaurantAt(index).setFavorite(true);
                        btn.setBackgroundResource(R.drawable.button_favorite);
                    }
//                    Intent intent = makeRestaurantInspectionIntent(getApplicationContext());
//                    startActivity(intent);
//                    finish();
                }
            });

    }

    private void startRestaurantInspectionActivityBtn() {
        Button btn = findViewById(R.id.restaurants_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = makeRestaurantInspectionIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImplement();
    }

    private void extractRestaurantInfo() {
        TextView location = findViewById(R.id.text_location);
        location.setText(restaurant.getAddress());

        TextView coordinate = findViewById(R.id.text_coordinate);
        double latitude = restaurant.getLatitude();
        double longitude = restaurant.getLongitude();

        coordinate.setText(getString(R.string.str_coordinates, latitude, longitude));
    }

    public static Intent makeRestaurantInspectionIntent(Context c){
        return new Intent(c, RestaurantInspectionActivity.class);
    }

    public void onClick(View v) {
        Intent intent = makeRestaurantMapIntent(getApplicationContext());

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        RestaurantInfoActivity.this.startActivityForResult(intent, LAUNCH_MAP_ACTIVITY);
        finish();
    }

    public static Intent makeRestaurantMapIntent(Context c){
        return new Intent(c, RestaurantMapActivity.class);
    }

    private Intent getParentActivityIntentImplement() {
        Intent intent = null;

        if (restaurantManager.getFromList() == 1) {
            intent = new Intent(this, RestaurantActivity.class);
            restaurantManager.setFromList(0);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        else if (restaurantManager.getFromMap() == 1) {
            intent = new Intent(this, RestaurantMapActivity.class);
            restaurantManager.setFromMap(0);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        return intent;
    }
}