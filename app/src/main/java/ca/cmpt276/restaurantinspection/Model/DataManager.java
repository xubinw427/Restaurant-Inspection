package ca.cmpt276.restaurantinspection.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class DataManager {
    private static DataManager instance;
    private Context fileContext;
    UpdateManager updateManager = UpdateManager.getInstance();

    private String currRestaurantData = "current_update_restaurants";
    private String currInspectionData = "current_update_inspection";

    private String updateURLRestaurant;
    private String updateURLInspection;

    private DataManager(Context context) {
        fileContext = context;
        readRestaurantURL();
        readInspectionsURL();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            throw new AssertionError(
                    "DataManager.init(InputStream file) must be called first.");
        }

        return instance;
    }

    public static DataManager init(Context context) {
        if (instance != null) {
            return null;
        }

        instance = new DataManager(context);
        return instance;
    }

    private void setUpdateURLRestaurant (String url) {
        updateURLRestaurant = url;
    }

    private void setUpdateURLInspection (String url) {
        updateURLInspection = url;
    }

    private void readRestaurantURL() {
        // Create okHttp to make get request
        OkHttpClient client = new OkHttpClient();

        // use the URL given by BF, and add an "s".
        String url = "https://data.surrey.ca/api/3/action/package_show?id=restaurants";

        // To hold request
        final Request request = new Request.Builder().url(url).build();

        // Make get request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(!response.isSuccessful()) {
                    throw new IOException("Unexpected code " +  response);
                }
                else {
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

                        /** On first load, save modified dates as last updated & last modified **/
                        if (updateManager.getLastUpdatedDate() == null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
                            Calendar cal = Calendar.getInstance();
                            String today = sdf.format(cal.getTime());

                            updateManager.setLastUpdatedDatePrefs(today);
                        }
                        if (updateManager.getLastModifiedRestaurants() == null) {
                            updateManager.setLastModifiedRestaurantsPrefs(lastModified);
                        }

                        else { updateManager.setLastModifiedRestaurants(lastModified); }
                        /** === END UPDATE MANAGER === **/

                        Log.d(TAG, updateURL2);
                        Log.d(TAG, lastModified);

                        setUpdateURLRestaurant(updateURL2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Same as Restaurants.
    private void readInspectionsURL() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports";

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(!response.isSuccessful()) {
                    throw new IOException("Unexpected code " +  response);
                }
                else {
                    final String myResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(myResponse);
                        JSONObject result = jsonObject.getJSONObject("result");
                        JSONArray jsonArray = (JSONArray) result.get("resources");
                        JSONObject csv = (JSONObject) jsonArray.get(0);
                        String url2 = csv.get("url").toString();
                        String updateURL2 = url2.replace("http", "https");
                        String lastModified = csv.get("last_modified").toString();

                        /** UPDATE MANAGER OPERATIONS **/
                        if (updateManager.getLastModifiedInspections() == null) {
                            updateManager.setLastModifiedInspectionsPrefs(lastModified);
                        }
                        else { updateManager.setLastModifiedInspections(lastModified); }
                        /** === END UPDATE MANAGER === **/

                        Log.d(TAG, updateURL2);
                        Log.d(TAG, lastModified);

                        setUpdateURLInspection(updateURL2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void readSecondURLForRestaurantData() {
        final Request requestForRestaurantData = new Request.Builder().url(updateURLRestaurant).build();

        OkHttpClient client2 = new OkHttpClient();

        client2.newCall(requestForRestaurantData).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(!response.isSuccessful()) {
                    throw new IOException("Unexpected code " +  response);
                }
                else {
                    final String secondResponse = response.body().string();

                    Scanner scanner = new Scanner(secondResponse);

                    String filename = "new_update_restaurant";

                    FileOutputStream outputStream;
                    outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);

                    while(scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        line = line + '\r';

                        try {
                            outputStream.write(line.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    outputStream.close();
                    scanner.close();
                }
            }
        });
    }

    public void readSecondURLForInspectionData() {
        final Request requestForRestaurantData = new Request.Builder().url(updateURLInspection).build();

        OkHttpClient client2 = new OkHttpClient();

        client2.newCall(requestForRestaurantData).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(!response.isSuccessful()) {
                    throw new IOException("Unexpected code " +  response);
                }
                else {
                    final String secondResponse = response.body().string();

                    Scanner scanner = new Scanner(secondResponse);

                    String filename = "new_update_inspection";

                    FileOutputStream outputStream;
                    outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);

                    while(scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        line = line + '\r';

                        try {
                            outputStream.write(line.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    outputStream.close();
                    scanner.close();
                }
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        Calendar cal = Calendar.getInstance();

        String today = sdf.format(cal.getTime());

        SharedPreferences pref = fileContext.getSharedPreferences("UpdatePref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("last_updated", today);
        editor.putString("last_modified_restaurants_by_server",
                updateManager.getLastModifiedRestaurants());
        editor.putString("last_modified_inspections_by_server",
                updateManager.getLastModifiedInspections());
        editor.apply();

        fileContext.deleteFile("update_restaurant");
        File oldRestaurantFile = fileContext.getFileStreamPath("new_update_restaurant");
        File newRestaurantFile = fileContext.getFileStreamPath("update_restaurant");
        oldRestaurantFile.renameTo(newRestaurantFile);

        fileContext.deleteFile("update_inspection");
        File oldInspectionFile = fileContext.getFileStreamPath("new_update_inspection");
        File newInspectionFile = fileContext.getFileStreamPath("update_inspection");
        oldInspectionFile.renameTo(newInspectionFile);

        updateManager.setUpdated(1);
    }
}
