package com.hiwijaya.crud.util;

/**
 * @author Happy Indra Wijaya
 */
public enum RentStatus {

    RETURNED(0, "Returned"),
    RENT(1, "Rent"),
    OUTDATED(2, "Outdated");

    private final int status;
    private final String desc;

    RentStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static RentStatus getStatus(int status){
        if(status == 0){
            return RETURNED;
        }
        else if(status == 1){
            return RENT;
        }
        else if(status == 2){
            return OUTDATED;
        }
        return null;
    }

}
