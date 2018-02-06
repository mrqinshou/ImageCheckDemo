package com.qinshou.imagecheckdemo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageCheckCodeView iccvTest = (ImageCheckCodeView) findViewById(R.id.iccv_test);
        iccvTest.setOnCheckResultCallback(new ImageCheckCodeView.OnCheckResultCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "验证成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
            }
        });
        iccvTest.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.b));
    }
}
