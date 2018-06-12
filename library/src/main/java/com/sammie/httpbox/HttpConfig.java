package com.sammie.httpbox;

/**
* 网络配置
* @author ZhangWeijun
* @time 2018/6/12 16:19
*/
public class HttpConfig {

    private final String baseUrl;
    private final int timeout;
    private final boolean retryOnFail;//失败时是否重试请求
    private final int retryTimes;//重试次数

    private HttpConfig(HttpConfigBuilder builder){
        this.baseUrl = builder.baseUrl;
        this.timeout = builder.timeout;
        this.retryOnFail = builder.retryOnFail;
        this.retryTimes  = builder.retryTimes;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public boolean isRetryOnFail() {
        return retryOnFail;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public static class HttpConfigBuilder{
        private String baseUrl = "";
        private int timeout = 15;
        private boolean retryOnFail = true;//失败时是否重试请求
        private int retryTimes = 2;//重试次数
        private boolean cacheEnable = false;//是否开启缓存

        public HttpConfigBuilder(String baseUrl){
            this.baseUrl = baseUrl;
        }

        public HttpConfigBuilder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public HttpConfigBuilder retryOnFail(boolean retryOnFail) {
            this.retryOnFail = retryOnFail;
            return this;
        }

        public HttpConfigBuilder retryTimes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public HttpConfigBuilder cacheEnable(boolean cacheEnable){
            this.cacheEnable = cacheEnable;
            return this;
        }

        public HttpConfig builder(){
            return new HttpConfig(this);
        }


    }

}
