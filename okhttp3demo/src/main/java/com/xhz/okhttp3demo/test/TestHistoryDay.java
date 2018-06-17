package com.xhz.okhttp3demo.test;

import android.util.Log;

import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.model2.GankResult;

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

public class TestHistoryDay {
    public static final String TAG = TestHistoryDay.class.getSimpleName();

    private GankService mGankService;

    public TestHistoryDay(GankService gankService){
        mGankService = gankService;
    }

    public void getHistoryDay(){
        Call<ResponseBody> call = mGankService.getDayHistory();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        Log.v(TAG,"getHistoryDay-onResponse, Thread id="+Thread.currentThread().getId());
                        final String result = response.body().string();
                        Log.v(TAG, "getHistoryDay-onResponse:"+result);
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Log.v(TAG,"getHistoryDay-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getHistoryDay2(){
        Call<GankResult<String>> call = mGankService.getDayHistory2();
        call.enqueue(new Callback<GankResult<String>>() {
            @Override
            public void onResponse(Call<GankResult<String>> call, Response<GankResult<String>> response) {
                if (response.isSuccessful()){
                    Log.v(TAG,"getHistoryDay2-onResponse, Thread id="+Thread.currentThread().getId());
                    final String result = response.body().toString();
                    Log.v(TAG, "getHistoryDay2-onResponse:"+result);
                }
                else{
                    Log.v(TAG,"getHistoryDay2-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<GankResult<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getHistoryDay3(){
        mGankService.getDayHistory3()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GankResult<String>>(){

                    @Override
                    public void onNext(GankResult<String> gankResult) {
                        Log.v(TAG,"getHistoryDay3-onNext, Thread id="+Thread.currentThread().getId());
                        Log.v(TAG, "getHistoryDay3, response:"+gankResult.toString());
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(TAG,"getHistoryDay3-onCompleted, Thread id="+Thread.currentThread().getId());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG,"getHistoryDay3-onError, Thread id="+Thread.currentThread().getId());
                        e.printStackTrace();
                    }
                });
    }
}
