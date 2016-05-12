package com.lanjiaomao.foursage.bean;

/**
 * Created by root on 2016/5/9.
 */
public class Friend {
    String name;
    String icon;
    public Friend(){

    }
    public Friend(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
