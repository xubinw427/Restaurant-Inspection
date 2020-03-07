package ca.cmpt276.restaurantinspection.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Inspection {
    private String trackingNumber;
    private String inspDate;
    private int daysAgo;
    private String monthDate;
    private String monthYear;
    private String inspType;
    private int numCritical;
    private int numNonCritical;
    private String hazardRating;
    private String violLump;

    /** String[] = [0: TrackingNum, 1: Date, 2: InspType, 3: NumCrit, 4: NumNonCrit, 5: HazRating, 6: ViolLump[]] **/
    public Inspection(String[] inspectionDetails) {
        trackingNumber = inspectionDetails[0];
        inspDate = inspectionDetails[1];
        inspType = inspectionDetails[2];

        try {
            numCritical = Integer.parseInt(inspectionDetails[3].trim());
            numNonCritical = Integer.parseInt(inspectionDetails[4].trim());
        }
        catch (NumberFormatException ex) {
            throw new RuntimeException("ERROR: Failed number conversion.");
        }

        hazardRating = inspectionDetails[5];

        /** Deal with Violation Lump here **/

    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public int getDaysAgo() {
        return daysAgo;
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

    public String getViolLump() {
        return violLump;
    }

    private void getDateInformation() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);

        String today = sdf.format(cal.getTime());

        try {
            Date startDate = sdf.parse(inspDate);
            Date endDate = sdf.parse(today);
            long difference = endDate.getTime() - startDate.getTime();
            int daysBetween = (int) (difference / (1000*60*60*24));

            daysAgo = daysBetween;
        }
        catch (java.text.ParseException ex) {
            throw new RuntimeException("ERROR: Failed to parse dates");
        }

        SimpleDateFormat getDay = new SimpleDateFormat("dd", Locale.CANADA);
        SimpleDateFormat getMonth = new SimpleDateFormat("MMMM", Locale.CANADA);
        SimpleDateFormat getYear = new SimpleDateFormat("yyyy", Locale.CANADA);

        cal.add(Calendar.DATE, -daysAgo);

        monthDate = getMonth.format(cal.getTime()) + " " + getDay.format(cal.getTime());
        monthYear = getMonth.format(cal.getTime()) + " " + getYear.format(cal.getTime());
    }
}
