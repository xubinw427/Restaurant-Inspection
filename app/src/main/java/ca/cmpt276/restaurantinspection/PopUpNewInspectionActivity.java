package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
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

    private Toast toast;
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_new_inspection);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        int restaurantIndex = restaurantManager.getCurrRestaurantPosition();
//        int inspectionIndex = restaurantManager.getCurrInspectionPosition();
        restaurant = restaurantManager.getRestaurantAt(restaurantIndex);
//        inspection = restaurant.getInspectionAt(inspectionIndex);

//        ActionBar actionBar = getSupportActionBar();

//        if (actionBar != null) {
//            actionBar.setTitle(restaurant.getName());
//            actionBar.setElevation(0);
//        }

//        setNewInspectionTextAndImages();

        extractInspection();
    }

    private void setNewInspectionTextAndImages() {
//        ImageView hazardIcon = this.findViewById(R.id.hazard_level_icon);
//        TextView restaurantName = this.findViewById(R.id.restaurant_name);
//        TextView newInspectionDate = this.findViewById(R.id.newest_inspection_date);
//
//        //isi haz level terakhir
//        switch (inspection.getHazardRating()) {
//            case "High":
//                hazardIcon.setImageResource(R.drawable.newinsp_high);
//                break;
//            case "Moderate":
//                hazardIcon.setImageResource(R.drawable.newinsp_mod);
//                break;
//            case "Low":
//                hazardIcon.setImageResource(R.drawable.newinsp_low);
//                break;
//            case "None":
//                hazardIcon.setImageResource(R.drawable.newinsp_none);
//                break;
//        }
//        //isi nama resto
//        //isi date terakhir
//        restaurantName.setText(restaurantManager.getClass().getName());
////        dateAndLevel.setText(getString(R.string.str_date_level, inspection.getFullDate(),
////                inspection.getHazardRating().toUpperCase()));
////        inspectionType.setText(inspection.getInspType());
////        numCritIssues.setText(getString(R.string.str_num_issues, inspection.getNumCritical()));
////        numNonCritIssues.setText(getString(R.string.str_num_issues, inspection.getNumNonCritical()));
    }

    private void extractInspection() {
        RecyclerView favoriteRecyclerView;
        RecyclerView.Adapter favoriteAdapter;
        RecyclerView.LayoutManager favoriteLayoutManager;

        ArrayList<Restaurant> favoriteList = restaurantManager.getList();

        favoriteRecyclerView = findViewById(R.id.rv4);
        favoriteRecyclerView.setHasFixedSize(true);

        favoriteLayoutManager = new LinearLayoutManager(this);
        favoriteRecyclerView.setLayoutManager(favoriteLayoutManager);

        favoriteAdapter = new FavoriteAdapter(favoriteList, this);
        favoriteRecyclerView.setAdapter(favoriteAdapter);

    }

    @Override
    public void onViolationClick(int position) {
//        if (toast != null) {
//            toast.cancel();
//        }
//
//        String longDescription = violationList.get(position).getLongDescription();
//        toast = Toast.makeText(this, longDescription, Toast.LENGTH_LONG);
//        toast.show();
    }

}
