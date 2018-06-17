package com.xhz.okhttp3demo.model2;

import java.util.List;

/**
 * Created by xh.zeng on 2016/11/25.
 */

public class GankResult<T> {
    public boolean error;
    public List<T> results;

    @Override
    public String toString() {
        return "GankResult{" +
                "error=" + error +
                ", results='" + results +
                "}\n";
    }
}
