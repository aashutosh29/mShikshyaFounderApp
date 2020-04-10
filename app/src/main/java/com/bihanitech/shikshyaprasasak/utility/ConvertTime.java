package com.bihanitech.shikshyaprasasak.utility;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertTime {

    public String convertDate(String sdate) {
/*

        String[] data = sdate.split(" ");
        String[] dat = data[0].split("-");
        String apple = dat[1] + "/" + dat[2] + "/" + dat[0];
        String newDate = apple + " " + data[1].substring(0, 8) + " " + data[1].substring(8);

*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");


        Date nDate = new Date();
        try {

            nDate = sdf.parse(sdate);
        } catch (ParseException e) {

        }

        Long newDateTimeLong = nDate.getTime();


        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                newDateTimeLong,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

        return timeAgo.toString();


    }
}
