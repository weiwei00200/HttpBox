package com.sammie.httpbox;

import java.util.HashMap;
import java.util.Map;

/**
* Http请求入口
* @author ZhangWeijun
* @time 2018/6/12 14:04
*/
public class HttpUtils {

    // 这个可以在 application 中去初始化
    private static IHttpRequest mInitHttpRequest;
    private static HttpConfig mHttpConfig;

    private int TypeGet = 0x0011, TypePost = 0x0022;
    private int mReqType;//请求类型
    private Map<String, Object> mParamMap;//请求参数
    private String url="";//请求URL

    public static void initHttpRequest(IHttpRequest httpRequest) {
        mInitHttpRequest = httpRequest;
    }

    public HttpUtils(){
        mParamMap = new HashMap<>();
    }

    public HttpUtils get(){
        mReqType = TypeGet;
        return this;
    }

    public HttpUtils param(String key , Object value){
        mParamMap.put(key, value);
        return this;
    }

    public HttpUtils url(String url) {
        this.url = url;
        return this;
    }

    // 省略部分代码 ......
    public <T> void execute(IHttpCallBack<T> callback) {
        // 如果没有指定，那么就用 application 中初始化的
//        mInitHttpRequest.get(mContext, mParams, mUrl, mCache, callback);
    }

}
