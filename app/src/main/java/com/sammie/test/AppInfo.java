package com.sammie.test;

import android.app.Application;

import com.sammie.httpbox.HttpUtils;
import com.sammie.httpbox.frames.OkHttpRequest;
import com.sammie.httpbox.frames.XUtilRequest;

public class AppInfo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtils.initHttpRequest(new OkHttpRequest());//使用OkHttp
//        HttpUtils.initHttpRequest(new XUtilRequest());//使用XUtil
    }
}
