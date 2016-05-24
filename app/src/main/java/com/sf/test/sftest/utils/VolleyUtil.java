package com.sf.test.sftest.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dw on 2016-05-18.
 */
public class VolleyUtil {
    private static VolleyUtil instance;
    private static RequestQueue requestQueue;
    private Context mContext;
    private VolleyUtil(){

    }

    public static synchronized VolleyUtil getInstance(){
        if(instance==null) instance=new VolleyUtil();
        return instance;
    }
    public void Init(Context context){

        mContext=context;
        requestQueue = Volley.newRequestQueue(mContext);
    }

    public  RequestQueue getRequestQueue(){
        return requestQueue;
    }
}

