package ca.cmpt276.restaurantinspection.Model;

import android.util.Log;

/** Violation Object that is Linked to Inspection Object **/
public class Violation {
    private String ID;
    private String type;
    private String severity;
    private String longDescription;
    private String shortDescription;
    private final String TAG = "Debug";

    /** String[]: [0: ID, 1: Type, 2: Severity, 3: LongDescription, 4: ShortDescription]**/
    public Violation(String[] violationDetails) {
        ID = violationDetails[0];
        //Log.d(TAG, "ID: " + violationDetails[0]);
        type = violationDetails[1];
        //Log.d(TAG, "Type: " + violationDetails[1]);
        severity = violationDetails[2];
        //Log.d(TAG, "Severity: " + violationDetails[2]);
        longDescription = violationDetails[3];
        //Log.d(TAG, "Long: " + violationDetails[3]);
        shortDescription = violationDetails[4];
        //Log.d(TAG, "Short: " + violationDetails[4]);
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