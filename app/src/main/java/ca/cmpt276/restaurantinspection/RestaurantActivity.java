package ca.cmpt276.restaurantinspection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.ViolationsMap;

import ca.cmpt276.restaurantinspection.Adapters.RestaurantAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestaurantActivity extends AppCompatActivity implements RestaurantAdapter.OnRestaurantListener {
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private static final String filename = "Update Restaurants";
    // For Debugging
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setElevation(0);

        restaurantManager = RestaurantManager.getInstance();

        extractRestaurants();

        mapView();


        // Following is to read Restaurant Data
        readTheFirstURL();

        // Following is to read Inspection Data
        readTheSecondURL();



    }



    private void readTheFirstURL() {
        // Create okHttp to make get request
        OkHttpClient client = new OkHttpClient();

        // use the URL given by BF, and add an "s".
        String url = "https://data.surrey.ca/api/3/action/package_show?id=restaurants";

        // To hold request
        final Request request = new Request.Builder().url(url).build();

        // Make get request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " +  response);
                }
                else
                {
                    // To store the response String.
                    final String myResponse = response.body().string();
                    try {

                        // Read the whole file as a JsonObject
                        JSONObject jsonObject = new JSONObject(myResponse);

                        // Read the result part as a JsonObject
                        JSONObject result = jsonObject.getJSONObject("result");

                        // Read resources as JsonArray
                        JSONArray jsonArray = (JSONArray) result.get("resources");

                        //Get The First resource in csv type.
                        JSONObject csv = (JSONObject) jsonArray.get(0);

                        // Get the real url to read actual data.
                        String url2 = csv.get("url").toString();
                        String updateURL2 = url2.replace("http", "https");

                        String lastModified = csv.get("last_modified").toString();
                        Log.d(TAG, updateURL2);
                        Log.d(TAG, lastModified);

                        // To read real data
                        readSecondURLForRestaurantData(updateURL2,lastModified);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void readSecondURLForRestaurantData(String url2, String lastModified) {

                // All the same as the first URL.
                final Request requestForRestaurantData = new Request.Builder().url(url2).build();

                OkHttpClient client2 = new OkHttpClient();

                client2.newCall(requestForRestaurantData).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(!response.isSuccessful())
                        {
                            throw new IOException("Unexpected code " +  response);
                        }
                        else {
                            final String secondResponse = response.body().string();

                            Scanner scanner = new Scanner(secondResponse);

                            // Make sure we get the right number of restaurants.
                            int count = 0;
                            while(scanner.hasNextLine())
                            {
                                String line = scanner.nextLine();
                                count++;
                            }
                            Log.d(TAG, "There are " + count + " Restaurants.");
                            scanner.close();

                        }

                    }
                });
            }
        });

    }

    // Same as Restaurants.
    private void readTheSecondURL() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports";

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " +  response);
                }
                else
                {
                    final String myResponse = response.body().string();
                    try {


                        JSONObject jsonObject = new JSONObject(myResponse);
                        JSONObject result = jsonObject.getJSONObject("result");
                        JSONArray jsonArray = (JSONArray) result.get("resources");
                        JSONObject csv = (JSONObject) jsonArray.get(0);
                        String url2 = csv.get("url").toString();
                        String updateURL2 = url2.replace("http", "https");
                        String lastModified = csv.get("last_modified").toString();
                        Log.d(TAG, updateURL2);
                        Log.d(TAG, lastModified);

                        readSecondURLForRestaurantData(updateURL2,lastModified);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void readSecondURLForRestaurantData(String url2, String lastModified) {

                final Request requestForInspectionData = new Request.Builder().url(url2).build();

                OkHttpClient client2 = new OkHttpClient();

                client2.newCall(requestForInspectionData).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(!response.isSuccessful())
                        {
                            throw new IOException("Unexpected code " +  response);
                        }
                        else {
                            final String secondResponse = response.body().string();

                            Scanner scanner = new Scanner(secondResponse);

                            int count = 0;
                            while(scanner.hasNextLine())
                            {
                                String line = scanner.nextLine();
                                count++;
                            }
                            Log.d(TAG, "There are " + count + " Inspections.");
                            scanner.close();

                        }

                    }
                });
            }
        });
    }

    private void mapView() {
        Button btn = findViewById(R.id.map_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = makeRestaurantMapIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
    }

    public void extractRestaurants() {
        RecyclerView restaurantRecyclerView = findViewById(R.id.rv);
        restaurantRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager restaurantLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter restaurantAdapter = new RestaurantAdapter
                (restaurantManager, this);

        restaurantRecyclerView.setLayoutManager(restaurantLayoutManager);
        restaurantRecyclerView.setAdapter(restaurantAdapter);
    }

    @Override
    public void onRestaurantClick(int position) {
        restaurantManager.setCurrRestaurantPosition(position);

        Intent intent = new Intent(this, RestaurantInfoActivity.class);
        startActivity(intent);
    }

    public static Intent makeRestaurantMapIntent(Context c){
        return new Intent(c, RestaurantMapActivity.class);
    }
}
