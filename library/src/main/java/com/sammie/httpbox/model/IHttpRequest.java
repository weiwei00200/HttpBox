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
    <T> void post(String url, Map<String, Object> params, final Class<T> resClass, final boolean cache, final IHttpCallBack<T> callback);


    /**
     * 下载文件
     *
     * @param url
     * @param savePath
     * @param callBack
     */
    void download(String url, String savePath, String fileName, final IHttpDownloadCallBack callBack);


    /**
     * 上传文件
     *
     * @param url
     * @param localPath
     * @param callBack
     */
    void upload(String url, String localPath, Map<String, Object> params, final IHttpUploadCallBack callBack);
}