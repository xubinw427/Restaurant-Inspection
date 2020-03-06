package ca.cmpt276.restaurantinspection.Model;

import java.util.Dictionary;
import java.util.Hashtable;

public class Violation {
    public class ViolationElements{
        String severity;
        String description;
        String repeat;
    }
    Dictionary<Integer, ViolationElements> list = new Hashtable();
}
