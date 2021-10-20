package com.java_polytech.piepline_interfaces;

public class RC {
    //When finished correctly - returns null as RC

    public enum RCType {
        INPUT_DATA_ERROR,
        NULL_REFERENCE_ERROR,
        //TODO: Add more types
    }

    public final RCType type;
    public final String info;

    public RC(RCType type, String info)
    {
        this.type = type;
        this.info = info;
    }
    public static final RC RC_NULL_REF = new RC(RCType.NULL_REFERENCE_ERROR, "Null reference error.");

}
