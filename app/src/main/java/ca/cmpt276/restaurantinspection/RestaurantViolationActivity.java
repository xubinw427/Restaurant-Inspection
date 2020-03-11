package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Adapters.ViolationAdapter;
import ca.cmpt276.restaurantinspection.Model.Inspection;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.Violation;

public class RestaurantViolationActivity extends AppCompatActivity implements ViolationAdapter.OnViolationListener {
    private Inspection inspection;
    ArrayList<Violation> violationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation);

        RestaurantManager restaurantManager = RestaurantManager.getInstance();
        int restaurantIndex = restaurantManager.getCurrRestaurantPosition();
        int inspectionIndex = restaurantManager.getCurrInspectionPosition();
        Restaurant restaurant = restaurantManager.getRestaurantAt(restaurantIndex);
        inspection = restaurant.getInspectionAt(inspectionIndex);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(restaurant.getName());
        actionBar.setElevation(0);

        TextView dateAndLevel = this.findViewById(R.id.date_level);
        TextView inspectionType = this.findViewById(R.id.inspection_type);
        TextView numCritIssues = this.findViewById(R.id.num_crit_issues_details);
        TextView numNonCritIssues = this.findViewById(R.id.num_noncrit_issues_details);

        dateAndLevel.setText(inspection.getFullDate() + " | " +
                inspection.getHazardRating().toUpperCase());
        inspectionType.setText(inspection.getInspType());
        numNonCritIssues.setText(Integer.toString(inspection.getNumNonCritical()));
        numCritIssues.setText(Integer.toString(inspection.getNumCritical()));

        extractInspectionViolations();
    }

    private void extractInspectionViolations() {
        RecyclerView violationRecyclerView;
        RecyclerView.Adapter violationAdapter;
        RecyclerView.LayoutManager violationLayoutManager;

        if (inspection.getViolationsList() == null) {
            TextView noViolationsMsg = this.findViewById(R.id.no_violations_msg);
            noViolationsMsg.setText("There were no violations \nfound for this inspection.");
        }
        else {
            violationList = inspection.getViolationsList();
            violationRecyclerView = findViewById(R.id.rv3);
            violationRecyclerView.setHasFixedSize(true);

            violationLayoutManager = new LinearLayoutManager(this);
            violationRecyclerView.setLayoutManager(violationLayoutManager);

            violationAdapter = new ViolationAdapter(violationList, this);
            violationRecyclerView.setAdapter(violationAdapter);
        }
    }

    @Override
    public void onViolationClick(int position) {
        String longDescription = violationList.get(position).getLongDescription();
        Toast toast = Toast.makeText(this, longDescription, Toast.LENGTH_LONG);
        toast.show();
    }

}
