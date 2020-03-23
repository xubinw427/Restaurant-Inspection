package ca.cmpt276.restaurantinspection.Model;

import android.content.Context;

public class UpdateManager {
    private static UpdateManager instance;
    private Context context;
    private String lastUpdatedDate;
    private String lastModifiedInspections;

    private UpdateManager(Context context) {
        this.context = context;
        this.lastUpdatedDate = null;
        this.lastModifiedInspections = null;
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

    public void setLastModifiedInspections(String lastModifiedDate) {
        this.lastModifiedInspections = lastModifiedDate;

    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;

    }
}
