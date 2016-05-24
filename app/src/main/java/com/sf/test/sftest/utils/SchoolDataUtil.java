package com.sf.test.sftest.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by dw on 2016-05-21.
 */
public class SchoolDataUtil {
    private final String TAG = getClass().getSimpleName();

    //query streetNumber's data in webContent
    public String getSchoolData(int streetNumber, String webContent) {
        String ret = "";
        Document document = Jsoup.parse(webContent);
        Elements evens = document.select("ul.even li");
        HashMap queryMap = new HashMap();
        for (Element even : evens) {
            String number_range = even.select("a").text();
            String url = even.select("a").attr("href").toString();
            Log.d(TAG, number_range);
            Log.d(TAG, url);
            HashMap map = getEachNumberArrangeMap(number_range, url);
            queryMap.putAll(map);
        }
        Log.d(TAG, "even queryMap size is:" + queryMap.size());
        evens = document.select("ul.odd li");
        for (Element even : evens) {
            String number_range = even.select("a").text();
            String url = even.select("a").attr("href").toString();
            url = url;
            System.out.println(number_range);
            System.out.println(url);
            HashMap map = getEachNumberArrangeMap(number_range, url);
            queryMap.putAll(map);
        }
        //queryMap is all data of even and odd streetnumber
        //begin to query streetNumber's data
        String schoolsUrl = (String) queryMap.get(streetNumber);
        Log.d(TAG, "schoolsUrl:" + schoolsUrl);
        if (schoolsUrl != null && schoolsUrl.isEmpty() == false && schoolsUrl.length() > 5) {
            try {
                document = Jsoup.connect("http://mybaragar.com/" + schoolsUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements = document.select("div.schoolList ul");
            if (elements != null && elements.size() > 0) {
                String schoolsInfo = document.select("div.schoolList ul").first().html();
                //System.out.println(schoolsInfo);
                return schoolsInfo;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    //according to streetNumber range, getting the url of school
    private HashMap getEachNumberArrangeMap(String number_range, String url) {
        Log.d(TAG, "number_range is:" + number_range);
        HashMap map = new HashMap();
        if (number_range.isEmpty()) return map;
        if (number_range.contains("-") == false) return map;
        String[] twoNumbers = number_range.split("-");
        if (twoNumbers[0].isEmpty()) return map;
        if (twoNumbers[1].isEmpty()) return map;

        String beginNumber = twoNumbers[0].trim();
        String endNumber = twoNumbers[1].trim();
        int begin = 0;
        int end = 0;
        try {
            begin = Integer.parseInt(beginNumber);
            end = Integer.parseInt(endNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }

        if (begin == end) {
            map.put(begin, url);

        } else if (begin > end) {
            map.put(begin, url);
            map.put(end, url);

        } else {
            for (int i = begin; i <= end; i++) {
                map.put(i, url);
            }
        }

        return map;
    }
}
