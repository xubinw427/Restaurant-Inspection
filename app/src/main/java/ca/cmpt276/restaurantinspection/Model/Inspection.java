package ca.cmpt276.restaurantinspection.Model;

public class Inspection {
    private String trackingNumber;
    private String inspDate;
    private String daysAgo;
    private String monthDate;
    private String monthYear;
    private String inspType;
    private int numCritical;
    private int numNonCritical;
    private String hazardRating;
    private String violLump;

    public String getTrackingNumber() {
        return trackingNumber;
    }

//    public String getInspDate() {
//        return inspDate;
//    }

    public String getInspType() {
        return inspType;
    }

    public int getNumCritical() {
        return numCritical;
    }

    public int getNumNonCritical() {
        return numNonCritical;
    }

    public String getHazardRating() {
        return hazardRating;
    }

    public String getViolLump() {
        return violLump;
    }
}
