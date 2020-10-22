package com.strongled.appupdatelib;

import java.io.File;

/**
 * Created by Sean on 2020/10/19
 */
public interface INetDownloadCallBack {
    void success(File apkFile);

    void progress(int progress);

    void failed(Throwable throwable);

}
