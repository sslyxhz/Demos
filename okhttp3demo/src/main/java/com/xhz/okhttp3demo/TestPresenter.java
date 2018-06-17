package com.xhz.okhttp3demo;

import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.api.ServiceFactory;
import com.xhz.okhttp3demo.test.TestDayListData;
import com.xhz.okhttp3demo.test.TestHistoryDataOfDay;
import com.xhz.okhttp3demo.test.TestHistoryDataOfSomeDay;
import com.xhz.okhttp3demo.test.TestHistoryDay;
import com.xhz.okhttp3demo.test.TestRandomData;
import com.xhz.okhttp3demo.test.TestSearchData;
import com.xhz.okhttp3demo.test.TestTypeData;

/**
 * Created by xh.zeng on 2016/11/28.
 */

public class TestPresenter {
    public static final String TAG = TestPresenter.class.getSimpleName();

    private GankService mGankService;

    public TestPresenter(){
        mGankService = ServiceFactory.getGankServiceInstance();
    }

    public void test() {
//        testSearchData("Android",5,1);
//        testHistoryDataOfDay(2016,11,11);
//        testHistoryDataOfSomeDay(5,1);
//        testGetHistoryDay();
//        testTypeData("Android",5,1);
        testDataOfDay(2016,11,11);
//        testDataOfRandom("Android",5);
    }

    public void testSearchData(String type, int count, int page){
        TestSearchData testSearchData = new TestSearchData(mGankService);
        testSearchData.getSearchData(type, count, page);
        testSearchData.getSearchData2(type, count, page);
        testSearchData.getSearchData3(type, count, page);
    }

    public void testHistoryDataOfDay(int year, int month, int day){
        TestHistoryDataOfDay testHistoryDataOfDay = new TestHistoryDataOfDay(mGankService);
        testHistoryDataOfDay.getHistoryDataOfDay(year, month, day);
        testHistoryDataOfDay.getHistoryDataOfDay2(year, month, day);
        testHistoryDataOfDay.getHistoryDataOfDay3(year, month, day);
    }

    public void testHistoryDataOfSomeDay(int pagesize, int page){
        TestHistoryDataOfSomeDay testHistoryDataOfSomeDay = new TestHistoryDataOfSomeDay(mGankService);
        testHistoryDataOfSomeDay.getHistoryDataOfSomeDay(pagesize,page);
        testHistoryDataOfSomeDay.getHistoryDataOfSomeDay2(pagesize,page);
        testHistoryDataOfSomeDay.getHistoryDataOfSomeDay3(pagesize,page);
    }

    public void testGetHistoryDay(){
        TestHistoryDay testHistoryDay = new TestHistoryDay(mGankService);
        testHistoryDay.getHistoryDay();
        testHistoryDay.getHistoryDay2();
        testHistoryDay.getHistoryDay3();
    }

    public void testTypeData(String type, int pagesize, int page){
        TestTypeData testTypeData = new TestTypeData(mGankService);
        testTypeData.getTypeData(type,pagesize,page);
        testTypeData.getTypeData2(type,pagesize,page);
        testTypeData.getTypeData3(type,pagesize,page);
    }

    public void testDataOfDay(int year, int month, int day){
        TestDayListData testDayListData = new TestDayListData(mGankService);
        testDayListData.getDayData(year, month, day);
        testDayListData.getDayData2(year, month, day);
        testDayListData.getDayData3(year, month, day);
    }

    public void testDataOfRandom(String type, int count){
        TestRandomData testRandomData = new TestRandomData(mGankService);
        testRandomData.getRandomData(type,count);
        testRandomData.getRandomData2(type,count);
        testRandomData.getRandomData3(type,count);
    }


}
