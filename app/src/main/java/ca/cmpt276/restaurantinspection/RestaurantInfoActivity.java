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

public class RestaurantInfoActivity extends AppCompatActivity {
    private Restaurant restaurant;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        RestaurantManager restaurantManager = RestaurantManager.getInstance();
        index = restaurantManager.getCurrRestaurantPosition();
        restaurant = restaurantManager.getTheOneAt(index);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(restaurant.getName());
        actionBar.setElevation(0);

        extractRestaurantInfo();

        /** ================= REPLACE INTENT FUNCTION BELOW ================**/

        Button btn = findViewById(R.id.inspection_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = makeRestaurantInspectionIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
    }

    private void extractRestaurantInfo() {
        System.out.println("Now we are processing " + index + " th Restaurant.");

        TextView location = findViewById(R.id.text_location);
        location.setText(" " + restaurant.getAddress());

        TextView coordinate = findViewById(R.id.text_coordinate);

        double latitude = restaurant.getLatitude();
        double longitude = restaurant.getLongitude();

        String coordinateInString = latitude + ",  " + longitude;

        coordinate.setText(" " + coordinateInString);
    }

    public static Intent makeRestaurantInspectionIntent(Context c){
        return new Intent(c, RestaurantInspectionActivity.class);
    }
}