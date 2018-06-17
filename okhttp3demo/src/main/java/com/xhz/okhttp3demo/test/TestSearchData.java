package com.xhz.okhttp3demo.test;

import android.util.Log;

import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.model2.GankResult;
import com.xhz.okhttp3demo.model2.bean.GankSearchData;

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

public class TestSearchData {
    public static final String TAG = TestSearchData.class.getSimpleName();

    private GankService mGankService;

    public TestSearchData(GankService gankService){
        mGankService = gankService;
    }

    public void getSearchData(String type, int count, int page){
        Call<ResponseBody> call = mGankService.getDataSearch(type, count ,page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        Log.v(TAG,"getSearchData-onResponse, Thread id="+Thread.currentThread().getId());
                        final String result = response.body().string();
                        Log.v(TAG, "getSearchData-onResponse:"+result);
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Log.v(TAG,"getSearchData-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getSearchData2(String type, int count, int page){
        Call<GankResult<GankSearchData>> call = mGankService.getDataSearch2(type, count ,page);
        call.enqueue(new Callback<GankResult<GankSearchData>>() {
            @Override
            public void onResponse(Call<GankResult<GankSearchData>> call, Response<GankResult<GankSearchData>> response) {
                if (response.isSuccessful()){
                    Log.v(TAG,"getSearchData2-onResponse, Thread id="+Thread.currentThread().getId());
                    final String result = response.body().toString();
                    Log.v(TAG, "getSearchData2-onResponse:"+result);
                }
                else{
                    Log.v(TAG,"getSearchData2-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<GankResult<GankSearchData>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getSearchData3(String type, int count, int page){
        mGankService.getDataSearch3(type, count ,page)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GankResult<GankSearchData>>(){

                    @Override
                    public void onNext(GankResult<GankSearchData> gankResult) {
                        Log.v(TAG,"getSearchData3-onNext, Thread id="+Thread.currentThread().getId());
                        Log.v(TAG, "getSearchData3, response:"+gankResult.toString());
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(TAG,"getSearchData3-onCompleted, Thread id="+Thread.currentThread().getId());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG,"getSearchData3-onError, Thread id="+Thread.currentThread().getId());
                        e.printStackTrace();
                    }
                });
    }
}
