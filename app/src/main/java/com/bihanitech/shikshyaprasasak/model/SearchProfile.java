package com.bihanitech.shikshyaprasasak.model;

public class SearchProfile {
    Integer id;
    String name;
    String address;
    String classAndSection;
    Integer imageId;
    String fatherName;
    String motherName;
    String guardianName;
    String contactNumber;


    public SearchProfile(String name, String address, String classAndSection, Integer imageId, String fatherName, String motherName, String guardianName, String contactNumber) {
        this.name = name;
        this.address = address;
        this.classAndSection = classAndSection;
        this.imageId = imageId;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.guardianName = guardianName;
        this.contactNumber = contactNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassAndSection() {
        return classAndSection;
    }

    public void setClassAndSection(String classAndSection) {
        this.classAndSection = classAndSection;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
