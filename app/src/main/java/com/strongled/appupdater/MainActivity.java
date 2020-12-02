package com.strongled.appupdater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.strongled.appupdatelib.ICheckVersionStatusListener;
import com.strongled.appupdatelib.IDownloadListener;
import com.strongled.appupdatelib.UpdateHelper;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private static final String BASE_URL = "https://order.strongled.com/sw_jk/";
    private static final String UPDATE_VERSION_URL = "get_released_info2.php?file_name=temperture_wincc.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        findViewById(R.id.btn_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateHelper.downloadApkFile(new IDownloadListener() {
                    @Override
                    public void success() {

                    }

                    @Override
                    public void progress(int progress) {

                    }

                    @Override
                    public void failed(Throwable throwable) {

                    }
                });
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateHelper.checkVersion(BASE_URL, UPDATE_VERSION_URL, MainActivity.this, new ICheckVersionStatusListener() {
                    @Override
                    public void checkStatus(int status) {
                        Log.e("Sean", "onClick: " + status);
                    }
                });

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpdateHelper.cancelOnDestroy(this);
    }

}