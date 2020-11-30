package com.strongled.appupdatelib;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.strongled.appupdatelib.bean.DownloadBean;
import com.strongled.appupdatelib.utils.AppUtils;
import com.strongled.appupdatelib.utils.ConstantsNetwork;

/**
 * Created by Sean on 2020/10/23
 */
public class UpdateHelper {
    private static int checkStatus = -1;

    private static int progress;
    private static boolean isSuccess;

    private static Context ctx;
    private static DownloadBean downloadBean;

    //检查是否服务器是否有最新的版本
    public static int checkVersion(String serverUrl, final String api, Context context) {
        ConstantsNetwork.URL = serverUrl;
        ctx = context;
        AppUpdater.getInstance().getNetManager().get(serverUrl + api, new INetCallBack() {
            @Override
            public void success(String response) {
                Log.i("Sean", "success: " + response);
                //1.解析json
                //{
                //    "title":"4.5.0更新啦！",
                //    "content":"1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
                //    "url":"http://59.110.162.30/v450_imooc_updater.apk",
                //    "md5":"14480fc08932105d55b9217c6d2fb90b",
                //    "versionCode":"450"
                //}
                //2.做版本匹配
                //如果需要更新
                //3.弹窗提醒
                //4.点击下载

                downloadBean = DownloadBean.parse(response);
                if (downloadBean == null) {
                    Toast.makeText(ctx, "数据解析异常", Toast.LENGTH_LONG).show();
                    return;
                }

                //检测是否需要弹窗
                try {
                    long versionCode = Long.parseLong(downloadBean.versionCode);
                    if (versionCode <= AppUtils.getVersionCode(ctx)) {
//                        Toast.makeText(context, "已经是最新版本了！", Toast.LENGTH_LONG).show();
                        checkStatus = 0;
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    checkStatus = -1;
                    return;
                }

                checkStatus = 1;

            }

            @Override
            public void failed(Throwable throwable) {
                checkStatus = -2;
            }
        }, context);
        return checkStatus;
    }

    /**
     * 在Destroy方法里面注销
     *
     * @param context
     */
    public static void cancelOnDestroy(Context context) {
        AppUpdater.getInstance().getNetManager().cancel(context);
    }


    /**
     * 开始下载更新，同时下载进度开始回传
     *
     * @return
     */
    public static void startDownloadApkFile() {
        UpdateVersionShowDialog.show((FragmentActivity) ctx, downloadBean);
    }


    public static int getProgress() {
        return progress;
    }

    protected static void setProgress(int progress) {
        UpdateHelper.progress = progress;
    }

    public static boolean isSuccess() {
        return isSuccess;
    }

    protected static void setIsSuccess(boolean isSuccess) {
        UpdateHelper.isSuccess = isSuccess;
    }
}
