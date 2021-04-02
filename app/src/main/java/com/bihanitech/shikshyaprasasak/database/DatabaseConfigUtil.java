package com.bihanitech.shikshyaprasasak.database;


import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.ExamName;
import com.bihanitech.shikshyaprasasak.model.SchoolInfo;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Event;
import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;

/**
 * Created by dilip on 1/11/18.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classesDatabase = new Class[]{
            SchoolInfo.class,
            ContactsItem.class,
            NoticeItem.class,
            Event.class,
            ExamName.class, Classes.class, Section.class

    };

    public static void main(String[] args) throws Exception {
        //create full path to ormlite_config.txt file
        String pathToConfig = System.getProperty("user.dir") + "/app/src/main/res/raw/ormlite_config.txt";
        File configFile = new File(pathToConfig);
        //we delete and update with new file for every new run
        if (configFile.exists()) {
            configFile.delete();
            configFile = new File(pathToConfig);
        }

        writeConfigFile(configFile, classesDatabase);
    }
}
