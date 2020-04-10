package com.bihanitech.shikshyaprasasak.utility.NepCalendar;

import android.annotation.SuppressLint;

import com.bihanitech.shikshyaprasasak.model.NepDate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Calendar {

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, int[]> daysInMonthMap = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, int[]> startWeekDayMonthMap = new HashMap<>();


    private List<List<Integer>> bs = Arrays.asList(
            Arrays.asList(2000,30,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2001,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2002,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2003,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2004,30,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2005,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2006,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2007,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2008,31,31,31,32,31,31,29,30,30,29,29,31),
            Arrays.asList(2009,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2010,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2011,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2012,31,31,31,32,31,31,29,30,30,29,30,30),
            Arrays.asList(2013,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2014,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2015,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2016,31,31,31,32,31,31,29,30,30,29,30,30),
            Arrays.asList(2017,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2018,31,32,31,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2019,31,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2020,31,31,31,32,31,31,30,29,30,29,30,30),
            Arrays.asList(2021,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2022,31,32,31,32,31,30,30,30,29,29,30,30),
            Arrays.asList(2023,31,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2024,31,31,31,32,31,31,30,29,30,29,30,30),
            Arrays.asList(2025,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2026,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2027,30,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2028,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2029,31,31,32,31,32,30,30,29,30,29,30,30),
            Arrays.asList(2030,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2031,30,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2032,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2033,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2034,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2035,30,32,31,32,31,31,29,30,30,29,29,31),
            Arrays.asList(2036,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2037,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2038,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2039,31,31,31,32,31,31,29,30,30,29,30,30),
            Arrays.asList(2040,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2041,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2042,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2043,31,31,31,32,31,31,29,30,30,29,30,30),
            Arrays.asList(2044,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2045,31,32,31,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2046,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2047,31,31,31,32,31,31,30,29,30,29,30,30),
            Arrays.asList(2048,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2049,31,32,31,32,31,30,30,30,29,29,30,30),
            Arrays.asList(2050,31,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2051,31,31,31,32,31,31,30,29,30,29,30,30),
            Arrays.asList(2052,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2053,31,32,31,32,31,30,30,30,29,29,30,30),
            Arrays.asList(2054,31,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2055,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2056,31,31,32,31,32,30,30,29,30,29,30,30),
            Arrays.asList(2057,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2058,30,32,31,32,31,30,30,30,29,30,29,31),
            Arrays.asList(2059,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2060,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2061,31,32,31,32,31,30,30,30,29,29,30,31),
            Arrays.asList(2062,30,32,31,32,31,31,29,30,29,30,29,31),
            Arrays.asList(2063,31,31,32,31,31,31,30,29,30,29,30,30),
            Arrays.asList(2064,31,31,32,32,31,30,30,29,30,29,30,30),
            Arrays.asList(2065,31,32,31,32,31,30,30,30,29,29,30,31),
                Arrays.asList(2066,31,31,31,32,31,31,29,30,30,29,29,31),
                Arrays.asList(2067,31,31,32,31,31,31,30,29,30,29,30,30),
                Arrays.asList(2068,31,31,32,32,31,30,30,29,30,29,30,30),
                Arrays.asList(2069,31,32,31,32,31,30,30,30,29,29,30,31),
                Arrays.asList(2070,31,31,31,32,31,31,29,30,30,29,30,30),
                Arrays.asList(2071,31,31,32,31,31,31,30,29,30,29,30,30),
                Arrays.asList(2072,31,32,31,32,31,30,30,29,30,29,30,30),
                Arrays.asList(2073,31,32,31,32,31,30,30,30,29,29,30,31),
                Arrays.asList(2074,31,31,31,32,31,31,30,29,30,29,30,30),
                Arrays.asList(2075,31,31,32,31,31,31,30,29,30,29,30,30),
                Arrays.asList(2076,31,32,31,32,31,30,30,30,29,29,30,30),
                Arrays.asList(2077,31,32,31,32,31,30,30,30,29,30,29,31),
                Arrays.asList(2078,31,31,31,32,31,31,30,29,30,29,30,30),
                Arrays.asList(2079,31,31,32,31,31,31,30,29,30,29,30,30),
                Arrays.asList(2080,31,32,31,32,31,30,30,30,29,29,30,30),
                Arrays.asList(2081,31,31,32,32,31,30,30,30,29,30,30,30),
                Arrays.asList(2082,30,32,31,32,31,30,30,30,29,30,30,30),
                Arrays.asList(2083,31,31,32,31,31,30,30,30,29,30,30,30),
                Arrays.asList(2084,31,31,32,31,31,30,30,30,29,30,30,30),
                Arrays.asList(2085,31,32,31,32,30,31,30,30,29,30,30,30),
                Arrays.asList(2086,30,32,31,32,31,30,30,30,29,30,30,30),
                Arrays.asList(2087,31,31,32,31,31,31,30,30,29,30,30,30),
                Arrays.asList(2088,30,31,32,32,30,31,30,30,29,30,30,30),
                Arrays.asList(2089,30,32,31,32,31,30,30,30,29,30,30,30),
                Arrays.asList(2090,30,32,31,32,31,30,30,30,29,30,30,30)

        );


    private HashMap<String,String> nepDate = new HashMap<String,String>(){
        {
            put("year","");
            put("month","");
            put("date","");
            put("day","");
            put("month","");
            put("numDay","");
        }
    };


    private HashMap<String,String> engDate = new HashMap<String,String>(){
        {
            put("year","");
            put("month","");
            put("date","");
            put("day","");
            put("month","");
            put("numDay","");
        }
    };


    public Boolean isLeapYaer(int year){
        if(year % 100 == 0){
            return year % 400 == 0;
        }else{
            return year % 4 == 0;
        }
    }


    public static String getNepaliMonth(int month){
        switch (month){
            case 1:
                return "Baisakh";
            case 2:
                return "Jestha";
            case 3:
                return "Ashad";
            case 4:
                return "Shrawn";
            case 5:
                return "Bhadra";
            case 6:
                return "Ashwin";
            case 7:
                return "Kartik";
            case 8:
                return "Mangsir";
            case 9:
                return "Poush";
            case 10:
                return "Magh";
            case 11:
                return "Falgun";
            case 12:
                return "Chaitra";
            default:
                return "";

        }
    }

    public static String getEngMonthForNepMonth(int month, int year){
        int engYear = year - 57;
        switch (month) {
            case 1:
                return "Apr/May \n"+engYear;
            case 2:
                return "May/Jun \n"+engYear;
            case 3:
                return "Jun/Jul \n"+engYear;
            case 4:
                return "Jul/Aug \n"+engYear;
            case 5:
                return "Aug/Sep \n"+engYear;
            case 6:
                return "Sep/Oct \n"+engYear;
            case 7:
                return "Oct/Nov \n"+engYear;
            case 8:
                return "Nov/Dec \n"+engYear;
            case 9:
                return "Dec/Jan \n"+engYear+"/"+(engYear+1);
            case 10:
                return "Jan/Feb \n"+(engYear+1);
            case 11:
                return "Feb/Mar \n"+(engYear+1);
            case 12:
                return "Mar/Apr \n"+(engYear+1);
            default:
                return "";
        }

    }

    public String getEngMonth(int month){
        switch (month){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";

        }
    }

    private String getDayOfWeek(int day){
        switch(day){
            case 1:
                return  "Sunday";

            case 2:
                return "Monday";

            case 3:
                return "Tuesday";

            case 4:
                return "Wednesday";

            case 5:
                return  "Thursday";

            case 6:
                return "Friday";

            case 7:
                return  "Saturday";
            default:
                return "";
        }
    }


    public boolean isRangeEng(int y, int m, int d){
        if(y<1944 || y > 2023){
            return false;
        }

        if(m < 1 || m > 12){
            return false;
        }

        return d >= 1 && d <= 31;
    }


    public boolean isRangeNep(int y, int m, int d){
        if(y<2000 || y > 2089){
            return false;
        }

        if(m < 1 || m > 12){
            return false;
        }

        return d >= 1 && d <= 32;
    }


    public NepDate AD_TO_BS(int yy , int mm, int dd) {
        if(!isRangeEng(yy,mm,dd)) {

            List<Integer> month = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
            List<Integer> lMonth = Arrays.asList(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

            int defEYear = 1994;
            int defNYear = 2000;
            int defNMonth = 9;
            int defNDay = 16;
            int totalEDay = 0;
            int totalNDay = 0;
            int a = 0;
            int day = 7 - 1;
            int m = 0;
            int y = 0;
            int i = 0;
            int j = 0;
            int numDay = 0;

            for (i = 0; i < (yy - defEYear); i++) {
                if (isLeapYaer(defEYear + i)) {
                    for (j = 0; j < 12; j++)
                        totalEDay += lMonth.get(j);
                } else
                    for (j = 0; j < 12; j++)
                        totalEDay += month.get(j);
            }


            for(i=0; i<(mm-1); i++){
                if(isLeapYaer(yy))
                    totalEDay += lMonth.get(i);
                else
                    totalEDay += month.get(i);
            }


            totalEDay += dd;

            i = 0; j = defNMonth;
            totalNDay = defNDay;
            m = defNMonth;
            y = defNYear;


            while (totalEDay!=0){
                a = bs.get(i).get(j);
                totalNDay++;
                day++;
                if(totalNDay > a){
                    m++;
                    totalNDay = 1;
                    j++;
                }

                if(day > 7)
                    day = 1;
                if(m > 12){
                    y++;
                    m = 1;
                }

                if(j > 12){
                    j = 1;
                    i++;
                }
                totalEDay--;
            }


            numDay = day;

            return new NepDate(y+"",m+"",totalNDay+"",getDayOfWeek(day),getNepaliMonth(m),numDay+"");

        }else {

            return null;
        }







    }
}
