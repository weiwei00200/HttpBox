package com.sammie.httpbox.model;

import android.content.Context;

import java.util.Map;

public interface IHttpRequest {

    /**
     * get 提交
     *
     * @param params
     * @param url
     * @param cache
     * @param callback
     * @param <T>
     */
    <T> void get(String url, Map<String, Object> params, Class<T> resClass, final boolean cache, final IHttpCallBack<T> callback);

    /**
     * post 提交
     *
     * @param params
     * @param url
     * @param cache
     * @param callback
     * @param <T>
     */
    <T> void post(String url, Map<String, Object> params, Class<T> resClass, final boolean cache, final IHttpCallBack<T> callback);

}