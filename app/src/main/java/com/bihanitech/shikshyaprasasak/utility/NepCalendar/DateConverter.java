
    /*
     * Copyright (C) 2013 The Android Open Source Project
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */


package com.bihanitech.shikshyaprasasak.utility.NepCalendar;


    import android.annotation.SuppressLint;
    import android.util.Log;

    import androidx.annotation.IntRange;
    import androidx.annotation.NonNull;

    import com.bihanitech.shikshyaprasasak.R;
    import com.bihanitech.shikshyaprasasak.model.itemModels.CDayItem;

    import org.jetbrains.annotations.Contract;
    import org.joda.time.DateTime;
    import org.joda.time.Days;

    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.GregorianCalendar;
    import java.util.HashMap;
    import java.util.List;


    /**
     * @author Kiran Gyawali
     * <p>
     * so, you are interested enough to try and find the logic behind conversion
     * or may be you want to know weather i did it by myself or .......[you know what i mean]
     * are you here to JUDGE ME ?........ are you?
     * I don't mind unless you are a metalHead
     * <p>
     * Created by Hornet on 4/29/2016.
     */
    public class DateConverter {

        @SuppressLint("UseSparseArrays")
        private final HashMap<Integer, int[]> daysInMonthMap = new HashMap<>();
        @SuppressLint("UseSparseArrays")
        private final HashMap<Integer, int[]> startWeekDayMonthMap = new HashMap<>();

        public DateConverter() {
        /*
         The 0s at index 0 are dummy values so as to make the int array of
         days in months seems more intuitive that index 1 refers to first
         month "Baisakh", index 2 refers to second month "Jesth" and so on.
         */

            daysInMonthMap.put(2000, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2001, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2002, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2003, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2004, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2005, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2006, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2007, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2008, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31});
            daysInMonthMap.put(2009, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2010, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2011, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2012, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
            daysInMonthMap.put(2013, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2014, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2015, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2016, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
            daysInMonthMap.put(2017, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2018, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2019, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2020, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2021, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2022, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
            daysInMonthMap.put(2023, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2024, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2025, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2026, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2027, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2028, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2029, new int[]{0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2030, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2031, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2032, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2033, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2034, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2035, new int[]{0, 30, 32, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31});
            daysInMonthMap.put(2036, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2037, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2038, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2039, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
            daysInMonthMap.put(2040, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2041, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2042, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2043, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
            daysInMonthMap.put(2044, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2045, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2046, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2047, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2048, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2049, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
            daysInMonthMap.put(2050, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2051, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2052, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2053, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
            daysInMonthMap.put(2054, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2055, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2056, new int[]{0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2057, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2058, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2059, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2060, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2061, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2062, new int[]{0, 30, 32, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2063, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2064, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2065, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2066, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31});
            daysInMonthMap.put(2067, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2068, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2069, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2070, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
            daysInMonthMap.put(2071, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2072, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2073, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
            daysInMonthMap.put(2074, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2075, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2076, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
            daysInMonthMap.put(2077, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
            daysInMonthMap.put(2078, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2079, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
            daysInMonthMap.put(2080, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
            daysInMonthMap.put(2081, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2082, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2083, new int[]{0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2084, new int[]{0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2085, new int[]{0, 31, 32, 31, 32, 30, 31, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2086, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2087, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2088, new int[]{0, 30, 31, 32, 32, 30, 31, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2089, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});
            daysInMonthMap.put(2090, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});

            startWeekDayMonthMap.put(2000, new int[]{0, 4, 6, 3, 6, 3, 6, 1, 3, 5, 6, 1, 2});
            startWeekDayMonthMap.put(2001, new int[]{0, 5, 1, 4, 1, 4, 7, 3, 5, 6, 1, 2, 4});
            startWeekDayMonthMap.put(2002, new int[]{0, 6, 2, 5, 2, 6, 2, 4, 6, 7, 2, 3, 5});
            startWeekDayMonthMap.put(2003, new int[]{0, 7, 3, 7, 3, 7, 3, 5, 7, 2, 3, 4, 6});
            startWeekDayMonthMap.put(2004, new int[]{0, 2, 4, 1, 4, 1, 4, 6, 1, 3, 4, 6, 7});
            startWeekDayMonthMap.put(2005, new int[]{0, 3, 6, 2, 6, 2, 5, 1, 3, 4, 6, 7, 2});
            startWeekDayMonthMap.put(2006, new int[]{0, 4, 7, 3, 7, 4, 7, 2, 4, 5, 7, 1, 3});
            startWeekDayMonthMap.put(2007, new int[]{0, 5, 1, 5, 1, 5, 1, 3, 5, 7, 1, 2, 4});
            startWeekDayMonthMap.put(2008, new int[]{0, 7, 3, 6, 2, 6, 2, 5, 6, 1, 3, 4, 5});
            startWeekDayMonthMap.put(2009, new int[]{0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7});
            startWeekDayMonthMap.put(2010, new int[]{0, 2, 5, 1, 5, 2, 5, 7, 2, 3, 5, 6, 1});
            startWeekDayMonthMap.put(2011, new int[]{0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 7, 2});
            startWeekDayMonthMap.put(2012, new int[]{0, 5, 1, 4, 7, 4, 7, 3, 4, 6, 1, 2, 4});
            startWeekDayMonthMap.put(2013, new int[]{0, 6, 2, 5, 2, 5, 1, 4, 6, 7, 2, 3, 5});
            startWeekDayMonthMap.put(2014, new int[]{0, 7, 3, 6, 3, 7, 3, 5, 7, 1, 3, 4, 6});
            startWeekDayMonthMap.put(2015, new int[]{0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7});
            startWeekDayMonthMap.put(2016, new int[]{0, 3, 6, 2, 5, 2, 5, 1, 2, 4, 6, 7, 2});
            startWeekDayMonthMap.put(2017, new int[]{0, 4, 7, 3, 7, 3, 6, 2, 4, 5, 7, 1, 3});
            startWeekDayMonthMap.put(2018, new int[]{0, 5, 1, 5, 1, 5, 1, 3, 5, 6, 1, 2, 4});
            startWeekDayMonthMap.put(2019, new int[]{0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 4, 5});
            startWeekDayMonthMap.put(2020, new int[]{0, 1, 4, 7, 3, 7, 3, 6, 1, 2, 4, 5, 7});
            startWeekDayMonthMap.put(2021, new int[]{0, 2, 5, 1, 5, 1, 4, 7, 2, 3, 5, 6, 1});
            startWeekDayMonthMap.put(2022, new int[]{0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 7, 2});
            startWeekDayMonthMap.put(2023, new int[]{0, 4, 7, 4, 7, 4, 7, 2, 4, 6, 7, 2, 3});
            startWeekDayMonthMap.put(2024, new int[]{0, 6, 2, 5, 1, 5, 1, 4, 6, 7, 2, 3, 5});
            startWeekDayMonthMap.put(2025, new int[]{0, 7, 3, 6, 3, 6, 2, 5, 7, 1, 3, 4, 6});
            startWeekDayMonthMap.put(2026, new int[]{0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7});
            startWeekDayMonthMap.put(2027, new int[]{0, 3, 5, 2, 5, 2, 5, 7, 2, 4, 5, 7, 1});
            startWeekDayMonthMap.put(2028, new int[]{0, 4, 7, 3, 7, 3, 6, 2, 4, 5, 7, 1, 3});
            startWeekDayMonthMap.put(2029, new int[]{0, 5, 1, 4, 1, 4, 1, 3, 5, 6, 1, 2, 4});
            startWeekDayMonthMap.put(2030, new int[]{0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 3, 5});
            startWeekDayMonthMap.put(2031, new int[]{0, 1, 3, 7, 3, 7, 3, 5, 7, 2, 3, 5, 6});
            startWeekDayMonthMap.put(2032, new int[]{0, 2, 5, 1, 5, 1, 4, 7, 2, 3, 5, 6, 1});
            startWeekDayMonthMap.put(2033, new int[]{0, 3, 6, 2, 6, 3, 6, 1, 3, 4, 6, 7, 2});
            startWeekDayMonthMap.put(2034, new int[]{0, 4, 7, 4, 7, 4, 7, 2, 4, 6, 7, 1, 3});
            startWeekDayMonthMap.put(2035, new int[]{0, 6, 1, 5, 1, 5, 1, 4, 5, 7, 2, 3, 4});
            startWeekDayMonthMap.put(2036, new int[]{0, 7, 3, 6, 3, 6, 2, 5, 7, 1, 3, 4, 6});
            startWeekDayMonthMap.put(2037, new int[]{0, 1, 4, 7, 4, 1, 4, 6, 1, 2, 4, 5, 7});
            startWeekDayMonthMap.put(2038, new int[]{0, 2, 5, 2, 5, 2, 5, 7, 2, 4, 5, 6, 1});
            startWeekDayMonthMap.put(2039, new int[]{0, 4, 7, 3, 6, 3, 6, 2, 3, 5, 7, 1, 3});
            startWeekDayMonthMap.put(2040, new int[]{0, 5, 1, 4, 1, 4, 7, 3, 5, 6, 1, 2, 4});
            startWeekDayMonthMap.put(2041, new int[]{0, 6, 2, 5, 2, 6, 2, 4, 6, 7, 2, 3, 5});
            startWeekDayMonthMap.put(2042, new int[]{0, 7, 3, 7, 3, 7, 3, 5, 7, 2, 3, 4, 6});
            startWeekDayMonthMap.put(2043, new int[]{0, 2, 5, 1, 4, 1, 4, 7, 1, 3, 5, 6, 1});
            startWeekDayMonthMap.put(2044, new int[]{0, 3, 6, 2, 6, 2, 5, 1, 3, 4, 6, 7, 2});
            startWeekDayMonthMap.put(2045, new int[]{0, 4, 7, 4, 7, 4, 7, 2, 4, 5, 7, 1, 3});
            startWeekDayMonthMap.put(2046, new int[]{0, 5, 1, 5, 1, 5, 1, 3, 5, 7, 1, 2, 4});
            startWeekDayMonthMap.put(2047, new int[]{0, 7, 3, 6, 2, 6, 2, 5, 7, 1, 3, 4, 6});
            startWeekDayMonthMap.put(2048, new int[]{0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7});
            startWeekDayMonthMap.put(2049, new int[]{0, 2, 5, 2, 5, 2, 5, 7, 2, 4, 5, 6, 1});
            startWeekDayMonthMap.put(2050, new int[]{0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 1, 2});
            startWeekDayMonthMap.put(2051, new int[]{0, 5, 1, 4, 7, 4, 7, 3, 5, 6, 1, 2, 4});
            startWeekDayMonthMap.put(2052, new int[]{0, 6, 2, 5, 2, 5, 1, 4, 6, 7, 2, 3, 5});
            startWeekDayMonthMap.put(2053, new int[]{0, 7, 3, 7, 3, 7, 3, 5, 7, 2, 3, 4, 6});
            startWeekDayMonthMap.put(2054, new int[]{0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 6, 7});
            startWeekDayMonthMap.put(2055, new int[]{0, 3, 6, 2, 6, 2, 5, 1, 3, 4, 6, 7, 2});
            startWeekDayMonthMap.put(2056, new int[]{0, 4, 7, 3, 7, 3, 7, 2, 4, 5, 7, 1, 3});
            startWeekDayMonthMap.put(2057, new int[]{0, 5, 1, 5, 1, 5, 1, 3, 5, 7, 1, 2, 4});
            startWeekDayMonthMap.put(2058, new int[]{0, 7, 2, 6, 2, 6, 2, 4, 6, 1, 2, 4, 5});
            startWeekDayMonthMap.put(2059, new int[]{0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7});
            startWeekDayMonthMap.put(2060, new int[]{0, 2, 5, 1, 5, 2, 5, 7, 2, 3, 5, 6, 1});
            startWeekDayMonthMap.put(2061, new int[]{0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 7, 2});
            startWeekDayMonthMap.put(2062, new int[]{0, 5, 7, 4, 7, 4, 7, 3, 4, 6, 7, 2, 3});
            startWeekDayMonthMap.put(2063, new int[]{0, 6, 2, 5, 2, 5, 1, 4, 6, 7, 2, 3, 5});
            startWeekDayMonthMap.put(2064, new int[]{0, 7, 3, 6, 3, 7, 3, 5, 7, 1, 3, 4, 6});
            startWeekDayMonthMap.put(2065, new int[]{0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7});
            startWeekDayMonthMap.put(2066, new int[]{0, 3, 6, 2, 5, 2, 5, 1, 2, 4, 6, 7, 1});
            startWeekDayMonthMap.put(2067, new int[]{0, 4, 7, 3, 7, 3, 6, 2, 4, 5, 7, 1, 3});
            startWeekDayMonthMap.put(2068, new int[]{0, 5, 1, 4, 1, 5, 1, 3, 5, 6, 1, 2, 4});
            startWeekDayMonthMap.put(2069, new int[]{0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 3, 5});
            startWeekDayMonthMap.put(2070, new int[]{0, 1, 4, 7, 3, 7, 3, 6, 7, 2, 4, 5, 7});
            startWeekDayMonthMap.put(2071, new int[]{0, 2, 5, 1, 5, 1, 4, 7, 2, 3, 5, 6, 1});
            startWeekDayMonthMap.put(2072, new int[]{0, 3, 6, 3, 6, 3, 6, 1, 3, 4, 6, 7, 2});
            startWeekDayMonthMap.put(2073, new int[]{0, 4, 7, 4, 7, 4, 7, 2, 4, 6, 7, 1, 3});
            startWeekDayMonthMap.put(2074, new int[]{0, 6, 2, 5, 1, 5, 1, 4, 6, 7, 2, 3, 5});
            startWeekDayMonthMap.put(2075, new int[]{0, 7, 3, 6, 3, 6, 2, 5, 7, 1, 3, 4, 6});
            startWeekDayMonthMap.put(2076, new int[]{0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7});
            startWeekDayMonthMap.put(2077, new int[]{0, 2, 5, 2, 5, 2, 5, 7, 2, 4, 5, 7, 1});
            startWeekDayMonthMap.put(2078, new int[]{0, 4, 7, 3, 6, 3, 6, 2, 4, 5, 7, 1, 3});
            startWeekDayMonthMap.put(2079, new int[]{0, 5, 1, 4, 1, 4, 7, 3, 5, 6, 1, 2, 4});
            startWeekDayMonthMap.put(2080, new int[]{0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 3, 5});
            startWeekDayMonthMap.put(2081, new int[]{0, 7, 3, 6, 3, 7, 3, 5, 7, 2, 3, 5, 7});
            startWeekDayMonthMap.put(2082, new int[]{0, 2, 4, 1, 4, 1, 4, 6, 1, 3, 4, 6, 1});
            startWeekDayMonthMap.put(2083, new int[]{0, 3, 6, 2, 6, 2, 5, 7, 2, 4, 5, 7, 2});
            startWeekDayMonthMap.put(2084, new int[]{0, 4, 7, 3, 7, 3, 6, 1, 3, 5, 6, 1, 3});
            startWeekDayMonthMap.put(2085, new int[]{0, 5, 1, 5, 1, 5, 7, 3, 5, 7, 1, 3, 5});
            startWeekDayMonthMap.put(2086, new int[]{0, 7, 2, 6, 2, 6, 2, 4, 6, 1, 2, 4, 6});
            startWeekDayMonthMap.put(2087, new int[]{0, 1, 4, 7, 4, 7, 3, 6, 1, 3, 4, 6, 1});
            startWeekDayMonthMap.put(2088, new int[]{0, 3, 5, 1, 5, 2, 4, 7, 2, 4, 5, 7, 2});
            startWeekDayMonthMap.put(2089, new int[]{0, 4, 6, 3, 6, 3, 6, 1, 3, 5, 6, 1, 3});
            startWeekDayMonthMap.put(2090, new int[]{0, 5, 7, 4, 7, 4, 7, 2, 4, 6, 7, 2, 4});
        }


        /**
         * convert nepali date into english date
         * <p>
         * I've got 99 problems, but you ain't one
         * --Jay Z
         *
         * @param nepYY {@code int} year of nepali date [2000-2090]
         * @param nepMM {@code int} month of nepali date [1-12]
         * @param nepDD {@code int} day of a nepali date [1-32]
         * @return {@link Model } object with the converted value from nepali to english
         */
        public Model getEnglishDate(@IntRange(from = 2000, to = 2090) int nepYY,
                                    @IntRange(from = 1, to = 12) int nepMM,
                                    @IntRange(from = 1, to = 32) int nepDD) {

            if (isNepDateInConversionRange(nepYY, nepMM, nepDD)) {

                int startingEngYear = 1943;
                int startingEngMonth = 4;
                int startingEngDay = 14;

                int startingDayOfWeek = Calendar.WEDNESDAY; // 2000/1/1 is Wednesday

                int startingNepYear = 2000;
                int startingNepMonth = 1;
                int startingNepDay = 1;

                Model tempModel = new Model();

                int engYY, engMM, engDD;
                long totalNepDaysCount = 0;

                //*count total no of days in nepali year from our starting range*//
                for (int i = startingNepYear; i < nepYY; i++) {
                    for (int j = 1; j <= 12; j++) {
                        totalNepDaysCount = totalNepDaysCount + daysInMonthMap.get(i)[j];
                    }
                }
                /*count total days in terms of month*/
                for (int j = startingNepMonth; j < nepMM; j++) {
                    totalNepDaysCount = totalNepDaysCount + daysInMonthMap.get(nepYY)[j];
                }
                /*count total days in terms of date*/
                totalNepDaysCount += nepDD - startingNepDay;

                int[] daysInMonth = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                int[] daysInMonthOfLeapYear = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                engYY = startingEngYear;
                engMM = startingEngMonth;
                engDD = startingEngDay;
                int endDayOfMonth ;
                int dayOfWeek = startingDayOfWeek;
                while (totalNepDaysCount != 0) {
                    if (isEngLeapYear(engYY)) {
                        endDayOfMonth = daysInMonthOfLeapYear[engMM];
                    } else {
                        endDayOfMonth = daysInMonth[engMM];
                    }
                    engDD++;
                    dayOfWeek++;

                    if (engDD > endDayOfMonth) {
                        engMM++;
                        engDD = 1;
                        if (engMM > 12) {
                            engYY++;
                            engMM = 1;
                        }
                    }
                    if (dayOfWeek > 7) {
                        dayOfWeek = 1;
                    }
                    totalNepDaysCount--;
                }
                tempModel.setDay(engDD);
                tempModel.setYear(engYY);
                tempModel.setMonth(engMM - 1);
                tempModel.setDayOfWeek(dayOfWeek);
                return tempModel;
            } else throw new IllegalArgumentException("Out of Range: Date is out of range to Convert");
        }


        /**
         * convert nepali date into english date
         * <p>
         * I've got 99 problems, but you ain't one
         * --Jay Z
         *
         * @param nepYY {@code int} year of nepali date [2000-2090]
         * @param nepMM {@code int} month of nepali date [1-12]
         * @param nepDD {@code int} day of a nepali date [1-32]
         * @return {@link Model } object with the converted value from nepali to english
         */
        public List<CDayItem> getEnglishLightly(@IntRange(from = 2000, to = 2090) int nepYY,
                                                @IntRange(from = 1, to = 12) int nepMM,
                                                @IntRange(from = 1, to = 32) int nepDD) {

            if (isNepDateInConversionRange(nepYY, nepMM, nepDD)) {

                List<CDayItem> cDayItems = new ArrayList<>();
                int engYear = nepYY - 57;
                int engMM = nepMM + 3;
                Log.v("CalendarPresenter","I am stuck here");
                int engLastDayInNep = getNepaliDate(engYear,12,31).getDay();
                Log.v("CalendarPresenter","I am finished stuck here");

                if(nepMM > 10 || (nepMM == 9 && nepDD > engLastDayInNep)){
                    engYear++;
                }

                Log.v("CalendarPresenter","I am stuck here part 2");
                Model engFirstDay = getEnglishDate(nepYY,nepMM,1);
                Log.v("CalendarPresenter","I am finished stuck here part 2");

                int firstDay = engFirstDay.getDayOfWeek();
                int engFirstNepDay = engFirstDay.getDay();
                switch (engFirstDay.getMonth()) {
                    case 0:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 31, nepYY, nepMM);
                        break;
                    case 1:
                        if (isEngLeapYear(engYear)) {
                            cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 29, nepYY, nepMM);
                        } else {
                            cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 28, nepYY, nepMM);
                        }
                        break;
                    case 2:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 31, nepYY, nepMM);
                        break;
                    case 3:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 30, nepYY, nepMM);
                        break;
                    case 4:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 31, nepYY, nepMM);
                        break;
                    case 5:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 30, nepYY, nepMM);
                        break;
                    case 6:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 31, nepYY, nepMM);
                        break;
                    case 7:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 31, nepYY, nepMM);
                        break;
                    case 8:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 30, nepYY, nepMM);
                        break;
                    case 9:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 31, nepYY, nepMM);
                        break;
                    case 10:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 30, nepYY, nepMM);
                        break;
                    case 11:
                        cDayItems = getNepDateForMonth(engFirstNepDay, firstDay, 31, nepYY, nepMM);
                        break;
                    default:
                        cDayItems = null;
                }

                return cDayItems;
                }else throw new IllegalArgumentException("Out of Range: Date is out of range to Convert");


                /*
                int startingEngYear = 1943;
                int startingEngMonth = 4;
                int startingEngDay = 14;

                int startingDayOfWeek = Calendar.WEDNESDAY; // 2000/1/1 is Wednesday

                int startingNepYear = 2000;
                int startingNepMonth = 1;
                int startingNepDay = 1;

                Model tempModel = new Model();

                int engYY, engMM, engDD;
                long totalNepDaysCount = 0;

                //*count total no of days in nepali year from our starting range*
                for (int i = startingNepYear; i < nepYY; i++) {
                    for (int j = 1; j <= 12; j++) {
                        totalNepDaysCount = totalNepDaysCount + daysInMonthMap.get(i)[j];
                    }
                }
                //count total days in terms of month
                for (int j = startingNepMonth; j < nepMM; j++) {
                    totalNepDaysCount = totalNepDaysCount + daysInMonthMap.get(nepYY)[j];
                }
                //count total days in terms of date
                totalNepDaysCount += nepDD - startingNepDay;

                int[] daysInMonth = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                int[] daysInMonthOfLeapYear = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                engYY = startingEngYear;
                engMM = startingEngMonth;
                engDD = startingEngDay;
                int endDayOfMonth ;
                int dayOfWeek = startingDayOfWeek;
                while (totalNepDaysCount != 0) {
                    if (isEngLeapYear(engYY)) {
                        endDayOfMonth = daysInMonthOfLeapYear[engMM];
                    } else {
                        endDayOfMonth = daysInMonth[engMM];
                    }
                    engDD++;
                    dayOfWeek++;

                    if (engDD > endDayOfMonth) {
                        engMM++;
                        engDD = 1;
                        if (engMM > 12) {
                            engYY++;
                            engMM = 1;
                        }
                    }
                    if (dayOfWeek > 7) {
                        dayOfWeek = 1;
                    }
                    totalNepDaysCount--;
                }
                tempModel.setDay(engDD);
                tempModel.setYear(engYY);
                tempModel.setMonth(engMM - 1);
                tempModel.setDayOfWeek(dayOfWeek);
                return tempModel;*/


        }


      private List<CDayItem> getNepDateForMonth(int engFirstNepDay, int firstDay, int days, int nepYY, int nepMM){
          List<CDayItem> cDayItems = new ArrayList<>();
          int nepDay = 1;

          while(engFirstNepDay<=days){
              cDayItems.add(new CDayItem(engFirstNepDay,nepDay,firstDay, -1));
              firstDay++;
              engFirstNepDay++;
              nepDay++;
              if(firstDay == 8){
                  firstDay = 1;
              }
              Log.v("CalendarPresenter",engFirstNepDay+"");
          }
          engFirstNepDay = 1;
          while(nepDay <= daysInMonthMap.get(nepYY)[nepMM]){
              cDayItems.add(new CDayItem(engFirstNepDay,nepDay,firstDay, -1));
              nepDay++;
              firstDay++;
              engFirstNepDay++;
              if(firstDay == 8){
                  firstDay = 1;
              }
              Log.v("CalendarPresenter",engFirstNepDay+"");

          }
            return cDayItems;

      }


        /**
         * @param date {@link Calendar} Gregorian calendar
         * @return {@link Model} dateModel after conversion from the given Calendar
         */
        @SuppressWarnings("unused")
        public Calendar getEnglishDate(Model date) {
            Model tempModel = getEnglishDate(date.getYear(), date.getMonth(), date.getDay());
            return new GregorianCalendar(tempModel.getYear(), tempModel.getMonth(), tempModel.getDay(), 0, 0, 0);
        }

        /**
         * convert english date into nepali date
         * <p>
         * this is probably the method you are looking for
         * conversion logic
         * get into it
         * tweak it, make it, break it, shake it, share it
         * </p>
         *
         * @param engYY {@code int} year of nepali date [1944-2033]
         * @param engMM {@code int} month of nepali date [1-12]
         * @param engDD {@code int} day of a nepali date [1-31]
         * @return return nepali date as a {@link Model} object converted from english to nepali
         */
        public Model getNepaliDate(@IntRange(from = 1944 - 2033) int engYY,
                                   @IntRange(from = 1, to = 12) int engMM,
                                   @IntRange(from = 1, to = 31) int engDD) {

            if (isEngDateInConversionRange(engYY, engMM, engDD)) {

                int startingEngYear = 1944;
                int startingEngMonth = 1;
                int startingEngDay = 1;

                int startingDayOfWeek = Calendar.SATURDAY; // 1944/1/1 is Saturday

                int startingNepYear = 2000;
                int startingNepMonth = 9;
                int startingNepDay = 17;

                int nepYY, nepMM, nepDD;
                int dayOfWeek = startingDayOfWeek;

                Model tempModel = new Model();

            /*
            Calendar currentEngDate = new GregorianCalendar();
            currentEngDate.set(engYY, engMM, engDD);
            Calendar baseEngDate = new GregorianCalendar();
            baseEngDate.set(startingEngYear, startingEngMonth, startingEngDay);
            long totalEngDaysCount = daysBetween(baseEngDate, currentEngDate);
            */

                /*calculate the days between two english date*/
                DateTime base = new DateTime(startingEngYear, startingEngMonth, startingEngDay, 0, 0); // June 20th, 2010
                DateTime newDate = new DateTime(engYY, engMM, engDD, 0, 0); // July 24th
                long totalEngDaysCount = Days.daysBetween(base, newDate).getDays();

                nepYY = startingNepYear;
                nepMM = startingNepMonth;
                nepDD = startingNepDay;

                while (totalEngDaysCount != 0) {
                    int daysInMonth = daysInMonthMap.get(nepYY)[nepMM];
                    nepDD++;
                    if (nepDD > daysInMonth) {
                        nepMM++;
                        nepDD = 1;
                    }
                    if (nepMM > 12) {
                        nepYY++;
                        nepMM = 1;
                    }
                    dayOfWeek++;
                    if (dayOfWeek > 7) {
                        dayOfWeek = 1;
                    }
                    totalEngDaysCount--;
                }
                tempModel.setYear(nepYY);
                tempModel.setMonth(nepMM - 1);
                tempModel.setDay(nepDD);
                tempModel.setDayOfWeek(dayOfWeek);

                return tempModel;
            } else throw new IllegalArgumentException("Out of Range: Date is out of range to Convert");
        }

        /**
         * @param date {@link Calendar} Gregorian calendar
         * @return {@link Model} dateModel after conversion from the given Calendar
         */
        @SuppressWarnings("unused")
        public Model getNepaliDate(Calendar date) {
            return getNepaliDate(date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH));
        }

        /**
         * calculate whether english year is leap year or not
         * <p>
         * what if i tell you this method is useless! would you believe me?
         * </p>
         *
         * @param year {@code int} value of the year
         * @return {@code Boolean} true if it is leapYear and false if it is not a leapYear
         */
        private static boolean isEngLeapYear(int year) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
        }

        /**
         * returns nepali month
         * <p>
         * where's waldo?
         * </p>
         *
         * @param month {@code int} [0-11] integer value of month
         * @return {@code int} representing the string value of month
         */
        @Contract(pure = true)
        public static int getNepaliMonthString(@IntRange(from = 0, to = 11) int month) {
            switch (month) {
                case 0:
                    return (R.string.Baisakh);
                case 1:
                    return (R.string.Jestha);
                case 2:
                    return (R.string.Ashar);
                case 3:
                    return (R.string.Shrawan);
                case 4:
                    return (R.string.Bhadra);
                case 5:
                    return (R.string.Ashoj);
                case 6:
                    return (R.string.Kartik);
                case 7:
                    return (R.string.Mangsir);
                case 8:
                    return (R.string.Poush);
                case 9:
                    return (R.string.Magh);
                case 10:
                    return (R.string.Falgun);
                case 11:
                    return (R.string.Chaitra);
            }
            return 0;
        }

        /**
         * check if english date is in the range of conversion
         * <p>i am writing this comment just to entertain you
         * rather than reading all boring syntax and stuff,
         * where is the fun in that???
         * </p>
         *
         * @param yy {@code int} year of english date
         * @param mm {@code int} month of english date
         * @param dd {@code int} day of a english date
         * @return {@code Boolean} true if it is in range or false if it is not in range
         */
        @Contract(pure = true)
        private static boolean isEngDateInConversionRange(int yy, int mm, int dd) {
            return (yy >= 1944 && yy <= 2033) && (mm >= 1 && mm <= 12) && (dd >= 1 && dd <= 31);
        }

        /**
         * check if nepali date is in the range of conversion
         * <p>anyone interested in improving this whole algorithm .....you need to have 3 P's </p>
         *
         * @param yy {@code int} year of nepali date
         * @param mm {@code int} month of nepali date
         * @param dd {@code int} day of a nepali date
         * @return {@code Boolean} true if it is in range / false if it is not in range
         */
        @Contract(pure = true)
        private static boolean isNepDateInConversionRange(int yy, int mm, int dd) {
            return (yy >= 2000 && yy <= 2090) && (mm >= 1 && mm <= 12) && (dd >= 1 && dd <= 32);
        }

        /**
         * returns the day of week start nepali month
         * <p>
         * who wouldn't want anything fast unless its death.
         * its better to take data from references, rather than computing at every step you need.
         * if you think memory is your first priority than performance....i would have to think again
         * </p>
         *
         * @param yy {@code int} year of nepali date [2000-2090]
         * @param mm {@code int} month of nepali date [1-12]
         * @return {@code int} first week day of given month in given year
         */
        public int getFirstWeekDayMonth(@IntRange(from = 2000, to = 2090) int yy, @IntRange(from = 1, to = 12) int mm) {
            return startWeekDayMonthMap.get(yy)[mm];
        }

        /**
         * returns the number of days in a particular month of a nepali year
         * <p>
         * As you all are well aware
         * nepali date is not like the one in {@link GregorianCalendar}
         * </p>
         *
         * @param yy {@code int} nepali year
         * @param mm {@code int} nepali month
         * @return {@link int} number of days in a given month of a given year
         */
        public int noOfDaysInMonth(@IntRange(from = 2000, to = 2090) int yy, @IntRange(from = 1, to = 12) int mm) {
            return daysInMonthMap.get(yy)[mm];
        }

        /**
         * returns the model with value of weekDay for a given DateModel
         * <p>
         * suppose you created new nepaliDate {@link Model} and want to know which weekday does it belong to
         * </p>
         *
         * @param nepaliDate {@link Model} any nepali date model with the missing weekDay value
         * @return {@link Model} nepaliDate is returned after setting the weekDay value
         */
        private Model fillMissingWeekDayValue(@NonNull Model nepaliDate) {
            int startWeekDay = getFirstWeekDayMonth(nepaliDate.getYear(),
                    nepaliDate.getMonth());
            for (int i = 1; i < nepaliDate.getDay(); i++) {
                startWeekDay++;
                if (startWeekDay > 7) startWeekDay = 1;
            }
            nepaliDate.setDayOfWeek(startWeekDay);
            return nepaliDate;
        }

        /**
         * if you want to know the weekday of a specific date without need of passing {@link Model} dateModel
         * <p>
         * i don't see much significance of this method
         * "BIG BUT"
         * we can be lazy sometimes , we may want to do things differently or we just want to be a black sheep for no reason
         * so just in case
         * </P>
         *
         * @param yy {@code int} year of nepali date [2000-2090]
         * @param mm {@code int} month of nepali date [1-12]
         * @param dd {@code int} day of a nepali date [1-32]
         * @return week day value in {@link int} [0-7]
         */
        public int getWeekDay(@IntRange(from = 2000, to = 2090) int yy, @IntRange(from = 1, to = 12) int mm, @IntRange(from = 1, to = 32) int dd) {
            Model tempModel = fillMissingWeekDayValue(new Model(yy, mm, dd));
            return tempModel.getDayOfWeek();
        }
        /**
         * aja kati gate ho oi?
         *
         * @return {@link Model} returns the date model with value of present date
         */

        public Model getTodayNepaliDate() {
            return getNepaliDate(Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }
        /**
         * @param  tempModel {@link Model} dateModel that need to be converted into Calendar
         * @return  {@link Calendar} Gregorian calendar
         */
        @SuppressWarnings("unused")
        public Calendar convertModelToCalendar(Model tempModel) {
            return new GregorianCalendar(tempModel.getYear(), tempModel.getMonth(), tempModel.getDay(), 0, 0, 0);
        }

        /**
         * @param date {@link Calendar} Gregorian calendar
         * @return {@link Model} dateModel after conversion from the given Calendar
         */
        @SuppressWarnings("unused")
        public Model convertCalendarToModel(Calendar date) {
            return new Model(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        }
    }

