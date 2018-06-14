package com.sammie.httpbox.model;

public interface IHttpDownloadCallBack {

    void process(int progress);

    void onStartDownload();

    void onDownloadSuccessful(String path);

    void onDownloadFailed();

    void onFinish();
}
