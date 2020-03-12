package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import ca.cmpt276.restaurantinspection.Adapters.ViolationAdapter;
import ca.cmpt276.restaurantinspection.Model.Inspection;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.Violation;

public class RestaurantViolationActivity extends AppCompatActivity implements ViolationAdapter.OnViolationListener {
    private Inspection inspection;
    ArrayList<Violation> violationList;
    Toast toast;

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

        ImageView inspectionOverview = this.findViewById(R.id.inspection_overview);
        TextView dateAndLevel = this.findViewById(R.id.date_level);
        TextView inspectionType = this.findViewById(R.id.inspection_type);
        TextView numNonCritIssues = this.findViewById(R.id.num_noncrit_issues_details);
        TextView numCritIssues = this.findViewById(R.id.num_crit_issues_details);

        switch(inspection.getHazardRating()) {
            case "High":
                inspectionOverview.setImageResource(R.drawable.inspection_det_high);
                break;
            case "Moderate":
                inspectionOverview.setImageResource(R.drawable.inspection_det_med);
                break;
            case "Low":
                inspectionOverview.setImageResource(R.drawable.inspection_det_low);
        }

        dateAndLevel.setText(getString(R.string.str_date_level, inspection.getFullDate(),
                                        inspection.getHazardRating().toUpperCase()));
        inspectionType.setText(inspection.getInspType());
        numCritIssues.setText(getString(R.string.str_num_issues, inspection.getNumCritical()));
        numNonCritIssues.setText(getString(R.string.str_num_issues, inspection.getNumNonCritical()));

        extractInspectionViolations();
    }

    private void extractInspectionViolations() {
        RecyclerView violationRecyclerView;
        RecyclerView.Adapter violationAdapter;
        RecyclerView.LayoutManager violationLayoutManager;

        if (inspection.getViolationsList() == null) {
            TextView noViolationsMsg = this.findViewById(R.id.no_violations_msg);
            noViolationsMsg.setText(R.string.str_no_violation);
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
        if (toast != null) {
            toast.cancel();
        }

        String longDescription = violationList.get(position).getLongDescription();
        toast = Toast.makeText(this, longDescription, Toast.LENGTH_LONG);
        toast.show();
    }

}