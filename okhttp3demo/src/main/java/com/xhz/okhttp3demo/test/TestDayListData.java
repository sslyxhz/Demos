package com.xhz.okhttp3demo.test;

import android.util.Log;

import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.model2.GankResultOfDay;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by xh.zeng on 2016/11/29.
 */

public class TestDayListData {
    public static final String TAG = TestDayListData.class.getSimpleName();

    private GankService mGankService;

    public TestDayListData(GankService gankService){
        mGankService = gankService;
    }

    public void getDayData(int year, int month, int day){
        Call<ResponseBody> call = mGankService.getDataOfDay(year, month, day);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        Log.v(TAG,"getDayData-onResponse, Thread id="+Thread.currentThread().getId());
                        final String result = response.body().string();
                        Log.v(TAG, "getDayData-onResponse:"+result);
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Log.v(TAG,"getDayData-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getDayData2(int year, int month, int day){
        Call<GankResultOfDay> call = mGankService.getDataOfDay2(year, month, day);
        call.enqueue(new Callback<GankResultOfDay>() {
            @Override
            public void onResponse(Call<GankResultOfDay> call, Response<GankResultOfDay> response) {
                if (response.isSuccessful()){
                    Log.v(TAG,"getDayData2-onResponse, Thread id="+Thread.currentThread().getId());
                    final String result = response.body().toString();
                    Log.v(TAG, "getDayData2-onResponse:"+result);
                }
                else{
                    Log.v(TAG,"getDayData2-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<GankResultOfDay> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getDayData3(int year, int month, int day){
        mGankService.getDataOfDay3(year, month, day)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GankResultOfDay>(){

                    @Override
                    public void onNext(GankResultOfDay gankResult) {
                        Log.v(TAG,"getDayData3-onNext, Thread id="+Thread.currentThread().getId());
                        Log.v(TAG, "getDayData3, response:"+gankResult.toString());
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(TAG,"getDayData3-onCompleted, Thread id="+Thread.currentThread().getId());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG,"getDayData3-onError, Thread id="+Thread.currentThread().getId());
                        e.printStackTrace();
                    }
                });
    }
}
