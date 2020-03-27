package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.cmpt276.restaurantinspection.Model.DataManager;
import ca.cmpt276.restaurantinspection.Model.UpdateManager;

import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.ViolationsMap;

public class PopUpDownloadActivity extends AppCompatActivity {
    UpdateManager updateManager = UpdateManager.getInstance();
    DataManager dataManager = DataManager.getInstance();
    Thread download;

    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private final String filenameForRestaurant = "update_restaurant";
    private final String filenameForInspection = "update_inspection";
    private final String TAG = "Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_download);

        setFinishOnTouchOutside(false);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        SharedPreferences pref = this.getSharedPreferences("UpdatePref", 0);
        final SharedPreferences.Editor EDITOR = pref.edit();
        restaurantManager.reset();

        FileInputStream internalRestaurants = null;
        FileInputStream internalInspections = null;
        try {
            internalRestaurants = openFileInput(filenameForRestaurant);
            internalInspections = openFileInput(filenameForInspection);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStream violationsIn = getResources().openRawResource(R.raw.all_violations);

        ViolationsMap.init(violationsIn);
        RestaurantManager.init(internalRestaurants, internalInspections);

        restaurantManager = RestaurantManager.getInstance();

        int size = restaurantManager.getSize();
        Log.d(TAG, "There are " + size + " restaurants.");

        cancelBtnPressed();

        download = new Thread(new Runnable() {
            @Override
            public void run() {
                dataManager.readSecondURLForRestaurantData();
                dataManager.readSecondURLForInspectionData();

                /** Will exit sleep once update is complete (set to 1) **/
                try {
                    while (updateManager.getUpdated() != 1) {
                        Thread.sleep(10);
                    }
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                if (updateManager.getCancelled() != 1) {
                    /** SAVE: Last Modified Restaurant/Inspection Time (by server) **/
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
                    Calendar cal = Calendar.getInstance();

                    EDITOR.putString("last_modified_restaurants_by_server",
                            updateManager.getLastModifiedRestaurants());
                    EDITOR.putString("last_modified_inspections_by_server",
                            updateManager.getLastModifiedInspections());
                    EDITOR.apply();
                }

                finish();
            }
        });

        download.start();
    }

    private void cancelBtnPressed() {
        Button btn = findViewById(R.id.btn_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download.interrupt();
                updateManager.setCancelled(1);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        download.interrupt();
        updateManager.setCancelled(1);
        finish();
    }
}
