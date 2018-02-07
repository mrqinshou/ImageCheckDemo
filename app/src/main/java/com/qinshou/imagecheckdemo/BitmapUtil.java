package com.qinshou.imagecheckdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;


/**
 * Description:
 * Created by: 禽兽先生
 * Created on 2018/2/1
 */

public class BitmapUtil {
    /**
     * Description:获取拼图块
     * Date:2018/2/7
     */
    public static Bitmap getPuzzleBitmap(Bitmap bitmap, Puzzle puzzle, int width, int height) {
        //创建一个拼图块大小的图片
        Bitmap mBitmap = Bitmap.createBitmap(puzzle.getWidth(), puzzle.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);

        //画拼图块的路径
        Path mPath = new Path();
        mPath.moveTo(0, puzzle.getHeight() / 4);
        mPath.lineTo(puzzle.getWidth() / 3, puzzle.getHeight() / 4);
        mPath.cubicTo(puzzle.getWidth() / 6, 0
                , puzzle.getWidth() - puzzle.getWidth() / 6, 0
                , puzzle.getWidth() - puzzle.getWidth() / 3, puzzle.getHeight() / 4);
        mPath.lineTo(puzzle.getWidth(), puzzle.getHeight() / 4);
        mPath.lineTo(puzzle.getWidth(), puzzle.getHeight());
        mPath.lineTo(0, puzzle.getHeight());
        mPath.lineTo(0, puzzle.getHeight() - puzzle.getHeight() / 4);
        mPath.cubicTo(puzzle.getWidth() / 3, puzzle.getHeight() - puzzle.getHeight() / 8
                , puzzle.getWidth() / 3, puzzle.getHeight() / 4 + puzzle.getHeight() / 8
                , 0, puzzle.getHeight() / 2);
        mPath.lineTo(0, puzzle.getHeight() / 4);
        mCanvas.drawPath(mPath, mPaint);

        //画拼图块的图片
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //拼图块显示的位置根据图片与控件的比例决定
        Rect src = new Rect((int) ((double) bitmap.getWidth() / (double) width * puzzle.getX())
                , (int) ((double) bitmap.getHeight() / (double) height * puzzle.getY())
                , (int) ((double) bitmap.getWidth() / (double) width * (puzzle.getX() + puzzle.getWidth()))
                , (int) ((double) bitmap.getHeight() / (double) height * (puzzle.getY() + puzzle.getHeight())));
        Rect dst = new Rect(0, 0, puzzle.getWidth(), puzzle.getHeight());
        mCanvas.drawBitmap(bitmap, src, dst, mPaint);

        //在拼图块外围画一个轮廓,显眼一点
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setXfermode(null);
        mCanvas.drawPath(mPath, mPaint);

        return mBitmap;
    }

    /**
     * Description:获取等待验证的图片
     * Date:2018/2/7
     */
    public static Bitmap getWaitCheckBitmap(Bitmap bitmap, Puzzle puzzle, int width, int height) {
        //创建一个与控件宽高相等的图片
        Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        Path mPath = new Path();

        //挖取掉拼图块
        mPath.moveTo(puzzle.getX(), puzzle.getY() + puzzle.getHeight() / 4);
        mPath.lineTo(puzzle.getX() + puzzle.getWidth() / 3, puzzle.getY() + puzzle.getHeight() / 4);
        mPath.cubicTo(puzzle.getX() + puzzle.getWidth() / 6, puzzle.getY()
                , puzzle.getX() + puzzle.getWidth() - puzzle.getWidth() / 6, puzzle.getY()
                , puzzle.getX() + puzzle.getWidth() - puzzle.getWidth() / 3, puzzle.getY() + puzzle.getHeight() / 4);
        mPath.lineTo(puzzle.getX() + puzzle.getWidth(), puzzle.getY() + puzzle.getHeight() / 4);
        mPath.lineTo(puzzle.getX() + puzzle.getWidth(), puzzle.getY() + puzzle.getHeight());
        mPath.lineTo(puzzle.getX(), puzzle.getY() + puzzle.getHeight());
        mPath.lineTo(puzzle.getX(), puzzle.getY() + puzzle.getHeight() - puzzle.getHeight() / 4);
        mPath.cubicTo(puzzle.getX() + puzzle.getWidth() / 3, puzzle.getY() + puzzle.getHeight() - puzzle.getHeight() / 8
                , puzzle.getX() + puzzle.getWidth() / 3, puzzle.getY() + puzzle.getHeight() / 4 + puzzle.getHeight() / 8
                , puzzle.getX(), puzzle.getY() + puzzle.getHeight() / 2);
        mPath.lineTo(puzzle.getX(), puzzle.getY() + puzzle.getHeight() / 4);
        mCanvas.drawPath(mPath, mPaint);

        //图片显示在控件范围内
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dst = new Rect(0, 0, width, height);
        mCanvas.drawBitmap(bitmap, src, dst, mPaint);
        return mBitmap;
    }
}
