package com.sammie.httpbox;

import android.content.Context;

import java.util.Map;

public interface IHttpRequest {
    /**
     * post 提交
     *
     * @param context
     * @param params
     * @param url
     * @param cache
     * @param callback
     * @param <T>
     */
    <T> void post(Context context, Map<String, Object> params, String url, final boolean cache, final IHttpCallBack<T> callback);

    /**
     * get 提交
     *
     * @param context
     * @param params
     * @param url
     * @param cache
     * @param callback
     * @param <T>
     */
    <T> void get(Context context, Map<String, Object> params, String url, final boolean cache, final IHttpCallBack<T> callback);
}