package ca.cmpt276.restaurantinspection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RestaurantInfoActivity extends AppCompatActivity {
    public static final String EXTRA = "cmpt276.restaurantinspection.EXTRA";
    private int index;
    //add restaurantManager getInstance here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Current Restaurant Name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        index = getIntent().getIntExtra(EXTRA, 0);

        /** ================= REPLACE INTENT FUNCTION BELOW ================**/

        Button btn = findViewById(R.id.inspectionButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestaurantInspectionActivity.class);
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
        Intent intent = new Intent(c, RestaurantInspectionActivity.class);
        intent.putExtra(EXTRA, index);
        return intent;
    }
}
