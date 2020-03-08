package ca.cmpt276.restaurantinspection.Model;

public class Restaurant {
    private String trackingNumber;
    private String physicalcity;
    private String facType;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private InspectionList inspectionList;
    public Restaurant()
    {
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getPhysicalcity() {
        return physicalcity;
    }

    public void setPhysicalcity(String physicalcity) {
        this.physicalcity = physicalcity;
    }

    public String getFacType() {
        return facType;
    }

    public void setFacType(String facType) {
        this.facType = facType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "trackingNumber ='" + trackingNumber + '\'' +
                ", physicalcity ='" + physicalcity + '\'' +
                ", facType ='" + facType + '\'' +
                ", name ='" + name + '\'' +
                ", address ='" + address + '\'' +
                ", latitude =" + latitude +
                ", longitude =" + longitude +
                '}';
    }
}
