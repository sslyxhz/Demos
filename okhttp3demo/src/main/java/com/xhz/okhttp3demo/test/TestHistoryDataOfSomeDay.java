package com.xhz.okhttp3demo.test;

import android.util.Log;

import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.model2.GankResult;
import com.xhz.okhttp3demo.model2.bean.GankHistoryDataOfSomeDay;

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

public class TestHistoryDataOfSomeDay {
    public static final String TAG = TestHistoryDataOfSomeDay.class.getSimpleName();

    private GankService mGankService;

    public TestHistoryDataOfSomeDay(GankService gankService){
        mGankService = gankService;
    }

    public void getHistoryDataOfSomeDay(int pagesize, int page){
        Call<ResponseBody> call = mGankService.getHistoryDataOfSomeDay(pagesize,page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        Log.v(TAG,"getHistoryDataOfSomeDay-onResponse, Thread id="+Thread.currentThread().getId());
                        final String result = response.body().string();
                        Log.v(TAG, "getHistoryDataOfSomeDay-onResponse:"+result);
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Log.v(TAG,"getHistoryDataOfSomeDay-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getHistoryDataOfSomeDay2(int pagesize, int page){
        Call<GankResult<GankHistoryDataOfSomeDay>> call = mGankService.getHistoryDataOfSomeDay2(pagesize,page);
        call.enqueue(new Callback<GankResult<GankHistoryDataOfSomeDay>>() {
            @Override
            public void onResponse(Call<GankResult<GankHistoryDataOfSomeDay>> call, Response<GankResult<GankHistoryDataOfSomeDay>> response) {
                if (response.isSuccessful()){
                    Log.v(TAG,"getHistoryDataOfSomeDay2-onResponse, Thread id="+Thread.currentThread().getId());
                    final String result = response.body().toString();
                    Log.v(TAG, "getHistoryDataOfSomeDay2-onResponse:"+result);
                }
                else{
                    Log.v(TAG,"getHistoryDataOfSomeDay2-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<GankResult<GankHistoryDataOfSomeDay>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getHistoryDataOfSomeDay3(int pagesize, int page){
        mGankService.getHistoryDataOfSomeDay3(pagesize,page)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GankResult<GankHistoryDataOfSomeDay>>(){

                    @Override
                    public void onNext(GankResult<GankHistoryDataOfSomeDay> gankResult) {
                        Log.v(TAG,"getHistoryDataOfSomeDay3-onNext, Thread id="+Thread.currentThread().getId());
                        Log.v(TAG, "getHistoryDataOfSomeDay3, response:"+gankResult.toString());
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(TAG,"getHistoryDataOfSomeDay3-onCompleted, Thread id="+Thread.currentThread().getId());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG,"getHistoryDataOfSomeDay3-onError, Thread id="+Thread.currentThread().getId());
                        e.printStackTrace();
                    }
                });
    }
}
