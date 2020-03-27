package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.ViolationsMap;

public class PopUpDownloadActivity extends AppCompatActivity {
    private RestaurantManager restaurantManager;
    private final String filenameForRestaurant = "update_restaurant";
    private final String filenameForInspection = "update_inspection";
    private final String TAG = "Debug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_download);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

//        FileInputStream internalRestaurants = null;
//        FileInputStream internalInspections = null;
//        try {
//            internalRestaurants = openFileInput(filenameForRestaurant);
//            internalInspections = openFileInput(filenameForInspection);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//        InputStream restaurantsIn = internalRestaurants;
//        InputStream inspectionsIn = internalInspections;
//        InputStream violationsIn = getResources().openRawResource(R.raw.all_violations);
//
//        ViolationsMap.init(violationsIn);
//        RestaurantManager.init(restaurantsIn, inspectionsIn);
//        restaurantManager = RestaurantManager.getInstance();
//
//        int size = restaurantManager.getSize();
//        Log.d(TAG, "There are " + size + " restaurants.");

        cancelBtnPressed();
    }

    private void cancelBtnPressed() {
        Button btn = findViewById(R.id.btn_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //TO DO: Cancel download
        moveTaskToBack(true);
    }

}
