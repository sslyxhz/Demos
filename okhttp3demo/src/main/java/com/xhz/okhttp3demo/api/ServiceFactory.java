package com.xhz.okhttp3demo.api;

/**
 * Created by xh.zeng on 2016/11/28.
 */

public class ServiceFactory {

    private static GankService mGankService;

    protected static final Object monitor = new Object();

    public static GankService getGankServiceInstance(){
        synchronized (monitor){
            if(mGankService == null){
                mGankService = new RetrofitManager().getGankService();
            }
            return mGankService;
        }
    }
}
