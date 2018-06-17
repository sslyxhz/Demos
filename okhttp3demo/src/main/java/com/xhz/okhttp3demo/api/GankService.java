package com.xhz.okhttp3demo.api;

import com.xhz.okhttp3demo.model2.GankResultOfDay;
import com.xhz.okhttp3demo.model2.GankResult;
import com.xhz.okhttp3demo.model2.bean.GankHistoryDataOfDay;
import com.xhz.okhttp3demo.model2.bean.GankHistoryDataOfSomeDay;
import com.xhz.okhttp3demo.model2.bean.GankRandomData;
import com.xhz.okhttp3demo.model2.bean.GankSearchData;
import com.xhz.okhttp3demo.model2.bean.GankTypeData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * GankIO网络接口
 * Created by xh.zeng on 2016/11/25.
 */

public interface GankService {
    String HOST = "http://gank.io/api/";

    enum GankType {
        all,
        Android,
        iOS,
        休息视频,
        福利,
        拓展资源,
        瞎推荐,
        App;
    }

    /**
     * 搜索 API
     *   例：http://gank.io/api/search/query/listview/category/Android/count/10/page/1
     *   2016-6-12 日更新
     * @param type all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param count 最大50
     * @param page
     * @return count, error, results
                {
                "desc": "listview\u7684\u6298\u53e0\u6548\u679c",
                "ganhuo_id": "56cc6d1d421aa95caa7076fa",
                "publishedAt": "2015-07-17T03:43:22.395000",
                "readability": "......",
                "type": "Android",
                "url": "https://github.com/dodola/ListItemFold",
                "who": "Jason"
                }
     */
    @GET("search/query/listview/category/{type}/count/{count}/page/{page}")
    Call<ResponseBody> getDataSearch(@Path("type") String type,
                                    @Path("count") int count,
                                    @Path("page") int page);
    @GET("search/query/listview/category/{type}/count/{count}/page/{page}")
    Call<GankResult<GankSearchData>> getDataSearch2(@Path("type") String type,
                                                    @Path("count") int count,
                                                    @Path("page") int page);
    @GET("search/query/listview/category/{type}/count/{count}/page/{page}")
    Observable<GankResult<GankSearchData>> getDataSearch3(@Path("type") String type,
                                                    @Path("count") int count,
                                                    @Path("page") int page);


    /**
     * 获取特定日期网站数据
     *   例：http://gank.io/api/history/content/day/2016/05/11
     *   2016-5-11 日更新
     * @param year
     * @param month
     * @param day
     * @return error, results
                {
                "_id": "5732b15067765974f885c05a",
                "content": "......",
                "publishedAt": "2016-05-11T12:11:00.0Z",
                "title": "\u79d2\u62cd\u725b\u4eba\u5927\u96c6\u5408\uff0c\u770b\u5230\u54ea\u4e2a\u4f60\u8dea\u4e86"
                }
     */
    @GET("history/content/day/{year}/{month}/{day}")
    Call<ResponseBody> getHistoryDataOfDay(@Path("year") int year,
                                           @Path("month") int month,
                                           @Path("day") int day);
    @GET("history/content/day/{year}/{month}/{day}")
    Call<GankResult<GankHistoryDataOfDay>> getHistoryDataOfDay2(@Path("year") int year,
                                                                @Path("month") int month,
                                                                @Path("day") int day);
    @GET("history/content/day/{year}/{month}/{day}")
    Observable<GankResult<GankHistoryDataOfDay>> getHistoryDataOfDay3(@Path("year") int year,
                                                                      @Path("month") int month,
                                                                      @Path("day") int day);


    /**
     * 获取某几日干货网站数据
     *   例：http://gank.io/api/history/content/2/1
     *   2016-5-11 日更新
     * @param pagesize
     * @param page
     * @return error, results
                {
                "_id": "5837affa421aa91caf16ffb5",
                "content": "......",
                "created_at": "2016-11-25T11:28:58.530Z",
                "publishedAt": "2016-11-25T11:26:00.0Z",
                "rand_id": "46f50ccb-4c65-4203-af60-70f0438be810",
                "title": "......",
                "updated_at": "2016-11-25T11:28:58.530Z"
                }
     */
    @GET("history/content/{pagesize}/{page}")
    Call<ResponseBody> getHistoryDataOfSomeDay(@Path("pagesize") int pagesize,
                                               @Path("page") int page);
    @GET("history/content/{pagesize}/{page}")
    Call<GankResult<GankHistoryDataOfSomeDay>> getHistoryDataOfSomeDay2(@Path("pagesize") int pagesize,
                                                                        @Path("page") int page);
    @GET("history/content/{pagesize}/{page}")
    Observable<GankResult<GankHistoryDataOfSomeDay>> getHistoryDataOfSomeDay3(@Path("pagesize") int pagesize,
                                                                              @Path("page") int page);


    /**
     * 获取发过干货日期接口
     *   例：http://gank.io/api/day/history
     *   2016-2-28 日更新
     * @return error, results
                    "2016-11-24",
                    "2016-11-23",
                     ...
     */
    @GET("day/history")
    Call<ResponseBody> getDayHistory();
    @GET("day/history")
    Call<GankResult<String>> getDayHistory2();
    @GET("day/history")
    Observable<GankResult<String>> getDayHistory3();


    /**
     * 分类数据
     *   例: http://gank.io/api/data/Android/10/1
     * @param type 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param pagesize 请求个数： 数字，大于0
     * @param page 第几页：数字，大于0
     * @return error, results
                {
                "_id": "5836a7fc421aa91cb7afe7e0",
                "createdAt": "2016-11-24T16:42:36.919Z",
                "desc": "\u652f\u6301https\u7684ijkplayer\u64ad\u653e\u5668",
                "images": [
                "http://img.gank.io/22aa7a50-de1f-4697-8eb8-7bcc247cce58"
                ],
                "publishedAt": "2016-11-25T11:29:49.832Z",
                "source": "web",
                "type": "Android",
                "url": "https://github.com/l123456789jy/ijkplayer",
                "used": true,
                "who": "Lazy"
                }
     */
    @GET("data/{type}/{pagesize}/{page}")
    Call<ResponseBody> getDataOfType(@Path("type") String type,
                                         @Path("pagesize") int pagesize,
                                         @Path("page") int page);
    @GET("data/{type}/{pagesize}/{page}")
    Call<GankResult<GankTypeData>> getDataOfType2(@Path("type") String type,
                                                  @Path("pagesize") int pagesize,
                                                  @Path("page") int page);
    @GET("data/{type}/{pagesize}/{page}")
    Observable<GankResult<GankTypeData>> getDataOfType3(@Path("type") String type,
                                                        @Path("pagesize") int pagesize,
                                                        @Path("page") int page);


    /**
     * 每日数据
     *   例: http://gank.io/api/day/2015/08/06
     * @param year
     * @param month
     * @param day
     * @return category, error, results
                "Android": [
                {
                "_id": "56cc6d23421aa95caa707c6a",
                "createdAt": "2015-08-06T02:07:23.290Z",
                "desc": "\u4e00\u4e2a\u5c0f\u6e05\u65b0spinner",
                "publishedAt": "2015-08-06T04:16:55.582Z",
                "type": "Android",
                "url": "https://github.com/arcadefire/nice-spinner",
                "used": true,
                "who": "\u6709\u65f6\u653e\u7eb5"
                }
                ]
     */
    @GET("day/{year}/{month}/{day}")
    Call<ResponseBody> getDataOfDay(@Path("year") int year,
                                    @Path("month") int month,
                                    @Path("day") int day);
    @GET("day/{year}/{month}/{day}")
    Call<GankResultOfDay> getDataOfDay2(@Path("year") int year,
                                                         @Path("month") int month,
                                                         @Path("day") int day);
    @GET("day/{year}/{month}/{day}")
    Observable<GankResultOfDay> getDataOfDay3(@Path("year") int year,
                                                          @Path("month") int month,
                                                          @Path("day") int day);


    /**
     * 随机数据
     *   例：http://gank.io/api/random/data/Android/20
     * @param type 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
     * @param count 个数： 数字，大于0
     * @return error, results
                {
                "_id": "56cc6d1d421aa95caa7075f7",
                "createdAt": "2015-06-01T16:24:28.534Z",
                "desc": "\u8ba9\u4f60\u7684\u4efb\u4f55View\u70b9\u51fb\u90fd\u5e26\u6c34\u6ce2\u7eb9\u6548\u679c",
                "publishedAt": "2015-06-02T03:44:53.696Z",
                "type": "Android",
                "url": "https://github.com/balysv/material-ripple",
                "used": true,
                "who": "Jason"
                }
     */
    @GET("random/data/{type}/{count}")
    Call<ResponseBody> getDataOfRandom(@Path("type") String type,
                                       @Path("count") int count);
    @GET("random/data/{type}/{count}")
    Call<GankResult<GankRandomData>> getDataOfRandom2(@Path("type") String type,
                                                      @Path("count") int count);
    @GET("random/data/{type}/{count}")
    Observable<GankResult<GankRandomData>> getDataOfRandom3(@Path("type") String type,
                                                            @Path("count") int count);

    // for test
    @GET("random/data/{type}/{count}")
    Call<String> getDataOfRandom1(@Path("type") String type,
                                       @Path("count") int count);


}
