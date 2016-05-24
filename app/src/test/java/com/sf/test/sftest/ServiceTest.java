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


    @Test
    public void getSchoolData() throws Exception {



        HrsbService hrsbService = new HrsbService(1333,"south park");
        hrsbService.getSchoolData();
        ShadowApplication.runBackgroundTasks();
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
        SugarContext.init(context);
        List<SchoolData> school= SchoolData.listAll(SchoolData.class);
        for(int i=0;i<school.size();i++){
            SchoolData ss=(SchoolData)school.get(i);
            System.out.println(ss.getNumber());
            System.out.println(ss.getWebdata());
        }
    }
    @org.junit.Test
    public void addition_isCorrect() throws Exception {

        Context context = RuntimeEnvironment.application.getApplicationContext();
        SugarContext.init(context);
        AssetManager mgr=context.getAssets();

        String webContent=readAsset(mgr,"test_school_data.html");
        SchoolDataUtil schoolDataUtil = new SchoolDataUtil();

        String schoolInformation= schoolDataUtil.getSchoolData(1333,webContent);



        List<SchoolData> schoolRecords = SchoolData.listAll(SchoolData.class);
        for(int i=0;i<schoolRecords.size();i++){
            SchoolData ss=(SchoolData)schoolRecords.get(i);
            System.out.println(ss.getNumber());
            System.out.println(ss.getWebdata());
        }
        System.out.println(schoolRecords.size());
        String queryNumber ="1333";
        String queryStreetName="south park";
        List<SchoolData> school= SchoolData.find(SchoolData.class,"number = ? and name =?",queryNumber,queryStreetName );
        for(int i=0;i<school.size();i++){
            SchoolData ss=(SchoolData)school.get(i);
            System.out.println(ss.getNumber());
            System.out.println(ss.getWebdata());
        }
        /*
        Context context = RuntimeEnvironment.application.getApplicationContext();
        AssetManager mgr=context.getAssets();
        TestReadFiles ff=new TestReadFiles();
        String webContent=ff.readAsset(mgr,"n11.html");
        SchoolDataUtil schoolDataUtil = new SchoolDataUtil();

        String schoolsUrl= schoolDataUtil.querySchoolsUrlByNumber(1333,webContent);

        System.out.println("schoolsUrl=  "+schoolsUrl);

        String schoolInformation=schoolDataUtil.getSchoolInformation(schoolsUrl);

        */
        assertEquals(4, 2 + 2);
    }

}