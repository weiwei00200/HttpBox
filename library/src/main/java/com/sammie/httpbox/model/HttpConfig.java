package com.sammie.httpbox.model;

/**
 * 网络配置
 *
 * @author ZhangWeijun
 * @time 2018/6/12 16:19
 */
public class HttpConfig {

    private final String baseUrl;
    private final int timeout;
    private final boolean retryOnFail;
    private final long cacheTime;//缓存时间，单位 秒

    private HttpConfig(HttpConfigBuilder builder) {
        this.baseUrl = builder.baseUrl;
        this.timeout = builder.timeout;
        this.retryOnFail = builder.retryOnFail;
        this.cacheTime = builder.cacheTime;
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

    public long getCacheTime() {
        return cacheTime;
    }

    public static class HttpConfigBuilder {
        private String baseUrl = "";
        private int timeout = 15;
        private boolean retryOnFail = true;//失败时是否重试请求
        private long cacheTime = 30;

        public HttpConfigBuilder() {
        }

        public HttpConfigBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public HttpConfigBuilder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public HttpConfigBuilder retryOnFail(boolean retryOnFail) {
            this.retryOnFail = retryOnFail;
            return this;
        }

        public HttpConfigBuilder cacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public HttpConfig builder() {
            return new HttpConfig(this);
        }


    }

}
