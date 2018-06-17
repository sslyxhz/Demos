package com.xhz.okhttp3demo.model;

import com.google.gson.annotations.SerializedName;
import com.xhz.okhttp3demo.model.bean.Gank;

import java.util.List;

/**
 * Created by xh.zeng on 2016/11/25.
 */

public class GankData extends BaseData {
    public List<String> category;
    public Result results;

    public class Result {
        @SerializedName("Android") public List<Gank> androidList;
        @SerializedName("休息视频") public List<Gank> relaxVideoList;
        @SerializedName("iOS") public List<Gank> iOSList;
        @SerializedName("福利") public List<Gank> meizhiList;
        @SerializedName("拓展资源") public List<Gank> expendResourceList;
        @SerializedName("瞎推荐") public List<Gank> recommandList;
        @SerializedName("App") public List<Gank> appList;
    }
}
