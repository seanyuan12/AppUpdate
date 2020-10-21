package com.strongled.appupdatelib;

/**
 * Created by Sean on 2020/10/19
 */
public class AppUpdater {

    private static AppUpdater sInstance = new AppUpdater();
    //网络请求，下载的能力
    //OkHttp，volley，httpclient
    private INetManager mNetManager = new OkHttpManager();

    public INetManager getNetManager() {
        return mNetManager;
    }

    public static AppUpdater getInstance() {
        return sInstance;
    }

}
