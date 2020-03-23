package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class PopUpUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_update);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        Button btn_proceed = findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = makePopUpDownloadIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });

        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //TODO: Cancel download
        moveTaskToBack(true);
    }

    public static Intent makePopUpDownloadIntent(Context c){
        return new Intent(c, PopUpDownloadActivity.class);
    }

    public static Intent makeRestaurantMapIntent(Context c){
        return new Intent(c, RestaurantMapActivity.class);
    }
}
