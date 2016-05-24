package com.sf.test.sftest;

import android.content.Context;
import android.content.res.AssetManager;

import com.orm.SugarContext;
import com.sf.test.sftest.service.HrsbService;
import com.sf.test.sftest.utils.SchoolData;
import com.sf.test.sftest.utils.SchoolDataUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import static org.junit.Assert.*;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ServiceTest {

    @Before
    public void init() {
        ShadowLog.stream = System.out;
    }


    public  String readAsset(AssetManager mgr, String path) {
        String contents = "";
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = mgr.open(path);
            reader = new BufferedReader(new InputStreamReader(is));
            contents = reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                contents += '\n' + line;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return contents;
    }

    @Test
    public void getDataFromLocalDB() throws Exception {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        AssetManager mgr=context.getAssets();
        String webContent=readAsset(mgr,"test_school_data.html");
        SchoolDataUtil schoolDataUtil = new SchoolDataUtil();

        String schoolInformation= schoolDataUtil.getSchoolData(1102,webContent);
        System.out.println("schoolInformation="+schoolInformation);
        SchoolData schoolData=new SchoolData("1102","south park",schoolInformation);
        schoolData.save();
        List<SchoolData> school= SchoolData.find(SchoolData.class,"number = ? and name =?","1102","south park" );
        for(int i=0;i<school.size();i++){
            SchoolData ss=(SchoolData)school.get(i);
            System.out.println(ss.getNumber());
            System.out.println(ss.getWebdata());
            assertEquals("1102",ss.getNumber());
        }
    }


}