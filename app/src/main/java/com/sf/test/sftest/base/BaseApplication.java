package com.sf.test.sftest.base;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orm.SugarContext;
import com.sf.test.sftest.utils.VolleyUtil;


public class BaseApplication extends Application {
    protected RequestQueue mQueue;
    private static Context context;

    @Override
    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();
        SugarContext.init(context);
        VolleyUtil.getInstance().Init(getApplicationContext());
        ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(getApplicationContext());
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
