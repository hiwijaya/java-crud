package com.hiwijaya.crud.util;

/**
 * @author Happy Indra Wijaya
 */
public enum Gender {

    MALE("M", "Male"),
    FEMALE("F", "Female");

    private final String symbol;
    private final String desc;

    private Gender(String symbol, String desc) {
        this.symbol = symbol;
        this.desc = desc;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDesc() {
        return desc;
    }


    public static Gender getGender(String symbol){
        if("M".equals(symbol)){
            return MALE;
        }
        else if("F".equals(symbol)){
            return FEMALE;
        }
        return null;
    }

}
