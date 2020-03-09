package ca.cmpt276.restaurantinspection.Model;

public class TestViolation {
    public String type;
    public String level;
    public String sDesc;
    public String lDesc;

    public TestViolation(String vtype, String vlevel, String vSDesc, String vLongDesc) {
        type = vtype;
        level = vlevel;
        sDesc = vSDesc;
        lDesc = vLongDesc;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public String getsDesc() {
        return sDesc;
    }

    public String getlDesc() {
        return lDesc;
    }
}
