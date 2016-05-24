package com.sf.test.sftest.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


public class BaseActivity extends Activity {

    protected final String TAG = getClass().getSimpleName();
    protected Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = this;

    }



}
