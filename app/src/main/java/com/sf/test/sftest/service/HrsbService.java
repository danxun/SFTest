package com.sf.test.sftest.service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sf.test.sftest.eventModel.GetSchoolDataEvent;
import com.sf.test.sftest.utils.SchoolData;
import com.sf.test.sftest.utils.SchoolDataUtil;
import com.sf.test.sftest.utils.VolleyUtil;
import com.sf.test.sftest.utils.constants.AppConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by dw on 2016-05-19.
 */
public class HrsbService {

    private final String TAG = getClass().getSimpleName();
    private String streetName;
    private int streetNumber;

    public HrsbService(int number,String name){
        this.streetName=name;
        this.streetNumber =number;
    }

    public void getSchoolData(){


        String httpurl= AppConfig.default_url;
        Log.d(TAG,"begin request data from:"+httpurl);

        StringRequest request = new StringRequest(Request.Method.POST, httpurl,
                responseListener, responseErrorLisener) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("DistrictGUID", "F3627A4F-1318-497A-BEB0-59EB8755CFE8");
                map.put("DISTRICTCODE", "NS54");
                map.put("DataStatus", "1");
                map.put("InitialSearch", "1");
                map.put("txtAddress_name", streetName);
                Log.d(TAG,"streetName="+streetName);
                map.put("SearchType", "3");
                return map;
            }
        };
        VolleyUtil.getInstance().getRequestQueue().add(request);
    }

    private Response.Listener<String> responseListener = new Response.Listener<String>(){
        @Override
        public void onResponse(String response){
            Log.d(TAG, "response -> " + response);
            List<SchoolData> school= SchoolData.find(SchoolData.class,"number = ? and name =?",String.valueOf(streetNumber),streetName );
            if(school !=null && school.size()>0){
                Log.d(TAG, "find in local DB");
                SchoolData ss=(SchoolData)school.get(0);
                EventBus.getDefault().post(new GetSchoolDataEvent(true, ss.getWebdata()));
            }else {
                Log.d(TAG, "begin to get from web url, respone is:" + response);
                SchoolDataUtil schoolDataUtil = new SchoolDataUtil();
                String schoolInformation = schoolDataUtil.getSchoolData(streetNumber, response);
                Log.d(TAG, "streetNumber="+streetNumber+" streetName="+streetName+" schoolInformation  is:" + schoolInformation);
                if (schoolInformation != null && schoolInformation.isEmpty()==false && !schoolInformation.equals("") && schoolInformation.length()>10) {
                    SchoolData schoolRecord= new SchoolData(String.valueOf(streetNumber),streetName,schoolInformation);
                    schoolRecord.save();
                    EventBus.getDefault().post(new GetSchoolDataEvent(true, schoolInformation));
                } else {
                    EventBus.getDefault().post(new GetSchoolDataEvent(false, ""));
                }
            }
        }
    };

    private Response.ErrorListener responseErrorLisener = new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "error message is"+error.getMessage(), error);
            EventBus.getDefault().post(new GetSchoolDataEvent(false, ""));
        }

    };
}
