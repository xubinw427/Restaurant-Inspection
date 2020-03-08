package ca.cmpt276.restaurantinspection.Model;

import java.util.ArrayList;

public class Restaurant {
    private String ID;
    private String name;
    private String address;
    private String coordinates;
    private double latitude;
    private double longitude;
    private ArrayList<Inspection> inspections;

    public Restaurant(String restaurantLump) {
        String[] restaurantInfo = restaurantLump.split(",");
        /** [0: ID, 1: Name, 2: PhysAddress, 3: PhysCity, 4: Factype, 5: Latitude, 6: Longitude] **/
        ID = restaurantInfo[0].replaceAll("\"", "");
        name = restaurantInfo[1].replaceAll("\"", "");
        address = restaurantInfo[2].replaceAll("\"", "") + ", " +
                  restaurantInfo[3].replaceAll("\"", "");

        coordinates = restaurantInfo[5].replaceAll("\"", "") + ", " +
                restaurantInfo[6].replaceAll("\"", "");
        latitude = Double.parseDouble(restaurantInfo[5]);
        longitude = Double.parseDouble(restaurantInfo[6]);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void addInspection(Inspection inspection) {
        inspections.add(inspection);
    }
}
