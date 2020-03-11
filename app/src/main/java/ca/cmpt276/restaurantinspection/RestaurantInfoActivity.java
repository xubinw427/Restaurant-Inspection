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
    private static final String EXTRA = "ca.cmpt276.restaurantinspection - EXTRA";
    private Restaurant restaurant;
    //add restaurantManager getInstance here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Current Restaurant Name");
        actionBar.setElevation(0);
        extractRestaurantData();

        /** ================= REPLACE INTENT FUNCTION BELOW ================**/

        Button btn = findViewById(R.id.inspection_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestaurantInspectionActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                //finish();
            }
        });


    }

    private void extractRestaurantData()
    {
        Intent intent = getIntent();
        int index = intent.getIntExtra(EXTRA, 0);

        System.out.println("Now we are processing " + index + " th Restaurant.");

        RestaurantManager restaurants = RestaurantManager.getInstance();
        restaurant = restaurants.getTheOneAt(index);

        TextView location = (TextView) findViewById(R.id.text_location);
        location.setText(" " + restaurant.getAddress());

        TextView coordinate = (TextView) findViewById(R.id.text_coordinate);

        double latitude = restaurant.getLatitude();
        double longitude = restaurant.getLongitude();

        String coordinateInString = latitude + " " + longitude;

        coordinate.setText(" " + coordinateInString);
    }

    public static Intent makeLaunchIntent(Context c, int position){
        Intent intent = new Intent(c, RestaurantInfoActivity.class);
        intent.putExtra(EXTRA, position);
        return intent;
    }
}