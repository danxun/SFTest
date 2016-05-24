package com.sf.test.sftest.eventModel;

/**
 * Created by dw on 2016-05-21.
 */
public class GetSchoolDataEvent {

    public final boolean hasData;
    public final String webData;
    public GetSchoolDataEvent(boolean hasData,String webData){
        this.hasData=hasData;
        this.webData=webData;
    }

}
