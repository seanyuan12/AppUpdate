package com.strongled.appupdatelib;

import java.io.File;

/**
 * Created by Sean on 2020/10/19
 */
public interface INetManager {
    void get(String url, INetCallBack callBack, Object tag);

    void download(String url, File targetFile, INetDownloadCallBack callBack, Object tag);

    void cancel(Object tag);
}
