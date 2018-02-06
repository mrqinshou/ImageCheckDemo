package com.qinshou.imagecheckdemo;

import android.graphics.Bitmap;

import java.util.Random;

public class Puzzle {
    private int containerWidth; //容器宽度
    private int containerHeight;    //容器高度
    private int x;  //拼图块左上角横坐标
    private int y;  //拼图块左上角纵坐标
    private int width;  //拼图块的宽
    private int height; //拼图块的高
    private Bitmap bitmap;  //原图

    public Puzzle() {
    }

    public void setContainerWidth(int containerWidth) {
        this.containerWidth = containerWidth;
        x = new Random().nextInt(containerWidth
                - containerWidth / 5    //减去拼图块宽度,保证拼图块全部显示
        );
        width = containerWidth / 5;
    }

    public void setContainerHeight(int containerHeight) {
        this.containerHeight = containerHeight;
        y = new Random().nextInt(containerHeight
                - containerHeight / 5   //减去拼图块高度,保证拼图块全部显示
        );
        height = containerHeight / 4;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = BitmapUtil.getPuzzleBitmap(bitmap, this, containerWidth, containerHeight);
    }
}
