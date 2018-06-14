package com.sammie.httpbox.frame;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.sammie.httpbox.model.HttpConfig;
import com.sammie.httpbox.model.IHttpCallBack;
import com.sammie.httpbox.model.IHttpDownloadCallBack;
import com.sammie.httpbox.model.IHttpRequest;
import com.sammie.httpbox.model.IHttpUploadCallBack;
import com.sammie.httpbox.util.Utils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.app.RedirectHandler;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.io.File;
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
            RequestParams requestParams = new RequestParams(Utils.getFullUrl(mHttpConfig.getBaseUrl(), url));
            setTimeout(requestParams);
            setCache(requestParams);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                requestParams.addQueryStringParameter(entry.getKey(), entry.getValue().toString());
            }

            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        if (resClass == null) {
                            callback.onSuccess(null);
                        } else {
                            callback.onSuccess(JSONObject.parseObject(result, resClass));
                        }
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
            RequestParams requestParams = new RequestParams(Utils.getFullUrl(mHttpConfig.getBaseUrl(), url));
            setTimeout(requestParams);
            setCache(requestParams);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                requestParams.addQueryStringParameter(entry.getKey(), entry.getValue().toString());
            }
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        if (resClass == null) {
                            callback.onSuccess(null);
                        } else {
                            callback.onSuccess(JSONObject.parseObject(result, resClass));
                        }
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
    public void download(String url, final String savePath, final String fileName, final IHttpDownloadCallBack callBack) {
        try {
            RequestParams requestParams = new RequestParams(url);
            setTimeout(requestParams);
            requestParams.setAutoRename(true);
            requestParams.setSaveFilePath(savePath + File.separator + fileName);
            x.http().get(requestParams, new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(File file) {
                    callBack.onDownloadSuccessful(file.getPath());
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callBack.onDownloadFailed();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    callBack.onFinish();
                }

                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {
                    callBack.onStartDownload();
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    callBack.process((int) ((float) current / total * 100));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDownloadFailed();
            callBack.onFinish();
        }
    }

    @Override
    public void upload(String url, Map<String, Object> params, final IHttpUploadCallBack callBack) {
        try {
            RequestParams requestParams = new RequestParams(url);
            setTimeout(requestParams);
            requestParams.setMultipart(true);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof File) {
                    requestParams.addBodyParameter(entry.getKey(), (File) entry.getValue());
                } else if (entry.getValue() instanceof String) {
                    requestParams.addBodyParameter(entry.getKey(), entry.getValue().toString());
                }
            }
            x.http().get(requestParams, new Callback.CommonCallback<File>() {
                @Override
                public void onSuccess(File file) {
                    callBack.onUploadSuccessful();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callBack.onUploadFailed();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    callBack.onFinish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onUploadFailed();
            callBack.onFinish();
        }
    }

    @Override
    public void upload(String url, String localPath, final IHttpUploadCallBack callBack) {
        try {
            RequestParams requestParams = new RequestParams(url);
            setTimeout(requestParams);
            requestParams.setMultipart(true);
            requestParams.addBodyParameter("", new File(localPath));
            x.http().get(requestParams, new Callback.CommonCallback<File>() {
                @Override
                public void onSuccess(File file) {
                    callBack.onUploadSuccessful();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callBack.onUploadFailed();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    callBack.onFinish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onUploadFailed();
            callBack.onFinish();
        }
    }
}
