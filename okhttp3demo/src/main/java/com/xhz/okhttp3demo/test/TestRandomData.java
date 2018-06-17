package com.xhz.okhttp3demo.test;

import android.util.Log;

import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.model2.GankResult;
import com.xhz.okhttp3demo.model2.bean.GankRandomData;

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

public class TestRandomData {
    public static final String TAG = TestRandomData.class.getSimpleName();

    private GankService mGankService;

    public TestRandomData(GankService gankService){
        mGankService = gankService;
    }

    public void getRandomData(String type, int count){
        Call<ResponseBody> call = mGankService.getDataOfRandom(type,count);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        Log.v(TAG,"getRandomData-onResponse, Thread id="+Thread.currentThread().getId());
                        final String result = response.body().string();
                        Log.v(TAG, "getRandomData-onResponse:"+result);
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Log.v(TAG,"getRandomData-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getRandomData2(String type, int count){
        Call<GankResult<GankRandomData>> call = mGankService.getDataOfRandom2(type,count);
        call.enqueue(new Callback<GankResult<GankRandomData>>() {
            @Override
            public void onResponse(Call<GankResult<GankRandomData>> call, Response<GankResult<GankRandomData>> response) {
                if (response.isSuccessful()){
                    Log.v(TAG,"getRandomData2-onResponse, Thread id="+Thread.currentThread().getId());
                    final String result = response.body().toString();
                    Log.v(TAG, "getRandomData2-onResponse:"+result);
                }
                else{
                    Log.v(TAG,"getRandomData2-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<GankResult<GankRandomData>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getRandomData3(String type, int count){
        mGankService.getDataOfRandom3(type,count)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GankResult<GankRandomData>>(){

                    @Override
                    public void onNext(GankResult<GankRandomData> gankResult) {
                        Log.v(TAG,"getRandomData3-onNext, Thread id="+Thread.currentThread().getId());
                        Log.v(TAG, "getRandomData3, response:"+gankResult.toString());
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(TAG,"getRandomData3-onCompleted, Thread id="+Thread.currentThread().getId());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG,"getRandomData3-onError, Thread id="+Thread.currentThread().getId());
                        e.printStackTrace();
                    }
                });
    }
}
