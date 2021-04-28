package com.bihanitech.shikshyaprasasak.ui.homeActivity.contactActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.LightDateConverter;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.Model;

import java.util.ArrayList;
import java.util.List;

public class ContactPresenter {

    private final ContactView contactView;
    private final MetaDatabaseRepo metaDatabaseRepo;

    //test code
    private List<String> engDate = new ArrayList<>();


    public ContactPresenter(ContactView contactView, MetaDatabaseRepo metaDatabaseRepo) {
        this.contactView = contactView;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    public void fetchContacts() {
        contactView.populateList(metaDatabaseRepo.getContactsList());
    }

    public void dummyAddEngDate(){
        engDate = new ArrayList<>();
        engDate.add("April 14, 2018");
        engDate.add("April 24, 2018");
        engDate.add("May 01, 2018");
        engDate.add("April 30, 2018");
        engDate.add("May 29, 2018");
        engDate.add("August 26, 2018");
        engDate.add("October 19, 2018");
        engDate.add("September 12, 2018");
        engDate.add("October 17, 2018");
        engDate.add("October 18, 2018");
        engDate.add("October 19, 2018");
        engDate.add("November 07, 2018");
        engDate.add("November 08, 2018");
        engDate.add("November 09, 2018");
        engDate.add("November 13, 2018");
        engDate.add("December 25, 2018");
        engDate.add("January 10, 2019");
        engDate.add("January 30, 2019");
        engDate.add("February 19, 2019");
        engDate.add("March 04, 2019");
        engDate.add("March 20, 2019");
        engDate.add("March 21, 2019");
        engDate.add("April 13, 2019");
        engDate.add("April 14, 2018");
        engDate.add("April 24, 2018");
        engDate.add("May 01, 2018");
        engDate.add("April 30, 2018");
        engDate.add("May 29, 2018");
        engDate.add("August 26, 2018");
        engDate.add("October 19, 2018");
        engDate.add("September 12, 2018");
        engDate.add("October 17, 2018");
        engDate.add("October 18, 2018");
        engDate.add("October 19, 2018");
        engDate.add("November 07, 2018");
        engDate.add("November 08, 2018");
        engDate.add("November 09, 2018");
        engDate.add("November 13, 2018");
        engDate.add("December 25, 2018");
        engDate.add("January 10, 2019");
        engDate.add("January 30, 2019");
        engDate.add("February 19, 2019");
        engDate.add("March 04, 2019");
        engDate.add("March 20, 2019");
        engDate.add("March 21, 2019");
        engDate.add("April 13, 2019");
        engDate.add("April 14, 2018");
        engDate.add("April 24, 2018");
        engDate.add("May 01, 2018");
        engDate.add("April 30, 2018");
        engDate.add("May 29, 2018");
        engDate.add("August 26, 2018");
        engDate.add("October 19, 2018");
        engDate.add("September 12, 2018");
        engDate.add("October 17, 2018");
        engDate.add("October 18, 2018");
        engDate.add("October 19, 2018");
        engDate.add("November 07, 2018");
        engDate.add("November 08, 2018");
        engDate.add("November 09, 2018");
        engDate.add("November 13, 2018");
        engDate.add("December 25, 2018");
        engDate.add("January 10, 2019");
        engDate.add("January 30, 2019");
        engDate.add("February 19, 2019");
        engDate.add("March 04, 2019");
        engDate.add("March 20, 2019");
        engDate.add("March 21, 2019");
        engDate.add("April 13, 2019");
        engDate.add("April 14, 2018");
        engDate.add("April 24, 2018");
        engDate.add("May 01, 2018");
        engDate.add("April 30, 2018");
        engDate.add("May 29, 2018");
        engDate.add("August 26, 2018");
        engDate.add("October 19, 2018");
        engDate.add("September 12, 2018");
        engDate.add("October 17, 2018");
        engDate.add("October 18, 2018");
        engDate.add("October 19, 2018");
        engDate.add("November 07, 2018");
        engDate.add("November 08, 2018");
        engDate.add("November 09, 2018");
        engDate.add("November 13, 2018");
        engDate.add("December 25, 2018");
        engDate.add("January 10, 2019");
        engDate.add("January 30, 2019");
        engDate.add("February 19, 2019");
        engDate.add("March 04, 2019");
        engDate.add("March 20, 2019");
        engDate.add("March 21, 2019");
        engDate.add("April 13, 2019");

    }


    public void convertAllDateToNepali(){
        dummyAddEngDate();
        LightDateConverter lightDateConverter = new LightDateConverter();
        List<Integer> nepDay = new ArrayList<>();
        List<Integer> nepMonth = new ArrayList<>();
        List<Integer> nepYear = new ArrayList<>();
        final String TAG = ContactPresenter.class.getSimpleName();

        for (String date : engDate) {
            Model model = lightDateConverter.getNepaliDate(Integer.parseInt(date.split(", ")[1]),convertMonthToNum(date.split(" ")[0]), Integer.parseInt(date.split(", ")[0].split(" ")[1]));
            Log.v(TAG,""+ model.getYear()+"/"+model.getMonth()+"/"+model.getDay());
            nepDay.add(model.getDay());
            nepMonth.add(model.getMonth());
            nepYear.add(model.getYear());


        }

        for (Integer integer : nepDay) {
            Log.v(TAG,"Day :"+integer );
        }

        for (Integer integer : nepMonth) {
            Log.v(TAG,"Month :"+integer );
        }

        nepYear.size();
        for (Integer integer : nepYear) {
            Log.v(TAG,"Year :"+integer );
        }
    }

    private int convertMonthToNum(String month){

        switch (month){
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
        }

        return 0;

    }


}
