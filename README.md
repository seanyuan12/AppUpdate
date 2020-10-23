# AppUpdate
一个基于安卓的应用内软件升级库


如何使用？
1：添加依赖，引入到您的项目，由于项目中有使用到okhttp3.12.0的开源库，所以也要加上okhttp的依赖
implementation 'com.github.seanyuan12:AppUpdate:1.0.0'
implementation "com.squareup.okhttp3:okhttp:3.12.0"
2：直接调用方法UpdateHelper.checkVersion(),传入相应的参数即可
3：记得在onDestroy中调用 cancelOndestroy()方法，否则中间退出时会发生异常！
示例：

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateHelper.checkVersion("", MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpdateHelper.cancelOnDestroy(this);
    }
}
