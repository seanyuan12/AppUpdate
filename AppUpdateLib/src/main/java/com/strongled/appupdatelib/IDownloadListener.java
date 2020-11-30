package com.strongled.appupdatelib;

import java.io.File;

/**
 * Created by Sean on 2020/11/30
 */
public interface IDownloadListener {

    void success();

    void progress(int progress);

    void failed(Throwable throwable);
}
