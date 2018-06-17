package com.xhz.okhttp3demo.api;

import com.xhz.okhttp3demo.model.GankData;
import com.xhz.okhttp3demo.model.PrettyGirlData;
import com.xhz.okhttp3demo.model.RelaxVideoData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xh.zeng on 2016/11/25.
 */

public interface RxGankService {

    public static enum GankCategory {
        福利,
        iOS,
        Android,
        App,
        拓展资源,
        瞎推荐,
        休息视频;
    }

    @GET("/data/福利/{pagesize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("/data/休息视频/{pagesize}/{page}")
    Observable<RelaxVideoData> getRelaxVideoData(@Path("pagesize") int pagesize, @Path("page")int page);

    @GET("/day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year")int year, @Path("month")int month, @Path("day")int day);
}
