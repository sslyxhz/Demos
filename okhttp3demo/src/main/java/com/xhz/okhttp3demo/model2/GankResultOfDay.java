package com.xhz.okhttp3demo.model2;

import com.google.gson.annotations.SerializedName;
import com.xhz.okhttp3demo.model2.bean.GankDayData;

import java.util.List;

/**
 * Created by xh.zeng on 2016/11/29.
 */

public class GankResultOfDay{
    public List<String> category;
    public boolean error;
    public GankDayListData results;

    @Override
    public String toString() {
        return "GankResultOfDay{" +
                "category=" + category +
                "error=" + error +
                ", results='" + results +
                "}\n";
    }

    public class GankDayListData {

        @SerializedName("Android")
        private List<GankDayData> androidList;          // android
        @SerializedName("iOS")
        private List<GankDayData> iOSList;              // ios
        @SerializedName("休息视频")
        private List<GankDayData> relaxVideoList;       // 休息视频
        @SerializedName("拓展资源")
        private List<GankDayData> expandResourceList;   // 拓展资源
        @SerializedName("福利")
        private List<GankDayData> welfareList;          // 福利
        @SerializedName("瞎推荐")
        private List<GankDayData> recommendList;        // 瞎推荐
        @SerializedName("App")
        private List<GankDayData> appList;              // app

        @Override
        public String toString() {
            return "GankDayListData{" +
                    "androidList=" + androidList +
                    ", iOSList='" + iOSList +
                    ", relaxVideoList=" + relaxVideoList +
                    ", expandResourceList=" + expandResourceList +
                    ", welfareList=" + welfareList +
                    ", recommendList=" + recommendList +
                    ", appList=" + appList +
                    "}\n";
        }

        public List<GankDayData> getAndroidList() {
            return androidList;
        }

        public void setAndroidList(List<GankDayData> Android) {
            this.androidList = Android;
        }

        public List<GankDayData> getIOS() {
            return iOSList;
        }

        public void setIOS(List<GankDayData> iOS) {
            this.iOSList = iOS;
        }

        public List<GankDayData> getRelaxVideoList() {
            return relaxVideoList;
        }

        public void setRelaxVideoList(List<GankDayData> relaxVideoList) {
            this.relaxVideoList = relaxVideoList;
        }

        public List<GankDayData> getExpandResourceList() {
            return expandResourceList;
        }

        public void setExpandResourceList(List<GankDayData> expandResourceList) {
            this.expandResourceList = expandResourceList;
        }

        public List<GankDayData> getWelfareList() {
            return welfareList;
        }

        public void setWelfareList(List<GankDayData> welfareList) {
            this.welfareList = welfareList;
        }

        public List<GankDayData> getRecommendList() {
            return welfareList;
        }

        public void setRecommendList(List<GankDayData> recommendList) {
            this.recommendList = recommendList;
        }

        public List<GankDayData> getAppList() {
            return appList;
        }

        public void setAppList(List<GankDayData> appList) {
            this.appList = appList;
        }

    }
}
