package com.bihanitech.shikshyaprasasak.repositories;



import com.bihanitech.shikshyaprasasak.model.MetaSchool;
import com.bihanitech.shikshyaprasasak.model.SchoolInfo;
import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;

import java.util.List;

/**
 * Created by dilip on 3/6/18.
 */

public interface MetaDatabase {
    void addAllMetaSchool(MetaSchool body);

    List<SchoolInfo> getSchoolInfo();
    
    void addContactList(List<ContactsItem> contactsItems);

    List<ContactsItem> getContactsList();

}
