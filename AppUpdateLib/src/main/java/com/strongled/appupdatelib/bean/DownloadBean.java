package com.strongled.appupdatelib.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Sean on 2020/10/20
 */
public class DownloadBean implements Serializable {
    public String title;
    public String content;
    public String url;
    public String md5;
    public String versionCode;

    public static DownloadBean parse(String response) {
        try {
            JSONObject repJson = new JSONObject(response);  //response 可能不是正常的json字符串，所有要抛个异常
            String title = repJson.optString("title");
            String content = repJson.optString("content");
            String url = repJson.optString("url");
            String md5 = repJson.optString("md5");
            String versionCode = repJson.optString("versionCode");
            DownloadBean bean = new DownloadBean();
            bean.title = title;
            bean.content = content;
            bean.url = url;
            bean.md5 = md5;
            bean.versionCode = versionCode;
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
