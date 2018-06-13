package com.sammie.httpbox.model;

public interface IHttpCallBack<T> {

    void onSuccess(T result);

    void onFailed(String errorMsg);

    void onComplete();
}
