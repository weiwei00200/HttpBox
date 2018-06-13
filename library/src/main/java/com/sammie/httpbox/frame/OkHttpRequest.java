package com.sammie.httpbox.frame;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sammie.httpbox.model.HttpConfig;
import com.sammie.httpbox.model.IHttpCallBack;
import com.sammie.httpbox.model.IHttpDownloadCallBack;
import com.sammie.httpbox.model.IHttpRequest;
import com.sammie.httpbox.model.IHttpUploadCallBack;
import com.sammie.httpbox.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Override
    public void download(String url, String savePath, String fileName, final IHttpDownloadCallBack callBack) {

        final File file = new File(savePath, fileName);
        //先不要检测，这里还有点问题，还没下载完成的时候也会存在这个文件
//        if (file.exists()) {
//            callBack.onFinish(file.getPath());
//            return;
//        }
        final Request request = new Request.Builder().url(url).build();
        final Call call = mOkkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.downloadFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onStartDownload();
                    }
                });
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        final int percent = (int) ((float) current / total * 100);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.process(percent);
                            }
                        });
                    }
                    fos.flush();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFinish(file.getPath());
                        }
                    });
                } catch (IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.downloadFailed();
                        }
                    });
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void upload(String url, String localPath, Map<String, Object> params, IHttpUploadCallBack callBack) {

    }
}
