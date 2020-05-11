package com.bihanitech.shikshyaprasasak.model.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentInfoResponse {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("SCHOOLID")
    @Expose
    private String sCHOOLID;
    @SerializedName("REGNO")
    @Expose
    private String rEGNO;
    @SerializedName("STNAME")
    @Expose
    private String sTNAME;
    @SerializedName("STCLASS")
    @Expose
    private String sTCLASS;
    @SerializedName("ROLLNO")
    @Expose
    private Integer rOLLNO;
    @SerializedName("DOBEDATE")
    @Expose
    private String dOBEDATE;
    @SerializedName("DOBNDATE")
    @Expose
    private String dOBNDATE;
    @SerializedName("GENDER")
    @Expose
    private String gENDER;
    @SerializedName("STDCONTACT")
    @Expose
    private String sTDCONTACT;
    @SerializedName("STADDRESS")
    @Expose
    private String sTADDRESS;
    @SerializedName("STDISTRICT")
    @Expose
    private String sTDISTRICT;
    @SerializedName("FATNAME")
    @Expose
    private String fATNAME;
    @SerializedName("MOTNAME")
    @Expose
    private String mOTNAME;
    @SerializedName("GUDNAME")
    @Expose
    private String gUDNAME;
    @SerializedName("STDSTATUS")
    @Expose
    private String sTDSTATUS;
    @SerializedName("STPPHOTO")
    @Expose
    private String sTPPHOTO;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("STCLASSID")
    @Expose
    private String sTCLASSID;
    @SerializedName("STSECTION")
    @Expose
    private String sTSECTION;
    @SerializedName("YEARID")
    @Expose
    private Integer yEARID;

    @SerializedName("STSECTIONNAME")
    @Expose
    private String stSectionName;


    public StudentInfoResponse() {

    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getSCHOOLID() {
        return sCHOOLID;
    }

    public void setSCHOOLID(String sCHOOLID) {
        this.sCHOOLID = sCHOOLID;
    }

    public String getREGNO() {
        return rEGNO;
    }

    public void setREGNO(String rEGNO) {
        this.rEGNO = rEGNO;
    }

    public String getSTNAME() {
        return sTNAME;
    }

    public void setSTNAME(String sTNAME) {
        this.sTNAME = sTNAME;
    }

    public String getSTCLASS() {
        return sTCLASS;
    }

    public void setSTCLASS(String sTCLASS) {
        this.sTCLASS = sTCLASS;
    }

    public Integer getROLLNO() {
        return rOLLNO;
    }

    public void setROLLNO(Integer rOLLNO) {
        this.rOLLNO = rOLLNO;
    }

    public String getDOBEDATE() {
        return dOBEDATE;
    }

    public void setDOBEDATE(String dOBEDATE) {
        this.dOBEDATE = dOBEDATE;
    }

    public String getDOBNDATE() {
        return dOBNDATE;
    }

    public void setDOBNDATE(String dOBNDATE) {
        this.dOBNDATE = dOBNDATE;
    }

    public String getGENDER() {
        return gENDER;
    }

    public void setGENDER(String gENDER) {
        this.gENDER = gENDER;
    }

    public String getSTDCONTACT() {
        return sTDCONTACT;
    }

    public void setSTDCONTACT(String sTDCONTACT) {
        this.sTDCONTACT = sTDCONTACT;
    }

    public String getSTADDRESS() {
        return sTADDRESS;
    }

    public void setSTADDRESS(String sTADDRESS) {
        this.sTADDRESS = sTADDRESS;
    }

    public String getSTDISTRICT() {
        return sTDISTRICT;
    }

    public void setSTDISTRICT(String sTDISTRICT) {
        this.sTDISTRICT = sTDISTRICT;
    }

    public String getFATNAME() {
        return fATNAME;
    }

    public void setFATNAME(String fATNAME) {
        this.fATNAME = fATNAME;
    }

    public String getMOTNAME() {
        return mOTNAME;
    }

    public void setMOTNAME(String mOTNAME) {
        this.mOTNAME = mOTNAME;
    }

    public String getGUDNAME() {
        return gUDNAME;
    }

    public void setGUDNAME(String gUDNAME) {
        this.gUDNAME = gUDNAME;
    }

    public String getSTDSTATUS() {
        return sTDSTATUS;
    }

    public void setSTDSTATUS(String sTDSTATUS) {
        this.sTDSTATUS = sTDSTATUS;
    }

    public String getSTPPHOTO() {
        return sTPPHOTO;
    }

    public void setSTPPHOTO(String sTPPHOTO) {
        this.sTPPHOTO = sTPPHOTO;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSTCLASSID() {
        return sTCLASSID;
    }

    public void setSTCLASSID(String sTCLASSID) {
        this.sTCLASSID = sTCLASSID;
    }

    public String getSTSECTION() {
        return sTSECTION;
    }

    public void setSTSECTION(String sTSECTION) {
        this.sTSECTION = sTSECTION;
    }

    public Integer getYEARID() {
        return yEARID;
    }

    public void setYEARID(Integer yEARID) {
        this.yEARID = yEARID;
    }


    public String getStSectionName() {
        return stSectionName;
    }

    public void setStSectionName(String stSectionName) {
        this.stSectionName = stSectionName;
    }
}
