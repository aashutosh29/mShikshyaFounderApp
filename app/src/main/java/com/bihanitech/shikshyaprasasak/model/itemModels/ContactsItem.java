package com.bihanitech.shikshyaprasasak.model.itemModels;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ContactsItem {


    @DatabaseField
    @Expose
    private String name;

    @DatabaseField
    @Expose
    private String contacts;

    public ContactsItem(){

    }

    public ContactsItem(String name, String contacts) {
        this.name = name;
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
