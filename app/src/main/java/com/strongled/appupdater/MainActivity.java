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
import com.strongled.appupdatelib.UpdateHelper;
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
                UpdateHelper.checkVersion("http://59.110.162.30/app_updater_version.json", MainActivity.this);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpdateHelper.cancelOnDestroy(this);
    }

}