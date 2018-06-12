package com.sammie.httpbox;

/**
* Http请求入口
* @author ZhangWeijun
* @time 2018/6/12 14:04
*/
public class HttpUtils {

    // 这个可以在 application 中去初始化
    private static IHttpRequest mInitHttpRequest;
    private IHttpRequest mHttpRequest;
    public static void initHttpRequest(IHttpRequest httpRequest) {
        mInitHttpRequest = httpRequest;
    }
    // 如果有两种的情况下 比如 volley 下载文件并不是很屌 ，那么可以换成 OKHttp
    public HttpUtils httpRequest(IHttpRequest httpRequest) {
        this.mHttpRequest = httpRequest;
        return this;
    }
    // 省略部分代码 ......
    public <T> void execute(IHttpCallBack<T> callback) {
        // 如果没有指定，那么就用 application 中初始化的
        if(mHttpRequest == null){
            mHttpRequest = mInitHttpRequest;
        }
//        mHttpRequest.get(mContext, mParams, mUrl, mCache, callback);
    }

}
