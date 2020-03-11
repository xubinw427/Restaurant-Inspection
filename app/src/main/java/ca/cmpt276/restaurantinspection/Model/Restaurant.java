package ca.cmpt276.restaurantinspection.Model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Restaurant implements Comparable<Restaurant>{
    private String id;
    private String name;
    private String address;
    private String coordinates;
    private double latitude;
    private double longitude;
    private ArrayList<Inspection> inspections;

    public Restaurant(String restaurantLump) {
        String[] restaurantInfo = restaurantLump.split(",");
        /** [0: ID, 1: Name, 2: PhysAddress, 3: PhysCity, 4: Factype, 5: Latitude, 6: Longitude] **/
        id = restaurantInfo[0];
        name = restaurantInfo[1];
        address = restaurantInfo[2] + ", " + restaurantInfo[3];

        coordinates = restaurantInfo[5] + ", " +
                    restaurantInfo[6];
        latitude = Double.parseDouble(restaurantInfo[5]);
        longitude = Double.parseDouble(restaurantInfo[6]);

        inspections = new ArrayList<>();
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

    public String getCoordinates() {
            return coordinates;
        }

    public double getLatitude() {
            return latitude;
        }

    public double getLongitude() {
            return longitude;
        }

    public ArrayList<Inspection> getInspections() {
        return inspections;
    }

    public String getHazard()
    {
        if(this.inspections.size() > 0)
            return inspections.get(0).getHazardRating();
        return "Low";
    }

    public String getDate()
    {
        if(this.inspections.size() > 0)
            return inspections.get(0).getDateDisplay();
        return "No Recent Inspection.";
    }

    public String getNumIssues()
    {
        if(this.inspections.size() > 0) {

            int num = inspections.get(0).getNumCritical() + inspections.get(0).getNumNonCritical();
            String numInString = Integer.toString(num);
            return numInString;
        }
        return "0";
    }
    public void addInspection(Inspection inspection) {
            inspections.add(inspection);
        }

    @Override
    @NonNull
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public int compareTo(Restaurant r)
    {
        int l1 = this.name.length();
        int l2 = r.getName().length();
        int min = Math.min(l1, l2);

        for(int i = 0; i < min; i++)
        {
            int s1Char = (int)this.name.charAt(i);
            int s2Char = (int)r.getName().charAt(i);

            if(s1Char != s2Char)
                return s1Char - s2Char;
        }

        if(l1 != l2)
            return l1 - l2;
        else
            return 0;
    }

}


