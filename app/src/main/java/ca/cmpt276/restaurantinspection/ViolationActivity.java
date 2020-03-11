package ca.cmpt276.restaurantinspection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
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

        /** ===== SET BASIC INSPECTION INFO!!! DON'T DELETE THIS, RE-POINT ==== **/
        TextView inspectionType = this.findViewById(R.id.inspection_type);
        TextView numCritIssues = this.findViewById(R.id.num_crit_issues_details);
        TextView numNonCritIssues = this.findViewById(R.id.num_noncrit_issues_details);

        /** Somehow pass by info from the inspection clicked on previous activate and set the below **/
        System.out.println("HERE");
        inspectionType.setText("SET INSP INFO");
        System.out.println("HERE2");
        numCritIssues.setText("#");
        numNonCritIssues.setText("#");
        /** ============================================== **/

        /** == TEST == **/
        tester = new ArrayList<>();
        tester.add(new TestViolation("Food", "Not Critical", "Poor food contamination protection", "00000000000000000000"));
        tester.add(new TestViolation("Operations", "Not Critical", "Operation does not follow regulations", "1111111111111111"));
        tester.add(new TestViolation("Employee", "Critical", "Inadquate employee personal hygiene", "2222222222222222222"));
        tester.add(new TestViolation("Pests", "Not Critical", "Allowed breeding of pests", "3333333333333333333"));
        tester.add(new TestViolation("Equipment", "Not Critical", "Lack of accurate thermometers", "44444444444444444444"));
        tester.add(new TestViolation("Food", "Critical", "Hazardous storage of cold food", "5555555555555555555"));

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
        String longDescription = tester.get(position).getlDesc();
        Toast toast = Toast.makeText(this, longDescription, Toast.LENGTH_SHORT);
        toast.show();
    }
    /** == END TEST == **/

}
