package com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.eventHolidays.Event;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Holiday;
import com.bihanitech.shikshyaprasasak.model.itemModels.CDayItem;
import com.bihanitech.shikshyaprasasak.model.responseModel.HolidayEventResponse;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.repositories.DbInternalRepo;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.LightDateConverter;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.Model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarPresenter {

    public static final String TAG = CalendarPresenter.class.getSimpleName();
    private final CalendarView calendarView;
    private final DbInternalRepo dbInternalRepo;
    private CDSService cdsService;

    public CalendarPresenter(CalendarView calendarView, DbInternalRepo dbInternalRepo) {
        this.calendarView = calendarView;
        this.dbInternalRepo = dbInternalRepo;
    }

    public void fetchAndPopulateCalendar(Model todayNepDate, int currentDate) {
        Log.v(TAG, "At the first while fetching");

        List<CDayItem> dayItems = getNepDayItems(todayNepDate);
        List<CDayItem> sorteddayItems = new ArrayList<>();


        if (dayItems.get(0).getDay() != 1) {
            int firstday = dayItems.get(0).getDay();
            int i = 1;
            while (i != firstday) {
                sorteddayItems.add(new CDayItem(0, 0, 0, currentDate));
                i++;
            }
        }
        for (CDayItem dayItem : dayItems) {
            if (dayItem.getEnglishDate() == currentDate) {
                dayItem.setCurrentDate(1);
            } else {
                dayItem.setCurrentDate(0);
            }
        }
        sorteddayItems.addAll(dayItems);

        calendarView.populateCalendar(sorteddayItems);

    }


    public List<CDayItem> getNepDayItems(Model todayNepDate) {

        List<CDayItem> dayItems = new ArrayList<>();

        Log.v(TAG, "Before Date Converter Initialized");

        LightDateConverter dateConverter = new LightDateConverter();
        Log.v(TAG, "Year : " + todayNepDate.getYear() + " Month : " + todayNepDate.getMonth() + " Day : " + todayNepDate.getDay());
        dayItems = dateConverter.getEnglishLightly(todayNepDate.getYear(), todayNepDate.getMonth(), todayNepDate.getDay());


        Log.v(TAG, "At the end of presenter call");

        return dayItems;
    }


    public void downloadEventsHolidays(String token, final int refetch, final Boolean autoDownload) {
        cdsService = ApiUtils.getCDSService();


        cdsService.fetchHolidayEventList("bearer" + token).enqueue(new Callback<HolidayEventResponse>() {

            @Override
            public void onResponse(Call<HolidayEventResponse> call, Response<HolidayEventResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "posts loaded from API");
                    HolidayEventResponse holidayEvents = response.body();


                    if (refetch == 1) {
                        dbInternalRepo.deleteEventsAndHolidays();
                    }
                    List<Event> events = new ArrayList<>();
                    List<Holiday> holidays = new ArrayList<>();
                    if (holidayEvents.getEvent().size() != 0) {
                        for (HolidayEventResponse.Event event : holidayEvents.getEvent()) {
                            Model nepDate = convertEventsDateToNepali(event.getStart());
                            Event event1 = new Event();
                            event1.setDetail(event.getTitle());
                            event1.setVenue(event.getVenue());
                            event1.setEngDate(event.getStart());
                            event1.setNepYear(nepDate.getYear());
                            event1.setNepMonth(nepDate.getMonth() + 1);
                            event1.setNepDay(nepDate.getDay());
                            events.add(event1);
                        }
                        dbInternalRepo.addEvents(events);

                    }

                    if (holidayEvents.getHoliday().size() != 0) {
                        for (HolidayEventResponse.Holiday holiday : holidayEvents.getHoliday()) {
                            Model nepDate = convertAllDateToNepali(holiday.getStart());
                            Holiday holiday1 = new Holiday();
                            holiday1.setDetail(holiday.getTitle());
                            holiday1.setEngDate(holiday.getStart());
                            holiday1.setNepYear(nepDate.getYear());
                            holiday1.setNepMonth(nepDate.getMonth() + 1);
                            holiday1.setNepDay(nepDate.getDay());
                            holidays.add(holiday1);
                        }

                        dbInternalRepo.addHolidays(holidays);
                    }
                    calendarView.setCalendarInfoDownloaded();
                    calendarView.proceedToShowEventHolidayList();


                } else {
                    if (autoDownload == false) {
                        int statusCode = response.code();
                        // handle request errors depending on status code
                        Log.v(TAG, "statusCode " + statusCode);
                        if (refetch == 1) {
                            if (response.code() == 401) {
                                calendarView.showUnauthorized();
                            } else {
                                calendarView.showSnackBar(1);
                            }
                        } else {
                            if (response.code() == 401) {
                                calendarView.showUnauthorized();
                            } else {
                                calendarView.showServerError();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HolidayEventResponse> call, Throwable t) {
                if (autoDownload == false) {

                    Log.d(TAG, call.toString());
                    Log.d(TAG, "error loading from API");
                    //   Log.d(TAG, t.getMessage());
                    Log.d(TAG, t.toString());
                    if (t instanceof Exception) {
                        Log.v(TAG, "this is ioException");
                        if (refetch == 1)
                            calendarView.showSnackBar(0);
                        else
                            calendarView.showNetworkError();
                    }
                }

            }
        });


    }

    public Model convertEventsDateToNepali(String engDate) {
        LightDateConverter lightDateConverter = new LightDateConverter();
        Model model = lightDateConverter.getNepaliDate(Integer.parseInt(engDate.split("-")[0]), Integer.parseInt(engDate.split("-")[1]), Integer.parseInt(engDate.split("-")[2]));
        return model;
    }

    public Model convertAllDateToNepali(String engDate) {
        LightDateConverter lightDateConverter = new LightDateConverter();

        Model model = lightDateConverter.getNepaliDate(Integer.parseInt(engDate.split(", ")[1]), convertMonthToNum(engDate.split(" ")[0]), Integer.parseInt(engDate.split(", ")[0].split(" ")[1]));
        return model;

    }

    private int convertMonthToNum(String month) {

        switch (month) {
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
