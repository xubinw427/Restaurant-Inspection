package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.SearchManager;

public class SearchActivity extends AppCompatActivity {
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private SearchManager searchManager = SearchManager.getInstance();

    private final String RESTAURANT_FILENAME = "update_restaurant";
    private final String INSPECTION_FILENAME = "update_inspection";

    int greenClicked = 0;
    int yellowClicked = 0;
    int redClicked = 0;
    int favouriteClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Search");
            actionBar.setElevation(0);
        }

        final EditText searchText = findViewById(R.id.search_text);
        final EditText greaterThanNum = findViewById(R.id.greater_than_filter_num);
        final EditText lessThanNum = findViewById(R.id.less_than_filter_num);

        final Button green = findViewById(R.id.low_filter_btn);
        final Button yellow = findViewById(R.id.mod_filter_btn);
        final Button red = findViewById(R.id.high_filter_btn);
        final Button fave = findViewById(R.id.button_favourite_filter);

        Button search = findViewById(R.id.search_btn);
        Button reset = findViewById(R.id.reset_search);

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (greenClicked == 0) {
                    green.setBackgroundResource(R.drawable.circle_green);
                    yellow.setBackgroundResource(R.drawable.circle_outline_yellow);
                    red.setBackgroundResource(R.drawable.circle_outline_red);

                    greenClicked = 1;
                    yellowClicked = 0;
                    redClicked = 0;
                } else {
                    green.setBackgroundResource(R.drawable.circle_outline_green);

                    greenClicked = 0;
                }
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yellowClicked == 0) {
                    green.setBackgroundResource(R.drawable.circle_outline_green);
                    yellow.setBackgroundResource(R.drawable.circle_yellow);
                    red.setBackgroundResource(R.drawable.circle_outline_red);

                    yellowClicked = 1;
                    greenClicked = 0;
                    redClicked = 0;
                } else {
                    yellow.setBackgroundResource(R.drawable.circle_outline_yellow);

                    yellowClicked = 0;
                }
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (redClicked == 0) {
                    green.setBackgroundResource(R.drawable.circle_outline_green);
                    yellow.setBackgroundResource(R.drawable.circle_outline_yellow);
                    red.setBackgroundResource(R.drawable.circle_red);

                    redClicked = 1;
                    yellowClicked = 0;
                    greenClicked = 0;
                } else {
                    red.setBackgroundResource(R.drawable.circle_outline_red);

                    redClicked = 0;
                }
            }
        });

        fave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favouriteClicked == 0) {
                    fave.setBackgroundResource(R.drawable.button_favorite);

                    favouriteClicked = 1;
                } else {
                    fave.setBackgroundResource(R.drawable.button_not_favorite);

                    favouriteClicked = 0;
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchText.getText().toString();
                String hazardLevel;
                int lessNumCrit;
                int greatNumCrit;

                if (greenClicked == 1) {
                    hazardLevel = "Low";
                } else if (yellowClicked == 1) {
                    hazardLevel = "Moderate";
                } else if (redClicked == 1) {
                    hazardLevel = "High";
                } else {
                    hazardLevel = "";
                }

                if (!greaterThanNum.getText().toString().equals("")) {
                    greatNumCrit = Integer.parseInt(greaterThanNum.getText().toString());
                } else {
                    greatNumCrit = Integer.MAX_VALUE;
                }

                if (!lessThanNum.getText().toString().equals("")) {
                    lessNumCrit = Integer.parseInt((lessThanNum.getText().toString()));
                } else {
                    lessNumCrit = -1;
                }

                searchManager.populateSearchManager(favouriteClicked, search,
                        hazardLevel,lessNumCrit,greatNumCrit);

                finish();
            }
        });
    }

//    private Intent getParentActivityIntentImplement() {
//        Intent intent = null;
//
//        if (restaurantManager.getFromList() == 1) {
//            intent = new Intent(this, RestaurantActivity.class);
//            restaurantManager.setFromList(0);
//
//        } else if (restaurantManager.getFromMap() == 1) {
//            intent = new Intent(this, RestaurantMapActivity.class);
//            restaurantManager.setFromMap(0);
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        }
//
//        return intent;
//    }
}
