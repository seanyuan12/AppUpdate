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

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AppUpdater.getInstance().getNetManager().cancel(this);
    }
}