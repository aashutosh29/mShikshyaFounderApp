package com.bihanitech.shikshyaprasasak.model.incomeSummary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("HEADINGID")
    @Expose
    private String headingid;
    @SerializedName("REGNO")
    @Expose
    private String regno;
    @SerializedName("PARTICULAR")
    @Expose
    private String particular;
    @SerializedName("CRAMT")
    @Expose
    private Integer cramt;
    @SerializedName("TRANUSER")
    @Expose
    private String tranuser;
    @SerializedName("TRANTIME")
    @Expose
    private String trantime;

    public String getHeadingid() {
        return headingid;
    }

    public void setHeadingid(String headingid) {
        this.headingid = headingid;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Integer getCramt() {
        return cramt;
    }

    public void setCramt(Integer cramt) {
        this.cramt = cramt;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getTrantime() {
        return trantime;
    }

    public void setTrantime(String trantime) {
        this.trantime = trantime;
    }

}