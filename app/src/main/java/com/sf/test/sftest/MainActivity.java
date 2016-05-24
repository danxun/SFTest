package com.sf.test.sftest;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.orm.SugarContext;
import com.sf.test.sftest.base.BaseActivity;
import com.sf.test.sftest.eventModel.GetSchoolDataEvent;
import com.sf.test.sftest.service.HrsbService;
import com.sf.test.sftest.utils.SchoolData;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {

    @Bind(R.id.button_submit)
    Button btn_submit;

    @Bind(R.id.editText_streetName)
    EditText text_query;

    @Bind(R.id.editText_number)
    EditText text_number;

    @Bind(R.id.webView)
    WebView webView;

    HrsbService hrsbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ButterKnife.bind(MainActivity.this);

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        List<SchoolData> schoolRecords = SchoolData.listAll(SchoolData.class);
        if (schoolRecords != null) {
            for (int i = 0; i < schoolRecords.size(); i++) {
                SchoolData ss = (SchoolData) schoolRecords.get(i);
                System.out.println(ss.getNumber());
                System.out.println(ss.getName());
            }
        }
    }

    @OnClick(R.id.button_submit)
    void queryData() {
        Log.d(TAG, "begin to query data");
        webView.loadData("", "text/html; charset=UTF-8", null);
        //begin to query, hide the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        int i_number = 1000;
        String s_name = "";

        if (text_number.getText().toString() != null && text_query.getText().toString()!=null) {
            i_number = Integer.parseInt(text_number.getText().toString());
            s_name = text_query.getText().toString();
            hrsbService = new HrsbService(i_number, s_name);
            hrsbService.getSchoolData();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    public void onEvent(GetSchoolDataEvent event) {
        Log.d(TAG, "event.hasData is" + event.hasData);
        if (event.hasData == true) {

            webView.loadData(event.webData, "text/html; charset=UTF-8", null);
        } else {
            webView.loadData("No data, please try other address.", "text/html; charset=UTF-8", null);
            Log.d(TAG, "no data");
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //handle config change here
        } else {
            //handle config change here
        }


    }

}
