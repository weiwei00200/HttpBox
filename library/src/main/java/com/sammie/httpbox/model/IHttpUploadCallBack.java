package com.sammie.httpbox.model;

public interface IHttpUploadCallBack {

    void onStartUpload();

    void onUploadSuccessful();

    void onUploadFailed();

    void onFinish();
}
