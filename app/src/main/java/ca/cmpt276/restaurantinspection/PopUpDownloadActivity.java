package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ca.cmpt276.restaurantinspection.Model.DataManager;
import ca.cmpt276.restaurantinspection.Model.UpdateManager;

public class PopUpDownloadActivity extends AppCompatActivity {
    UpdateManager updateManager = UpdateManager.getInstance();
    DataManager dataManager = DataManager.getInstance();
    Thread download;

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

        SharedPreferences pref = this.getSharedPreferences("UpdatePref", 0);
        final SharedPreferences.Editor editor = pref.edit();

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
                    /** SAVE: Updated Time, Last Modified Restaurant/Inspection Time (by server) **/
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
                    Calendar cal = Calendar.getInstance();

                    String today = sdf.format(cal.getTime());

                    editor.putString("last_modified_restaurants_by_server",
                            updateManager.getLastModifiedRestaurants());
                    editor.putString("last_modified_inspections_by_server",
                            updateManager.getLastModifiedInspections());
                    editor.apply();
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
