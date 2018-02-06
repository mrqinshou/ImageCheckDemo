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
    public static Bitmap getPuzzleBitmap(Bitmap bitmap, Puzzle puzzle) {
        Bitmap mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        Path mPath = new Path();

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
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawBitmap(bitmap, 0, 0, mPaint);

        return mBitmap;
    }

    public static Bitmap getWaitVerificationBitmap(Bitmap bitmap, Puzzle puzzle) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Bitmap mBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        Path mPath = new Path();

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
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        mCanvas.drawBitmap(bitmap, 0, 0, mPaint);
        return mBitmap;
    }

    public static Bitmap getPuzzleBitmap(Bitmap bitmap, Puzzle puzzle, int width, int height) {
        Bitmap mBitmap = Bitmap.createBitmap(puzzle.getWidth(), puzzle.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(10f);
        mPaint.setAntiAlias(true);
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
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Rect src = new Rect((int) ((double) bitmap.getWidth() / (double) width * puzzle.getX())
                , (int) ((double) bitmap.getHeight() / (double) height * puzzle.getY())
                , (int) ((double) bitmap.getWidth() / (double) width * (puzzle.getX() + puzzle.getWidth()))
                , (int) ((double) bitmap.getHeight() / (double) height * (puzzle.getY() + puzzle.getHeight())));
        Rect dst = new Rect(0, 0, puzzle.getWidth(), puzzle.getHeight());
        mCanvas.drawBitmap(bitmap, src, dst, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setXfermode(null);
        mCanvas.drawPath(mPath, mPaint);

        return mBitmap;
    }

    public static Bitmap getWaitVerificationBitmap(Bitmap bitmap, Puzzle puzzle, int width, int height) {
        Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        Path mPath = new Path();

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
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dst = new Rect(0, 0, width, height);
        mCanvas.drawBitmap(bitmap, src, dst, mPaint);
        return mBitmap;
    }

}
