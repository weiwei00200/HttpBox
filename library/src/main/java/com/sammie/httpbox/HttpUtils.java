package com.sammie.httpbox;

import com.sammie.httpbox.frame.OkHttpRequest;
import com.sammie.httpbox.model.HttpConfig;
import com.sammie.httpbox.model.IHttpCallBack;
import com.sammie.httpbox.model.IHttpDownloadCallBack;
import com.sammie.httpbox.model.IHttpRequest;
import com.sammie.httpbox.model.IHttpUploadCallBack;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;

/**
 * Http请求入口
 *
 * @author ZhangWeijun
 * @time 2018/6/12 14:04
 */
public class HttpUtils<T> {

    // 这个可以在 application 中去初始化
    private static IHttpRequest mInitIHttpRequest;
    private IHttpRequest mIHttpRequest;
    private static HttpConfig mHttpConfig;

    private int TypeGet = 0x0011, TypePost = 0x0022;
    private String mUrl = "";//请求URL
    private int mReqType = TypePost;//请求类型
    private Map<String, Object> mParamMap;//请求参数
    private Class<T> mParseClass;//json需要装换的ObjectClass
    private boolean mCache;//是否要使用缓存
    private String mDownloadFileSavePath = "";//下载文件保存的路劲
    private String mDownloadFileName = "";//下载文件的定义名称
    private String mUploadFilePath = "";//上传文件的本地路径

    public static void initHttpRequest(IHttpRequest httpRequest) {
        mInitIHttpRequest = httpRequest;
    }

    public static HttpUtils with(Class parseClass) {
        return new HttpUtils(parseClass);
    }

    public static HttpUtils with() {
        return new HttpUtils();
    }

    private HttpUtils() {
        checkIHttpRequest();
        mParamMap = new HashMap<>();
    }

    private HttpUtils(Class parseClass) {
        checkIHttpRequest();
        mParseClass = parseClass;
        mParamMap = new HashMap<>();
    }
    private void checkIHttpRequest(){
        if (mInitIHttpRequest != null) {
            mIHttpRequest = mInitIHttpRequest;
        } else {
            //如果没设置就默认用OkHttp;
            mIHttpRequest = new OkHttpRequest(new HttpConfig.HttpConfigBuilder().builder());
        }
    }

    public HttpUtils get() {
        mReqType = TypeGet;
        return this;
    }

    public HttpUtils post() {
        mReqType = TypePost;
        return this;
    }

    public HttpUtils param(String key, Object value) {
        mParamMap.put(key, value);
        return this;
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils cache(boolean cache) {
        mCache = cache;
        return this;
    }

    public HttpUtils downloadFileSavePath(String downloadFileSavePath) {
        mDownloadFileSavePath = downloadFileSavePath;
        return this;
    }

    public HttpUtils downloadFileName(String downloadFileName){
        mDownloadFileName = downloadFileName;
        return this;
    }

    public HttpUtils uploadFilePath(String uploadFilePath) {
        mUploadFilePath = uploadFilePath;
        return this;
    }

    /**
     * 请求数据
     *
     * @param callback
     */
    public void execute(IHttpCallBack<T> callback) {
        if (mReqType == TypeGet) {
            mIHttpRequest.get(mUrl, mParamMap, mParseClass, mCache, callback);
        } else if (mReqType == TypePost) {
            mIHttpRequest.post(mUrl, mParamMap, mParseClass, mCache, callback);
        }
    }

    /**
     * 下载文件
     *
     * @param callBack
     */
    public void download(IHttpDownloadCallBack callBack) {
        mIHttpRequest.download(mUrl, mDownloadFileSavePath, mDownloadFileName, callBack);
    }

    public void upload(IHttpUploadCallBack callBack) {
        mIHttpRequest.upload(mUrl, mUploadFilePath, mParamMap, callBack);
    }
}
