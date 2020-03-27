package ca.cmpt276.restaurantinspection.Model;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateManager {
    private static UpdateManager instance;
    private Context context;
    private String lastModifiedInspections;
    private String lastModifiedRestaurants;

    private int updated = -1;
    private int cancelled = 0;

    private UpdateManager(Context context) {
        this.context = context;
        this.lastModifiedInspections = null;

        SharedPreferences pref = context.getSharedPreferences("UpdatePref", 0);
        SharedPreferences.Editor editor = pref.edit();

        /** TO DELETE: TESTING TO MAKE POP-UP APPEAR IF NO UPDATE IN 20 HOURS **/
        editor.putString("last_updated", "2020-03-25 11:32:43");
        /** ================================================================== **/

        editor.apply();
    }

    public static UpdateManager getInstance() {
        if (instance == null) {
            throw new AssertionError(
                    "UpdateManager.init(InputStream file) must be called first.");
        }

        return instance;
    }

    public static UpdateManager init(Context context) {
        if (instance != null) {
            return null;
        }

        instance = new UpdateManager(context);
        return instance;
    }

    public void setUpdated(int i) {
        this.updated = i;
    }

    public int getUpdated() {
        return updated;
    }

    public void setCancelled(int i) {
        this.cancelled = i;
    }

    public int getCancelled() {
        return cancelled;
    }

    public String getLastModifiedInspections() {
        return lastModifiedInspections;
    }

    public void setLastModifiedInspectionsPrefs(String lastModifiedDate) {
        this.lastModifiedInspections = "2000-01-01 00:00:00";

        SharedPreferences pref = context.getSharedPreferences("UpdatePref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("last_modified_inspections_by_server", lastModifiedDate);
        editor.apply();
    }

    public void setLastModifiedInspections(String lastModifiedDate) {
        this.lastModifiedInspections = lastModifiedDate;
    }


    public String getLastModifiedRestaurants() {
        return lastModifiedRestaurants;
    }

    public void setLastModifiedRestaurantsPrefs(String lastModifiedDate) {
        this.lastModifiedRestaurants = "2000-01-01 00:00:00";

        SharedPreferences pref = context.getSharedPreferences("UpdatePref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("last_modified_restaurants_by_server", lastModifiedDate);
        editor.apply();
    }

    public void setLastModifiedRestaurants(String lastModifiedDate) {
        this.lastModifiedRestaurants = lastModifiedDate;
    }

    public void setLastUpdatedDatePrefs(String lastUpdatedDate) {
        SharedPreferences pref = context.getSharedPreferences("UpdatePref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("last_updated", lastUpdatedDate);
        editor.apply();
    }

    public boolean twentyHrsSinceUpdate() {
        SharedPreferences pref = context.getSharedPreferences("UpdatePref", 0);

        /** Sleep thread to allow app time to get & process server data **/
        try {
            while (pref.getString("last_updated", null) == null) {
                Thread.sleep(50);
            }
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        String rawDate = pref.getString("last_updated", null);

        String replacedDate = rawDate.replace("T", " ");
        String[] dateSplit = replacedDate.split("\\.");

        String dateCompare = dateSplit[0];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        Calendar cal = Calendar.getInstance();

        String today = sdf.format(cal.getTime());

        Date dateStart;
        Date dateEnd;
        long hoursApart;

        try {
            dateStart = sdf.parse(dateCompare);
            dateEnd = sdf.parse(today);

            long diff = dateEnd.getTime() - dateStart.getTime();
            hoursApart =  diff / (60 * 60 * 1000);

            if (hoursApart >= 20) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkUpdateNeeded() {
        /** Check if modified time from newly pulled in data equals one in Saved Prefs **/
        SharedPreferences pref = context.getSharedPreferences("UpdatePref", 0);

        try {
            while (pref.getString("last_modified_inspections_by_server", null) == null
                    || pref.getString("last_modified_restaurants_by_server", null) == null) {
                Thread.sleep(10);
            }
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        String savedRestaurantsDate = pref.getString("last_modified_restaurants_by_server",
                                                    null);
        String savedInspectionsDate = pref.getString("last_modified_inspections_by_server",
                                                    null);
        String lastUpdatedDate = pref.getString("last_updated", null);

        System.out.println(lastUpdatedDate);

        if (lastUpdatedDate == null) { return true; }

        System.out.println("IS BEFORE REST DATE");
        System.out.println(savedRestaurantsDate);
        System.out.println(isBeforeDate(lastUpdatedDate, savedRestaurantsDate));
        System.out.println("IS BEFORE INSP DATE");
        System.out.println(savedInspectionsDate);
        System.out.println(isBeforeDate(lastUpdatedDate, savedInspectionsDate));

        if (isBeforeDate(lastUpdatedDate, savedRestaurantsDate)) { return true; }

        if (isBeforeDate(lastUpdatedDate, savedInspectionsDate)) { return true; }

        return false;
    }

    private boolean isBeforeDate(String date1, String date2) {
        String replacedDate = date2.replace("T", " ");
        String[] dateSplit = replacedDate.split("\\.");

        String cleanDate2 = dateSplit[0];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);

        try {
            Date firstDate = sdf.parse(date1);
            Date secondDate = sdf.parse(cleanDate2);

            long diff = secondDate.getTime() - firstDate.getTime();
            float hoursApart =  diff / (60 * 60 * 1000);

            if (hoursApart <= 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
