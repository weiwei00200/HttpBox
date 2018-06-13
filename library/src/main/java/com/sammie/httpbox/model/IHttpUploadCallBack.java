package com.sammie.httpbox.model;

public interface IHttpUploadCallBack {

    void process(long progress);

    void onStartUpload();

    void onStopUpload();

    void onFinish();
}
