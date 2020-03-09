package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Adapters.ViolationAdapter;
import ca.cmpt276.restaurantinspection.Model.TestViolation;

/** TO-DO CHANGE NAME FROM tester
 *  Re-point TestViolation Data to ACTUAL Violation data **/
public class ViolationActivity extends AppCompatActivity implements ViolationAdapter.OnViolationListener {
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    ArrayList<TestViolation> tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation);

        /** == TEST == **/
        tester = new ArrayList<>();
        tester.add(new TestViolation("Food", "Not Critical", "Poor food contamination protection", "May 5th, 2018"));
        tester.add(new TestViolation("Operations", "Not Critical", "Operation does not follow regulations", "March 7th, 2018"));
        tester.add(new TestViolation("Employee", "Critical", "Inadquate employee personal hygiene", "February 22nd, 2018"));
        tester.add(new TestViolation("Pests", "Not Critical", "Allowed breeding of pests", "February 17th, 2018"));
        tester.add(new TestViolation("Equipment", "Not Critical", "Lack of accurate thermometers", "January 21st, 2018"));
        tester.add(new TestViolation("Food", "Critical", "Hazardous storage of cold food", "January 3rd, 2018"));

        myRecyclerView = findViewById(R.id.rv3);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myAdapter = new ViolationAdapter(tester, this);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
        /** == END TEST == **/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Current Restaurant Name");
        actionBar.setElevation(0);
    }

    /** == TEST == **/
    @Override
    public void onViolationClick(int position) {
        Toast toast = Toast.makeText(this, "YOU CLICKED", Toast.LENGTH_SHORT);
        toast.show();
    }
    /** == END TEST == **/

}
