package ca.cmpt276.restaurantinspection;

import androidx.annotation.NonNull;

import android.location.Location;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import java.io.InputStream;

import ca.cmpt276.restaurantinspection.Adapters.RestaurantInfoWindowAdapter;
import ca.cmpt276.restaurantinspection.Model.CustomMarker;
import ca.cmpt276.restaurantinspection.Model.OwnIconRendered;
import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.Model.ViolationsMap;

public class RestaurantMapActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private RestaurantManager restaurantManager;
    ClusterManager <CustomMarker> mClusterManager;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_map);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurants Map");
        actionBar.setElevation(0);

        InputStream restaurantsIn = getResources().openRawResource(R.raw.restaurants_itr1);
        InputStream inspectionsIn = getResources().openRawResource(R.raw.inspectionreports_itr1);
        InputStream violationsIn = getResources().openRawResource(R.raw.all_violations);

        ViolationsMap.init(violationsIn);
        RestaurantManager.init(restaurantsIn, inspectionsIn);
        restaurantManager = RestaurantManager.getInstance();

        initMap();
        startRestaurantListActivity();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        getDeviceLocation();
        //add markers
        setUpCluster();
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task location = mFusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Location currentLocation = (Location) task.getResult();
                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),DEFAULT_ZOOM);
                }
                else {
                    Toast.makeText(RestaurantMapActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setUpCluster() {
        //Initialize the manager with context and the map
        mClusterManager = new ClusterManager<>(this,mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        addMapItems();
        mClusterManager.setRenderer(new OwnIconRendered(RestaurantMapActivity.this, mMap, mClusterManager));
        
        mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<CustomMarker>() {
            @Override
            public void onClusterItemInfoWindowClick(CustomMarker marker) {
                int position = getRestaurantPosition(marker.getPosition());
                restaurantManager.setCurrRestaurantPosition(position);
                Intent intent = new Intent(RestaurantMapActivity.this, RestaurantInfoActivity.class);
                startActivity(intent);

            }
        });

    }

    private void addMapItems() {
        mClusterManager.getMarkerCollection().setInfoWindowAdapter(new RestaurantInfoWindowAdapter(RestaurantMapActivity.this));
        mClusterManager.getClusterMarkerCollection().setInfoWindowAdapter(new RestaurantInfoWindowAdapter(RestaurantMapActivity.this));
        for (Restaurant restaurant : restaurantManager){
            if (restaurant!=null) {
                try {
                    double lat = restaurant.getLatitude();
                    double lng = restaurant.getLongitude();
                    String title = restaurant.getName();
                    String hazard = restaurant.getHazard();
                    String snippet = "\n" + "Address: " + restaurant.getAddress() +"\n\n" +
                            "Hazard Level: " + hazard + "\n";

                    CustomMarker location = new CustomMarker(lat, lng, title, snippet, hazard);
                    mClusterManager.addItem(location);
                }
                catch (NullPointerException e) {
                    Log.e("","markRestaurantLocations: NullPointerException: "+e.getMessage());
                }
            }
        }
    }

    private void startRestaurantListActivity() {
        Button btn = findViewById(R.id.restaurants_button_inact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = makeRestaurantListIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent makeRestaurantListIntent(Context c){
        return new Intent(c, RestaurantActivity.class);
    }

    private int getRestaurantPosition(LatLng position){
        double lat = position.latitude;
        double lng = position.longitude;
        for (int i =0; i<restaurantManager.getList().size(); i++){
            Restaurant restaurant = restaurantManager.getList().get(i);
            if (restaurant.getLatitude() == lat && restaurant.getLongitude() == lng){
                return i;
            }

        }
        return -1;
    }


}


