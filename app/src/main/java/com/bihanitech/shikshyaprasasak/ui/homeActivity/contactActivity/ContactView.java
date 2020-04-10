package com.bihanitech.shikshyaprasasak.ui.homeActivity.contactActivity;


import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;

import java.util.List;

public interface ContactView {
    void populateList(List<ContactsItem> contactsItemList);

    void proceedToCall(String contacts);
}
