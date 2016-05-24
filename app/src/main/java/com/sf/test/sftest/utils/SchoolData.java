package com.sf.test.sftest.utils;

import com.orm.SugarRecord;

/**
 * Created by dw on 2016-05-21.
 */
public class SchoolData  extends SugarRecord {

    private String number;
    private String name;
    private String webdata;

    public SchoolData(){
    }

    public SchoolData(String number, String name, String webdata){
        this.number=number;
        this.name=name;
        this.webdata=webdata;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebdata() {
        return webdata;
    }

    public void setWebdata(String webdata) {
        this.webdata = webdata;
    }
}
