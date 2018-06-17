package com.xhz.okhttp3demo.test;

import android.util.Log;

import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.model2.GankResult;
import com.xhz.okhttp3demo.model2.bean.GankTypeData;

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

public class TestTypeData {
    public static final String TAG = TestTypeData.class.getSimpleName();

    private GankService mGankService;

    public TestTypeData(GankService gankService){
        mGankService = gankService;
    }

    public void getTypeData(String type, int pagesize, int page){
        Call<ResponseBody> call = mGankService.getDataOfType(type,pagesize,page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        Log.v(TAG,"getTypeData-onResponse, Thread id="+Thread.currentThread().getId());
                        final String result = response.body().string();
                        Log.v(TAG, "getTypeData-onResponse:"+result);
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Log.v(TAG,"getTypeData-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getTypeData2(String type, int pagesize, int page){
        Call<GankResult<GankTypeData>> call = mGankService.getDataOfType2(type,pagesize,page);
        call.enqueue(new Callback<GankResult<GankTypeData>>() {
            @Override
            public void onResponse(Call<GankResult<GankTypeData>> call, Response<GankResult<GankTypeData>> response) {
                if (response.isSuccessful()){
                    Log.v(TAG,"getTypeData2-onResponse, Thread id="+Thread.currentThread().getId());
                    final String result = response.body().toString();
                    Log.v(TAG, "getTypeData2-onResponse:"+result);
                }
                else{
                    Log.v(TAG,"getTypeData2-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<GankResult<GankTypeData>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getTypeData3(String type, int pagesize, int page){
        mGankService.getDataOfType3(type,pagesize,page)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GankResult<GankTypeData>>(){

                    @Override
                    public void onNext(GankResult<GankTypeData> gankResult) {
                        Log.v(TAG,"getTypeData3-onNext, Thread id="+Thread.currentThread().getId());
                        Log.v(TAG, "getTypeData3, response:"+gankResult.toString());
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(TAG,"getTypeData3-onCompleted, Thread id="+Thread.currentThread().getId());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG,"getTypeData3-onError, Thread id="+Thread.currentThread().getId());
                        e.printStackTrace();
                    }
                });
    }
}
