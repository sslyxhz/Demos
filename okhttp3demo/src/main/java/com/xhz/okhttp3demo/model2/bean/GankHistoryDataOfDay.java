package com.xhz.okhttp3demo.model2.bean;

/**
 * Created by xh.zeng on 2016/11/27.
 */

public class GankHistoryDataOfDay {

    /**
     * _id : 5732b15067765974f885c05a
     * content : ......
     * publishedAt : 2016-05-11T12:11:00.0Z
     * title : 秒拍牛人大集合，看到哪个你跪了
     */

    private String _id;
    private String content;
    private String publishedAt;
    private String title;

    @Override
    public String toString() {
        return "GankHistoryDataOfDay{" +
                "_id=" + _id +
                ", content='" + content +
                ", publishedAt=" + publishedAt +
                ", title=" + title +
                "}\n";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
