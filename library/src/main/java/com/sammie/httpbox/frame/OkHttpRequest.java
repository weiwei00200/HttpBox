package com.sammie.httpbox.frame;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sammie.httpbox.model.HttpConfig;
import com.sammie.httpbox.model.IHttpCallBack;
import com.sammie.httpbox.model.IHttpRequest;
import com.sammie.httpbox.util.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequest implements IHttpRequest {

    private OkHttpClient mOkkHttpClient;
    private HttpConfig mHttpConfig;
    private Handler mHandler;//OkHttp的异步回调方法不是在主线程执行的，所以需要通知UT

    public OkHttpRequest(HttpConfig httpConfig) {
        mHttpConfig = httpConfig;
        mOkkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(httpConfig.getTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(httpConfig.getTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(httpConfig.getTimeout(), TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(httpConfig.isRetryOnFail())
                .build();
        mHandler = new Handler();
    }

    @Override
    public <T> void get(String url, Map<String, Object> params, final Class<T> resClass, boolean cache, final IHttpCallBack<T> callback) {
        try {
            url = Utils.getFullUrl(mHttpConfig.getBaseUrl(), url);
            String paramStr = "";
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                paramStr += "&" + entry.getKey() + "=" + entry.getValue();
            }
            if (!TextUtils.isEmpty(paramStr)) {
                url += "?" + paramStr;
            }
            Request request = new Request.Builder().url(url).build();
            Call call = mOkkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailed(e.getMessage());
                            callback.onComplete();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (response.isSuccessful()) {
                                    String json = response.body().string();
                                    if (!TextUtils.isEmpty(json)) {
                                        callback.onSuccess(JSONObject.parseObject(json, resClass));
                                    } else {
                                        callback.onFailed("HttpBox--response empty json.");
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onComplete();
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailed("Request failed.");
            callback.onComplete();
        }
    }

    @Override
    public <T> void post(String url, Map<String, Object> params, final Class<T> resClass, boolean cache, final IHttpCallBack<T> callback) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
            RequestBody formBody = builder.build();
            Request request = new Request.Builder()
                    .url(Utils.getFullUrl(mHttpConfig.getBaseUrl(), url))
                    .post(formBody)
                    .build();
            Call call = mOkkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailed(e.getMessage());
                            callback.onComplete();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (response.isSuccessful()) {
                                    String json = response.body().string();
                                    if (!TextUtils.isEmpty(json)) {
                                        callback.onSuccess(JSONObject.parseObject(json, resClass));
                                    } else {
                                        callback.onFailed("HttpBox--response empty json.");
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            callback.onComplete();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailed("Request failed.");
            callback.onComplete();
        }
    }
}
