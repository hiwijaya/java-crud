package com.hiwijaya.crud.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Happy Indra Wijaya
 */
public class Lib {

    public static Date now(){
        return new Date();
    }

    public static Date nextWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(now());
        cal.add(Calendar.DATE, 7);

        return cal.getTime();
    }



}
