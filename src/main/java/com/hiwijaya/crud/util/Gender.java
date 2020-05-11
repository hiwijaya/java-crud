package com.hiwijaya.crud.util;

/**
 * @author Happy Indra Wijaya
 */
public enum Gender {

    MALE("M", "Male"),
    FEMALE("F", "Female");

    private final String symbol;
    private final String desc;

    Gender(String symbol, String desc) {
        this.symbol = symbol;
        this.desc = desc;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDesc() {
        return desc;
    }

}
