package ca.cmpt276.restaurantinspection;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;

import ca.cmpt276.restaurantinspection.Model.Violation;
import ca.cmpt276.restaurantinspection.Model.ViolationsMap;

public class MainActivity extends AppCompatActivity {
    private ViolationsMap violationsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /** ================== **/
        InputStream input = getResources().openRawResource(R.raw.all_violations);
        violationsMap = ViolationsMap.getInstance(input);

        /** Testing Violation Class with data from ViolationsMap. TO DELETE **/
//        String[] tmp = violationsMap.getViolationFromMap("404");
//        Violation test = new Violation(tmp);
//        String TAG = "MyActivity";
//        Log.v(TAG, test.getID());
//        Log.v(TAG, test.getType());
//        Log.v(TAG, test.getSeverity());
//        Log.v(TAG, test.getLongDescription());
//        Log.v(TAG, test.getShortDescription());

        /** Testing data is populated into ViolationsMap correctly. TO DELETE. **/
//        String[] TEST = violationsMap.getViolationFromMap("404");
//
//        Toast toast = Toast.makeText(this, TEST[4], Toast.LENGTH_SHORT);
//        toast.show();
        /** ================== **/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
