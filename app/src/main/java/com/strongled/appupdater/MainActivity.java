package com.strongled.appupdater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.strongled.appupdatelib.AppUpdater;
import com.strongled.appupdatelib.INetCallBack;
import com.strongled.appupdatelib.UpdateVersionShowDialog;
import com.strongled.appupdatelib.bean.DownloadBean;
import com.strongled.appupdatelib.utils.AppUtils;

public class MainActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUpdater.getInstance().getNetManager().get("http://59.110.162.30/app_updater_version.json", new INetCallBack() {
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

                        DownloadBean downloadBean = DownloadBean.parse(response);
                        if (downloadBean == null) {
                            Toast.makeText(MainActivity.this, "数据解析异常", Toast.LENGTH_LONG).show();
                            return;
                        }

                        //检测是否需要弹窗
                        try {
                            long versionCode = Long.parseLong(downloadBean.versionCode);
                            if (versionCode <= AppUtils.getVersionCode(MainActivity.this)) {
                                Toast.makeText(MainActivity.this, "已经是最新版本了！", Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "版本检测接口返回数据异常", Toast.LENGTH_LONG).show();
                            return;
                        }

                        UpdateVersionShowDialog.show(MainActivity.this, downloadBean);


                    }

                    @Override
                    public void failed(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "版本更新请求失败", Toast.LENGTH_LONG).show();
                    }
                }, MainActivity.this);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getNetManager().cancel(this);
    }

}