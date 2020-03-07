package ca.cmpt276.restaurantinspection.Model;

import java.util.Dictionary;
import java.util.Hashtable;

public class Violation {
    public class ViolationElements {
        private String severity;
        private String description;
        private String repeat;

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRepeat() {
            return repeat;
        }

        public void setRepeat(String repeat) {
            this.repeat = repeat;
        }
    }

    Dictionary<Integer, ViolationElements> list = new Hashtable();

    public Dictionary<Integer, ViolationElements> getList() {
        return list;
    }
}
