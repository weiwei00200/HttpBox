package com.sammie.test;

import android.app.Application;

import com.sammie.httpbox.HttpUtils;
import com.sammie.httpbox.frame.OkHttpRequest;
import com.sammie.httpbox.frame.XUtilRequest;
import com.sammie.httpbox.model.HttpConfig;

public class AppInfo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String baseUrl = "https://testapi.xhuijia.com/";

        HttpUtils.initHttpRequest(new OkHttpRequest(
                new HttpConfig.HttpConfigBuilder()
                        .baseUrl(baseUrl)
                        .timeout(15000)
                        .retryOnFail(true)
                        .builder()));//使用OkHttp
//        HttpUtils.initHttpRequest(new XUtilRequest(this,
//                new HttpConfig.HttpConfigBuilder()
//                        .baseUrl(baseUrl)
//                        .timeout(15000)
//                        .retryOnFail(true)
//                        .builder()));//使用XUtil
    }
}
