package ca.cmpt276.restaurantinspection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import ca.cmpt276.restaurantinspection.Adapters.InspectionAdapter;
import ca.cmpt276.restaurantinspection.Model.Inspection;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;

/** List of All Inspections on Record for Restaurant **/
public class RestaurantInspectionActivity extends AppCompatActivity implements InspectionAdapter.OnInspectionListener {
    private RestaurantManager restaurantManager;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);

        restaurantManager = RestaurantManager.getInstance();
        int index = restaurantManager.getCurrRestaurantPosition();
        restaurant = restaurantManager.getList().get(index);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(restaurant.getName());
            actionBar.setElevation(0);
        }
        extractRestaurantInspections();

        startRestaurantInfoActivityBtn();
    }

    private void startRestaurantInfoActivityBtn() {
        Button btn = findViewById(R.id.map_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = makeRestaurantInfoIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onInspectionClick(int position) {
        restaurantManager.setCurrInspectionPosition(position);

        Intent intent = new Intent(getApplicationContext(), RestaurantViolationActivity.class);
        startActivity(intent);
    }

    private void extractRestaurantInspections() {
        RecyclerView inspectionRecyclerView;
        RecyclerView.Adapter inspectionAdapter;
        RecyclerView.LayoutManager inspectionLayoutManager;

        ArrayList<Inspection> inspectionList = restaurant.getInspectionsList();

        inspectionRecyclerView = findViewById(R.id.rv2);
        inspectionRecyclerView.setHasFixedSize(true);

        inspectionLayoutManager = new LinearLayoutManager(this);
        inspectionRecyclerView.setLayoutManager(inspectionLayoutManager);

        inspectionAdapter = new InspectionAdapter(inspectionList, this);
        inspectionRecyclerView.setAdapter(inspectionAdapter);

        if (restaurant.getInspectionsList().size() == 0) {
            TextView noInspectionsMsg = this.findViewById(R.id.no_inspections_msg);
            noInspectionsMsg.setText(R.string.str_no_inspections);
        }
    }

    public static Intent makeRestaurantInfoIntent(Context c){
        return new Intent(c, RestaurantInfoActivity.class);
    }
}