package ca.cmpt276.restaurantinspection.Model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
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

    private DataManager(Context context) {
        this.fileContext = context;
        readTheFirstURL();
        readTheSecondURL();
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

                            String filename = "update_restaurant";

                            FileOutputStream outputStream;
                            outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);

                            // Make sure we get the right number of restaurants.
                            int count = 0;
                            while(scanner.hasNextLine())
                            {
                                String line = scanner.nextLine();
                                line = line + '\r';
                                try {
                                    outputStream.write(line.getBytes());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                count++;
                            }
                            outputStream.close();
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

                        /** On first load, save modified dates as last updated & last modified **/
                        UpdateManager updateManager = UpdateManager.getInstance();
                        if (updateManager.getLastUpdatedDate() == null) {
                            System.out.println(lastModified);
                            updateManager.setLastUpdatedDatePrefs(lastModified);
                        }
                        if (updateManager.getLastModifiedInspections() == null) {
                            System.out.println(lastModified);
                            updateManager.setLastModifiedInspectionsPrefs(lastModified);
                        }

                        updateManager.setLastModifiedInspections(lastModified);
                        /** === END UPDATE MANAGER === **/

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

                            String filename = "update_inspection";

                            FileOutputStream outputStream;

                            outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);
                            // Make sure we get the right number of inspections.
                            int count = 0;
                            while(scanner.hasNextLine()) {
                                String line = scanner.nextLine();
                                line = line + '\r';
                                try {
                                    outputStream.write(line.getBytes());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                count++;
                            }
                            outputStream.close();
                            Log.d(TAG, "There are " + count + " Inspections.");
                            scanner.close();
                        }
                    }
                });
            }
        });
    }
}
