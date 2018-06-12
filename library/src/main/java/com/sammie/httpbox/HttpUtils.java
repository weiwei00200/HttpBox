package com.sammie.httpbox;

import android.content.Context;
import android.view.View;

/**
* Http请求入口
* @author ZhangWeijun
* @time 2018/6/12 14:04
*/
public class HttpUtils {

    // 这个可以在 application 中去初始化
    private static IHttpRequest mInitHttpRequest;
    private static HttpConfig mHttpConfig;

    public static void initHttpRequest(IHttpRequest httpRequest,HttpConfig httpConfig) {
        mInitHttpRequest = httpRequest;
        mHttpConfig = httpConfig;
    }


    // 省略部分代码 ......
    public <T> void execute(IHttpCallBack<T> callback) {
        // 如果没有指定，那么就用 application 中初始化的
//        mInitHttpRequest.get(mContext, mParams, mUrl, mCache, callback);
    }

}
