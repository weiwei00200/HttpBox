package com.sammie.httpbox.frames;

import android.content.Context;

import com.sammie.httpbox.IHttpCallBack;
import com.sammie.httpbox.IHttpRequest;

import org.xutils.x;

import java.util.Map;

public class XUtilRequest implements IHttpRequest {


    public XUtilRequest(){

    }

    @Override
    public <T> void post(Context context, Map<String, Object> params, String url, boolean cache, IHttpCallBack<T> callback) {

    }

    @Override
    public <T> void get(Context context, Map<String, Object> params, String url, boolean cache, IHttpCallBack<T> callback) {

    }
}
