package com.xhz.okhttp3demo.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xh.zeng on 2016/11/28.
 */

public class RetrofitManager {

    private Retrofit mRetrofit;
    private GankService mGankService;

    final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").serializeNulls().create();

    public RetrofitManager(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(GankService.HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mGankService = mRetrofit.create(GankService.class);
    }

    public GankService getGankService(){
        return mGankService;
    }

}
