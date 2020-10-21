package com.strongled.appupdatelib;

/**
 * Created by Sean on 2020/10/19
 */
public interface INetCallBack {
    void success(String response);

    void failed(Throwable throwable);
}
