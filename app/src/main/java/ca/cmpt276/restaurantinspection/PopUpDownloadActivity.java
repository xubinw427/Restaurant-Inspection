package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ca.cmpt276.restaurantinspection.Model.DataManager;
import ca.cmpt276.restaurantinspection.Model.UpdateManager;

public class PopUpDownloadActivity extends AppCompatActivity {
    UpdateManager updateManager = UpdateManager.getInstance();
    DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_download);

        setFinishOnTouchOutside(false);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        dataManager.readSecondURLForRestaurantData();
        dataManager.readSecondURLForInspectionData();

        cancelBtnPressed();

        /** Will exit busy loop on cancel (0), or when data is done updating (1) **/
        while (updateManager.getUpdated() == -1);

        /** SAVE NEW UPDATED TIME!!!
         *  AND REST PREF
         *  AND INSP PREF **/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        Calendar cal = Calendar.getInstance();

        String today = sdf.format(cal.getTime());

        SharedPreferences pref = this.getSharedPreferences("UpdatePref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("last_updated", today);
        editor.putString("last_modified_restaurants_by_server",
                            updateManager.getLastModifiedRestaurants());
        editor.putString("last_modified_inspections_by_server",
                            updateManager.getLastModifiedInspections());
        editor.apply();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 3000);
    }

    private void cancelBtnPressed() {
        Button btn = findViewById(R.id.btn_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO DO: Cancel download
                updateManager.setUpdated(0);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //TO DO: Cancel download
        finish();
//        moveTaskToBack(true);
    }

}
