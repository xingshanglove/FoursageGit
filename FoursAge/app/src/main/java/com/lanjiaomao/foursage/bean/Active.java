package com.lanjiaomao.foursage.bean;

/**
 * Created by root on 2016/5/10.
 */
public class Active {
    String p1Icon;
    String p2Icon;
    String p1Name;
    String p2Name;
    String theme;
    String desc;
    String time;
    String location;
    public Active(){

    }

    public Active(String p1Icon, String location, String time, String desc, String p2Name, String p1Name, String p2Icon, String theme) {
        this.p1Icon = p1Icon;
        this.location = location;
        this.time = time;
        this.desc = desc;
        this.p2Name = p2Name;
        this.p1Name = p1Name;
        this.p2Icon = p2Icon;
        this.theme = theme;
    }

    public String getP1Icon() {
        return p1Icon;
    }

    public void setP1Icon(String p1Icon) {
        this.p1Icon = p1Icon;
    }

    public String getP2Icon() {
        return p2Icon;
    }

    public void setP2Icon(String p2Icon) {
        this.p2Icon = p2Icon;
    }

    public String getP2Name() {
        return p2Name;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    public String getP1Name() {
        return p1Name;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
