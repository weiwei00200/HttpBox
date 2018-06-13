package com.sammie.httpbox.frame;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.sammie.httpbox.model.HttpConfig;
import com.sammie.httpbox.model.IHttpCallBack;
import com.sammie.httpbox.model.IHttpRequest;
import com.sammie.httpbox.util.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

public class XUtilRequest implements IHttpRequest {
    private HttpConfig mHttpConfig;

    public XUtilRequest(Application application, HttpConfig httpConfig) {
        mHttpConfig = httpConfig;
        x.Ext.init(application);
    }

    private void setTimeout(RequestParams requestParams) {
        requestParams.setConnectTimeout(mHttpConfig.getTimeout());
        requestParams.setReadTimeout(mHttpConfig.getTimeout());
    }

    private void setCache(RequestParams requestParams) {
        requestParams.setCacheMaxAge(mHttpConfig.getCacheTime() * 1000);
    }

    @Override
    public <T> void get(String url, Map<String, Object> params, final Class<T> resClass, boolean cache, final IHttpCallBack<T> callback) {
        try {
            RequestParams requestParams = new RequestParams(Utils.getFullUrl(mHttpConfig.getBaseUrl(),url));
            setTimeout(requestParams);
            setCache(requestParams);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                requestParams.addQueryStringParameter(entry.getKey(), entry.getValue().toString());
            }

            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        callback.onSuccess(JSONObject.parseObject(result, resClass));
                    } else {
                        callback.onFailed("HttpBox--response empty json.");
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.onFailed(ex.getMessage());
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    callback.onComplete();
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
            RequestParams requestParams = new RequestParams(Utils.getFullUrl(mHttpConfig.getBaseUrl(),url));
            setTimeout(requestParams);
            setCache(requestParams);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                requestParams.addQueryStringParameter(entry.getKey(), entry.getValue().toString());
            }
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        callback.onSuccess(JSONObject.parseObject(result, resClass));
                    } else {
                        callback.onFailed("HttpBox--response empty json.");
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.onFailed(ex.getMessage());
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    callback.onComplete();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailed("Request failed.");
            callback.onComplete();
        }
    }
}
