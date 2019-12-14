package com.monsanity.sofuserapp.utils;

import android.text.format.DateFormat;

import java.util.Calendar;

public class Utils {

    public static String getFormattedDate(Long timeStamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp * 1000L);
        return DateFormat.format(Constant.DATE_FORMAT, cal).toString();
    }

}
