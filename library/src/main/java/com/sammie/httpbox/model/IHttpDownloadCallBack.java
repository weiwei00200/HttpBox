package com.sammie.httpbox.model;

public interface IHttpDownloadCallBack {

    void process(int progress);

    void onStartDownload();

    void downloadFailed();

    void onFinish(String path);
}
