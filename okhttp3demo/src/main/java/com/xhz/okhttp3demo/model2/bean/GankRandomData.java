package com.xhz.okhttp3demo.model2.bean;

/**
 * Created by xh.zeng on 2016/11/27.
 */

public class GankRandomData {

    /**
     * _id : 56cc6d1d421aa95caa7075f7
     * createdAt : 2015-06-01T16:24:28.534Z
     * desc : 让你的任何View点击都带水波纹效果
     * publishedAt : 2015-06-02T03:44:53.696Z
     * type : Android
     * url : https://github.com/balysv/material-ripple
     * used : true
     * who : Jason
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;

    @Override
    public String toString() {
        return "GankRandomData{" +
                "_id=" + _id +
                ", createdAt='" + createdAt +
                ", desc=" + desc +
                ", publishedAt=" + publishedAt +
                ", type=" + type +
                ", url=" + url +
                ", used=" + used +
                ", who=" + who +
                "}\n";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
