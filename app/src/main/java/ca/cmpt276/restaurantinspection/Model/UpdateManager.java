package ca.cmpt276.restaurantinspection.Model;

public class UpdateManager {
    private static UpdateManager instance;
    private String lastUpdatedDate;
    private String lastModifiedInspections;

    private UpdateManager() {
        lastModifiedInspections = null;
    }

    public static UpdateManager getInstance() {
        if (instance == null) {
            instance = new UpdateManager();
        }

        return instance;
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
