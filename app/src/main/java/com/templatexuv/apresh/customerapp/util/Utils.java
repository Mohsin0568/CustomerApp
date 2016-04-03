package com.templatexuv.apresh.customerapp.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Apresh on 5/5/2015.
 */
public class Utils {
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public String getDateFromMilliSeconds(String milliseconds){

        String dateString = null;
        long millisecond = Long.parseLong(milliseconds);

        Date date = new Date(millisecond);
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        dateString = formatter.format(date);

        return dateString;
    }



}
