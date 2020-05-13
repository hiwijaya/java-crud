package com.hiwijaya.crud.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


/**
 * @author Happy Indra Wijaya
 */
public class Lib {

    public static Date now(){
        return new Date();
    }

    public static Date yesterday(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(now());
        cal.add(Calendar.DATE, -1);

        return cal.getTime();
    }

    public static Date nextWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(now());
        cal.add(Calendar.DATE, 7);

        return cal.getTime();
    }

    public static boolean isOutdated(Date dueDate){
        return dueDate.before(now());
    }

    public static Properties getPropertiesFile(String fileName){

        InputStream inputStream = Lib.class.getClassLoader().getResourceAsStream(fileName);

        if(inputStream == null){
            System.out.println(fileName + " not found.");
            return null;
        }


        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            inputStream.close();    // put inside finally block instead

            return  properties;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
