package ca.cmpt276.restaurantinspection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Adapters.InspectionAdapter;
import ca.cmpt276.restaurantinspection.Model.Inspection;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.TestInspection;

import static ca.cmpt276.restaurantinspection.RestaurantInfoActivity.EXTRA;

/** TO-DO CHANGE NAME FROM tester
 *  Re-point TestInspection Data to ACTUAL Inspection data **/
public class RestaurantInspectionActivity extends AppCompatActivity implements InspectionAdapter.OnInspectionListener {

    private RestaurantManager manager = RestaurantManager.getInstance();
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    ArrayList<TestInspection> tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Current Restaurant Name");
        actionBar.setElevation(0);



        Restaurant restaurant = manager.getList().get(0);

        ArrayList<Inspection> inspectionList = restaurant.getInspections();


        myRecyclerView = findViewById(R.id.rv2);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myAdapter = new InspectionAdapter(inspectionList, this);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);


        /** ================= REPLACE INTENT FUNCTION BELOW ================**/

        Button btn = findViewById(R.id.info_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestaurantInfoActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
    }

    /** == TEST == **/
    @Override
    public void onInspectionClick(int position) {
        Toast toast = Toast.makeText(this, "YOU CLICKED", Toast.LENGTH_SHORT);
        toast.show();
    }
    /** == END TEST == **/

    /** =========== INTENT LAUNCHER HERE >> findViewById(R.id.inspAct ============ **/
    public static Intent makeIntent(Context c, int index){
        Intent intent = new Intent(c, RestaurantInspectionActivity.class);
        intent.putExtra(EXTRA, index);
        return intent;
    }
}