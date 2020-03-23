package ca.cmpt276.restaurantinspection.Model;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class OwnIconRendered extends DefaultClusterRenderer<CustomMarker> {
    private RestaurantManager manager = RestaurantManager.getInstance();
    private int position = manager.getCurrRestaurantPosition();
    private Restaurant restaurant = manager.getRestaurantAt(position);
    private LatLng pos = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());

    public OwnIconRendered(Context context, GoogleMap map,
                           ClusterManager<CustomMarker> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(CustomMarker item, MarkerOptions markerOptions) {
        markerOptions.icon(item.getIcon());
        markerOptions.snippet(item.getSnippet());
        markerOptions.title(item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    @Override
    protected  void onClusterItemRendered (CustomMarker item, Marker marker){
        super.onClusterItemRendered(item, marker);
        if (item.getPosition().equals(pos)) {
            getMarker(item).showInfoWindow();
        }
    }
}
