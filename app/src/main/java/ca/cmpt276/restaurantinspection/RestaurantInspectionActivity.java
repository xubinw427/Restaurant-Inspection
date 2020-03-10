package ca.cmpt276.restaurantinspection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Adapters.InspectionAdapter;
import ca.cmpt276.restaurantinspection.Model.TestInspection;

/** TO-DO CHANGE NAME FROM tester
 *  Re-point TestInspection Data to ACTUAL Inspection data **/
public class RestaurantInspectionActivity extends AppCompatActivity implements InspectionAdapter.OnInspectionListener {
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    ArrayList<TestInspection> tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Current Restaurant Name");
        actionBar.setElevation(0);

        /** == TEST == **/
        tester = new ArrayList<>();
        tester.add(new TestInspection("low", "3", "6", "May 5th, 2018"));
        tester.add(new TestInspection("mod", "5", "9", "March 7th, 2018"));
        tester.add(new TestInspection("low", "2", "1", "February 22nd, 2018"));
        tester.add(new TestInspection("high", "5", "9", "February 17th, 2018"));
        tester.add(new TestInspection("mod", "5", "6", "January 21st, 2018"));
        tester.add(new TestInspection("high", "7", "4", "January 3rd, 2018"));

        myRecyclerView = findViewById(R.id.rv2);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myAdapter = new InspectionAdapter(tester, this);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
        /** == END TEST == **/

        /** ================= REPLACE INTENT FUNCTION BELOW ================**/

        Button btn = findViewById(R.id.info_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestaurantInfoActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
    }

    /** == TEST == **/
    @Override
    public void onInspectionClick(int position) {
        Toast toast = Toast.makeText(this, "YOU CLICKED", Toast.LENGTH_SHORT);
        toast.show();
    }
    /** == END TEST == **/

    /** =========== INTENT LAUNCHER HERE >> findViewById(R.id.inspAct ============ **/

}