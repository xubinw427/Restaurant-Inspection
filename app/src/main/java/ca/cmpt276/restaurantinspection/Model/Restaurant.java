package ca.cmpt276.restaurantinspection.Model;

import android.util.Log;

import java.util.ArrayList;

public class Restaurant {
    private String id;
    private String name = "";
    private String address;
    private double latitude;
    private double longitude;
    private ArrayList<Inspection> inspectionsList;
    private final String TAG = "Writting";

    public Restaurant(String restaurantLump) {
        restaurantLump = restaurantLump.replaceAll(", ", " ");
        restaurantLump = restaurantLump.replaceAll("\"", "");

        System.out.println(restaurantLump);
        String[] restaurantInfo = restaurantLump.split(",");
        /** [0: ID, 1: Name, 2: PhysAddress, 3: PhysCity, 4: Factype, 5: Latitude, 6: Longitude] **/
        id = restaurantInfo[0];
        name = restaurantInfo[1];
        address = restaurantInfo[2] + ", " + restaurantInfo[3];
        latitude = Double.parseDouble(restaurantInfo[5]);
        longitude = Double.parseDouble(restaurantInfo[6]);

        inspectionsList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Inspection getInspectionAt(int index) {
        return inspectionsList.get(index);
    }

    public ArrayList<Inspection> getInspectionsList() {
        return inspectionsList;
    }

    public String getHazard() {
        if (this.inspectionsList.size() > 0) {
            return inspectionsList.get(0).getHazardRating();
        }
        return "Low";
    }

    public String getDate() {
        if (this.inspectionsList.size() > 0) {
            return inspectionsList.get(0).getDateDisplay();
        }
        return "No Recent Inspection.";
    }

    public String getNumIssues() {
        if (this.inspectionsList.size() > 0) {
            int num = inspectionsList.get(0).getNumCritical() + inspectionsList.get(0).getNumNonCritical();
            return Integer.toString(num);
        }
        return "0";
    }

    public void addInspection(Inspection inspection) {
        inspectionsList.add(inspection);
    }
}