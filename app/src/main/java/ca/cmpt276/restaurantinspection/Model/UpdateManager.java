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
    private String lastUpdatedDate;
    private String lastModifiedInspections;

    private UpdateManager(Context context) {
        this.context = context;
        this.lastUpdatedDate = null;
        this.lastModifiedInspections = null;

        SharedPreferences pref = context.getSharedPreferences("UpdatePref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("last_updated", null);
        editor.putString("last_modified_by_server", null);
        editor.commit();
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

    public String getLastModifiedInspections() {
        return lastModifiedInspections;
    }

    public void setLastModifiedInspectionsPrefs(String lastModifiedDate) {
        this.lastModifiedInspections = lastModifiedDate;

        SharedPreferences pref = context.getSharedPreferences("UpdatePref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("last_modified_by_server", lastModifiedDate);
        editor.apply();
    }

    public void setLastModifiedInspections(String lastModifiedDate) {
        this.lastModifiedInspections = lastModifiedDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDatePrefs(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;

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

            System.out.println(hoursApart);

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
            while (pref.getString("last_modified_by_server", null) == null) {
                Thread.sleep(10);
            }
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        String savedDate = pref.getString("last_modified_by_server", null);

        if (!this.lastModifiedInspections.equals(savedDate)) {
            return true;
        }

        return false;
    }
}
