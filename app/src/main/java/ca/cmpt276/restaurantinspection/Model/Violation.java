package ca.cmpt276.restaurantinspection.Model;

public class Violation {
    private String ID;
    private String type;
    private String severity;
    private String longDescription;
    private String shortDescription;

    /** String[]: [0: ID, 1: Type, 2: Severity, 3: LongDescription, 4: ShortDescription]**/

    public Violation(String[] violationDetails) {
        this.ID = violationDetails[0];
        this.type = violationDetails[1];
        this.severity = violationDetails[2];
        this.longDescription = violationDetails[3];
        this.shortDescription = violationDetails[4];
    }

    public String getID() {
        return ID;
    }

    public String getType() {
        return type;
    }

    public String getSeverity() {
        return severity;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}
