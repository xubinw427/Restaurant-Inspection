package ca.cmpt276.restaurantinspection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantInfoActivity extends AppCompatActivity {
    public static final String EXTRA = "cmpt276.restaurantinspection.EXTRA";
    private int index;
    //add restaurantManager getInstance here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Current Restaurant Name");
        actionBar.setElevation(0);

        index = getIntent().getIntExtra(EXTRA, 0);

        /** ================= REPLACE INTENT FUNCTION BELOW ================**/

        Button btn = findViewById(R.id.inspection_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RestaurantInspectionActivity.makeIntent(RestaurantInfoActivity.this, index)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent);
                finish();
            }
        });

//        setData();
    }

//    public void setData(){
//        String name = manager.getList().get(index).getName();
//        String address = manager.getList().get(index).getAddress();
//        String coords = manager.getList().get(index).getCoordinates();
//
//        TextView textAddress = findViewById(R.id.text_location);
//        TextView textCoords = findViewById(R.id.text_coordinate);
//
//        textAddress.setText("" + address);
//        textCoords.setText("" + coords);
//    }

    public static Intent makeLaunchIntent(Context c, int index){
        Intent intent = new Intent(c, RestaurantInfoActivity.class);
        intent.putExtra(EXTRA, index);
        return intent;
    }
}
