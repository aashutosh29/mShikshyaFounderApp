package com.bihanitech.shikshyaprasasak.model.responseModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class HolidayEventResponse {

    @SerializedName("holiday")
    @Expose
    private List<Holiday> holiday = null;

    @SerializedName("event")
    @Expose
    private List<Event> event = null;

    public List<Holiday> getHoliday() {
        return holiday;
    }

    public void setHoliday(List<Holiday> holiday) {
        this.holiday = holiday;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }


    public class Event {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("start_date")
        @Expose
        private String start;
        @SerializedName("end_date")
        @Expose
        private String end;
        @SerializedName("venue")
        @Expose
        private String venue;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

    }


    public class Holiday {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("start")
        @Expose
        private String start;
        @SerializedName("end")
        @Expose
        private String end;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }
    }

}