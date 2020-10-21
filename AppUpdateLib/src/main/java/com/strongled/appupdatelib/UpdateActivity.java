package com.strongled.appupdatelib;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.strongled.appupdatelib.bean.DownloadBean;
import com.strongled.appupdatelib.utils.AppUtils;


public class UpdateActivity extends AppCompatActivity {

    private Button btnUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        btnUpdater = findViewById(R.id.btn_updater);
        btnUpdater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            Toast.makeText(UpdateActivity.this, "数据解析异常", Toast.LENGTH_LONG).show();
                            return;
                        }

                        //检测是否需要弹窗
                        try {
                            long versionCode = Long.parseLong(downloadBean.versionCode);
                            if (versionCode <= AppUtils.getVersionCode(UpdateActivity.this)) {
                                Toast.makeText(UpdateActivity.this, "已经是最新版本了！", Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(UpdateActivity.this, "版本检测接口返回数据异常", Toast.LENGTH_LONG).show();
                            return;
                        }

                        UpdateVersionShowDialog.show(UpdateActivity.this, downloadBean);


                    }

                    @Override
                    public void failed(Throwable throwable) {
                        Toast.makeText(UpdateActivity.this, "版本更新请求失败", Toast.LENGTH_LONG).show();
                    }
                }, UpdateActivity.this);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getNetManager().cancel(this);
    }
}