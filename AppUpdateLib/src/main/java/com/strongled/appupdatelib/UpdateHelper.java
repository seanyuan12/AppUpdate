package com.strongled.appupdatelib;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.strongled.appupdatelib.bean.DownloadBean;
import com.strongled.appupdatelib.utils.AppUtils;
import com.strongled.appupdatelib.utils.ConstantsNetwork;

import java.io.File;

/**
 * Created by Sean on 2020/10/23
 */
public class UpdateHelper {
    private static int checkStatus = -1;

    private static Context ctx;
    private static DownloadBean downloadBean;
    private static final String suffix = "sw_released_apk/";

    //检查是否服务器是否有最新的版本
    public static void checkVersion(String serverUrl, final String api, Context context, final ICheckVersionStatusListener listener) {
        ConstantsNetwork.URL = serverUrl;
        ctx = context;
        AppUpdater.getInstance().getNetManager().get(serverUrl + api, new INetCallBack() {
            @Override
            public void success(String response) {
                Log.i("Sean", "success: " + response);
                downloadBean = DownloadBean.parse(response);
                if (downloadBean == null) {
                    Toast.makeText(ctx, "数据解析异常", Toast.LENGTH_LONG).show();
                    listener.checkStatus(-3);
                } else {
                    //检测是否需要弹窗
                    try {
                        long versionCode = Long.parseLong(downloadBean.versionCode);
                        if (versionCode <= AppUtils.getVersionCode(ctx)) {
                            listener.checkStatus(0);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        listener.checkStatus(-2);
                    }

                    listener.checkStatus(1);
                }
            }

            @Override
            public void failed(Throwable throwable) {
                listener.checkStatus(-1);
            }
        }, context);
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
     * 下载apk文件
     *
     * @return
     */
    public static void downloadApkFile(final IDownloadListener listener) {
        final File targetFile = new File(ctx.getCacheDir(), "target.apk");
        String downloadUrl = downloadBean.filename;
        AppUpdater.getInstance().getNetManager().download(ConstantsNetwork.URL + suffix + downloadUrl, targetFile, new INetDownloadCallBack() {
            @Override
            public void success(File apkFile) {
                String fileMd5 = AppUtils.getFileMd5(targetFile);
                if (fileMd5 != null && fileMd5.equals(downloadBean.md5)) {
                    AppUtils.installApk((Activity) ctx, apkFile);
                } else {
                    Toast.makeText(ctx, "md5检测失败", Toast.LENGTH_LONG).show();
                }
                listener.success();
            }

            @Override
            public void progress(int progress) {
                listener.progress(progress);
            }

            @Override
            public void failed(Throwable throwable) {
                Toast.makeText(ctx, "文件下载失败", Toast.LENGTH_LONG).show();
                listener.failed(throwable);
            }
        }, ctx);
    }


}
