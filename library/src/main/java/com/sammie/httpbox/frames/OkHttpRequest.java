package com.sammie.httpbox.frames;

import android.content.Context;

import com.sammie.httpbox.HttpCache;
import com.sammie.httpbox.IHttpCallBack;
import com.sammie.httpbox.IHttpRequest;

import java.util.Map;

import okhttp3.OkHttpClient;

public class OkHttpRequest implements IHttpRequest {

    private OkHttpClient okHttpClient;
    private HttpCache mHttpCache;

    public OkHttpRequest(){
        okHttpClient = new OkHttpClient();
        mHttpCache = new HttpCache();
    }


    @Override
    public <T> void post(Context context, Map<String, Object> params, String url, boolean cache, IHttpCallBack<T> callback) {

    }

    @Override
    public <T> void get(Context context, Map<String, Object> params, String url, boolean cache, IHttpCallBack<T> callback) {

    }
}
