package ca.cmpt276.restaurantinspection.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Inspection implements Comparable<Inspection>{
    private String trackingNumber;

    private String inspDate;
    private String dateDisplay;
    private String fullDate;

    private String inspType;
    private int numCritical;
    private int numNonCritical;
    private String hazardRating;

    private ArrayList<Violation> violationsList;

    /** inspectionLump is either
     * [0: Inspection (as is, there were no violations)] >> length = 1
     * OR
     * [0: First half of Inspection, 1: Violations & Hazard Rating] >> length = 2 **/
    /** ViolLump is currently a long string of all violations to be split by '|' **/
    public Inspection(String[] inspectionLump, ViolationsMap map) {
        violationsList = new ArrayList<>();

        String[] inspectionDetails = inspectionLump[0].split(",");
        /** [0: ID, 1: Date, 2: InspType 3: NumCrit, 4: NumNonCrit, 5: NULL, 6: HazardLevel] if len 1
         * OR
         * [0: ID, 1: Date, 2: InspType 3: NumCrit, 4: NumNonCrit] if len 2 **/

        trackingNumber = inspectionDetails[0];
        inspDate = inspectionDetails[1];
        getDateInformation();

        inspType = inspectionDetails[2];

        try {
            numCritical = Integer.parseInt(inspectionDetails[3].trim());
            numNonCritical = Integer.parseInt(inspectionDetails[4].trim());
        }
        catch (NumberFormatException ex) {
            throw new RuntimeException("ERROR: Failed number conversion.");
        }

        if (inspectionLump.length == 1) {
            hazardRating = inspectionDetails[6];
        }

        else {
            String[] separateViolationsAndHazard = inspectionLump[1].split("\",");
            /** [0: Violations, 1: 1: HazardLevel] **/

            hazardRating = separateViolationsAndHazard[1];

            String violLump = separateViolationsAndHazard[0];
            String[] violations = violLump.split("\\|");

            for (String violation : violations) {
                String[] currViolation = violation.split(",");

                String violationID = currViolation[0];
                String[] violationInfo = ViolationsMap.getViolationFromMap(violationID);

                /** ID not found **/
                if (violationInfo == null) {
                    return;
                }

                /** Create new data from info retrieved **/
                Violation newViolation = new Violation(violationInfo);

                violationsList.add(newViolation);
            }
        }
    }
    public String getInspDate() {
        return inspDate;
    }

    public String getDateDisplay() {
        return dateDisplay;
    }

    public String getFullDate() {
        return fullDate;
    }

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

    public ArrayList<Violation> getViolationsList() {
        return violationsList;
    }

    private void getDateInformation() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CANADA);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        int daysAgo;

        String today = sdf.format(cal.getTime());

        /** Find number of days between today and inspection date **/
        try {
            Date startDate = sdf.parse(inspDate);
            Date endDate = sdf.parse(today);
            float difference = endDate.getTime()/(1000*60*60*24) - startDate.getTime()/(1000*60*60*24);
            daysAgo = (int) difference;
        }
        catch (java.text.ParseException ex) {
            throw new RuntimeException("ERROR: Failed to parse dates");
        }

        SimpleDateFormat getDay = new SimpleDateFormat("dd", Locale.CANADA);
        SimpleDateFormat getMonth = new SimpleDateFormat("MMMM", Locale.CANADA);
        SimpleDateFormat getYear = new SimpleDateFormat("yyyy", Locale.CANADA);

        cal.add(Calendar.DATE, - daysAgo);

        if (daysAgo < 31) {
            dateDisplay = daysAgo + "days ago";
        }
        else if (daysAgo < 366) {
            dateDisplay = getMonth.format(cal.getTime()) + " " + getDay.format(cal.getTime());
        }
        else {
            dateDisplay = getMonth.format(cal.getTime()) + " " + getYear.format(cal.getTime());
        }

        fullDate = getMonth.format(cal.getTime()) + " " + getDay.format(cal.getTime()) +
                ", " + getYear.format(cal.getTime());
    }


    @Override
    public int compareTo(Inspection inspection) {
        int thisDate = Integer.parseInt(this.inspDate);
        int comparedDate = Integer.parseInt(inspection.getInspDate());

        if(thisDate >= comparedDate)
            return comparedDate - thisDate;
        else
            return thisDate - comparedDate;
    }
}
