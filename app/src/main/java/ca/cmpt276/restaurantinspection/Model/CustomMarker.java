package ca.cmpt276.restaurantinspection.Model;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import ca.cmpt276.restaurantinspection.R;

public class CustomMarker implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;

    private BitmapDescriptor mIcon;

    public CustomMarker(double lat, double lng){
        mPosition = new LatLng(lat, lng);
    }

    public CustomMarker(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }
    public CustomMarker(double lat, double lng, String title, String snippet, String hazard) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;

        switch(hazard){
            case "Low":
                mIcon = BitmapDescriptorFactory.fromResource(R.drawable.smile);
                break;
            case "Moderate":
                mIcon = BitmapDescriptorFactory.fromResource(R.drawable.serious);
                break;
            case "High":
                mIcon = BitmapDescriptorFactory.fromResource(R.drawable.mad);
                break;
            default:
                break;

        }
    }

    public BitmapDescriptor getIcon(){ return mIcon;}
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public void setIcon(String hazard) {
        switch(hazard){
            case "Low":
                mIcon = BitmapDescriptorFactory.fromResource(R.drawable.smile);
                break;
            case "Moderate":
                mIcon = BitmapDescriptorFactory.fromResource(R.drawable.serious);
                break;
            case "High":
                mIcon = BitmapDescriptorFactory.fromResource(R.drawable.mad);
                break;
            default:
                break;

        }

    }
}
