package com.xhz.okhttp3demo.test;

import android.util.Log;

import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.model2.GankResult;
import com.xhz.okhttp3demo.model2.bean.GankHistoryDataOfDay;

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

public class TestHistoryDataOfDay {
    public static final String TAG = TestHistoryDataOfDay.class.getSimpleName();

    private GankService mGankService;

    public TestHistoryDataOfDay(GankService gankService){
        mGankService = gankService;
    }

    public void getHistoryDataOfDay(int year, int month, int day){
        Call<ResponseBody> call = mGankService.getHistoryDataOfDay(year, month, day);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        Log.v(TAG,"getHistoryDataOfDay-onResponse, Thread id="+Thread.currentThread().getId());
                        final String result = response.body().string();
                        Log.v(TAG, "getHistoryDataOfDay-onResponse:"+result);
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Log.v(TAG,"getHistoryDataOfDay-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getHistoryDataOfDay2(int year, int month, int day){
        Call<GankResult<GankHistoryDataOfDay>> call = mGankService.getHistoryDataOfDay2(year, month, day);
        call.enqueue(new Callback<GankResult<GankHistoryDataOfDay>>() {
            @Override
            public void onResponse(Call<GankResult<GankHistoryDataOfDay>> call, Response<GankResult<GankHistoryDataOfDay>> response) {
                if (response.isSuccessful()){
                    Log.v(TAG,"getHistoryDataOfDay2-onResponse, Thread id="+Thread.currentThread().getId());
                    final String result = response.body().toString();
                    Log.v(TAG, "getHistoryDataOfDay2-onResponse:"+result);
                }
                else{
                    Log.v(TAG,"getHistoryDataOfDay2-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<GankResult<GankHistoryDataOfDay>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getHistoryDataOfDay3(int year, int month, int day){
        mGankService.getHistoryDataOfDay3(year, month, day)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GankResult<GankHistoryDataOfDay>>(){

                    @Override
                    public void onNext(GankResult<GankHistoryDataOfDay> gankResult) {
                        Log.v(TAG,"getHistoryDataOfDay3-onNext, Thread id="+Thread.currentThread().getId());
                        Log.v(TAG, "getHistoryDataOfDay3, response:"+gankResult.toString());
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(TAG,"getHistoryDataOfDay3-onCompleted, Thread id="+Thread.currentThread().getId());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG,"getHistoryDataOfDay3-onError, Thread id="+Thread.currentThread().getId());
                        e.printStackTrace();
                    }
                });
    }
}
