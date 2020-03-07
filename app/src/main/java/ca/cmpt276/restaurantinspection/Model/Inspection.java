package ca.cmpt276.restaurantinspection.Model;

public class Inspection {
    private String trackingNumber;
    private int inspDate;
    private String inspType;
    private int numCritical;
    private int numNonCritical;
    private String hazardRating;
    private String violLump;

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public int getInspDate() {
        return inspDate;
    }

    public void setInspDate(int inspDate) {
        this.inspDate = inspDate;
    }

    public String getInspType() {
        return inspType;
    }

    public void setInspType(String inspType) {
        this.inspType = inspType;
    }

    public int getNumCritical() {
        return numCritical;
    }

    public void setNumCritical(int numCritical) {
        this.numCritical = numCritical;
    }

    public int getNumNonCritical() {
        return numNonCritical;
    }

    public void setNumNonCritical(int numNonCritical) {
        this.numNonCritical = numNonCritical;
    }

    public String getHazardRating() {
        return hazardRating;
    }

    public void setHazardRating(String hazardRating) {
        this.hazardRating = hazardRating;
    }

    public String getViolLump() {
        return violLump;
    }

    public void setViolLump(String violLump) {
        this.violLump = violLump;
    }

}
