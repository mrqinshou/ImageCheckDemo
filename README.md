# ImageCheckDemo
图片验证码控件

图片滑动验证的控件

使用步骤:
1.xml 中.
    <com.qinshou.imagecheckdemo.ImageCheckCodeView
        android:id="@+id/iccv_test"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

2.Java 代码中可以设置图片.
        ImageCheckCodeView iccvTest = (ImageCheckCodeView) findViewById(R.id.iccv_test);
        iccvTest.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.b));

3.对验证结果进行监听.
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
