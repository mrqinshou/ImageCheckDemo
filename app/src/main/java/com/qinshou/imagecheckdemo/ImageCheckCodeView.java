package com.qinshou.imagecheckdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Description:图形验证码控件
 * Created by 禽兽先生
 * Created on 2018/1/30
 */

public class ImageCheckCodeView extends View {
    private Paint mPaint;   //画笔,画圆角矩形的进度条,画进度游标
    private Bitmap mBitmap; //进行图片验证的原图
    private Bitmap waitCheckBitmap;   //等待验证的图片,是原图挖取掉一个拼图块后的图片
    private Puzzle puzzle;  //进行验证的拼图块
    private RectF roundRectF;   //进度条的圆角矩形
    private float progress = 0; //当前手机的滑动进度
    private int seekBarHeight;  //进度条的高度
    private boolean startImageCheck = false;   //开始图片验证的标志位
    private Rect puzzleSrc; //拼图块绘制区域
    private Rect puzzleDst; //拼图块显示区域
    private OnCheckResultCallback onCheckResultCallback;

    public void setOnCheckResultCallback(OnCheckResultCallback onCheckResultCallback) {
        this.onCheckResultCallback = onCheckResultCallback;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        if (getMeasuredWidth() == 0 || getMeasuredHeight() == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    initBitmap();
                }
            });
        } else {
            initBitmap();
        }
    }

    public ImageCheckCodeView(Context context) {
        this(context, null);
    }

    public ImageCheckCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageCheckCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        mPaint = new Paint();
        //创建进度条的圆角矩形的显示区域
        roundRectF = new RectF();
        //创建拼图块对象
        puzzle = new Puzzle();
        puzzleSrc = new Rect();
        puzzleDst = new Rect();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getContext().getResources().getDisplayMetrics().widthPixels;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getContext().getResources().getDisplayMetrics().heightPixels / 3;
        }
        setMeasuredDimension(width, height);

        initBitmap();
    }

    private void initBitmap() {
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.a);
        }

        seekBarHeight = getMeasuredHeight() / 10;
        roundRectF.set(0, getMeasuredHeight() - seekBarHeight * 2, getMeasuredWidth(), getMeasuredHeight() - seekBarHeight);

        puzzle.setContainerWidth(getMeasuredWidth());
        puzzle.setContainerHeight(getMeasuredHeight());
        puzzle.setBitmap(mBitmap);

        waitCheckBitmap = BitmapUtil.getWaitCheckBitmap(mBitmap, puzzle, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(waitCheckBitmap, 0, 0, null);
        //画灰色,50% 透明度的圆角矩形的指示条
        mPaint.setColor(Color.argb(128, 128, 128, 128));
        canvas.drawRoundRect(roundRectF, 45, 45, mPaint);
        //开始验证后重绘触摸点和拼图块的位置
        if (startImageCheck) {
            puzzleSrc.set(0, 0, puzzle.getWidth(), puzzle.getHeight());
            puzzleDst.set((int) progress - puzzle.getWidth() / 2, puzzle.getY(), (int) progress + puzzle.getWidth() - puzzle.getWidth() / 2, puzzle.getY() + puzzle.getHeight());
            canvas.drawBitmap(puzzle.getBitmap(), puzzleSrc, puzzleDst, null);

            //画触摸点
            mPaint.setColor(Color.BLACK);
            canvas.drawCircle(progress, getMeasuredHeight() - seekBarHeight - seekBarHeight / 2, seekBarHeight / 2, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getRawX() < getMeasuredWidth() / 10) {
                    startImageCheck = true;
                    progress = event.getRawX() < seekBarHeight / 2
                            ? seekBarHeight / 2
                            : event.getRawX() > getMeasuredWidth() - seekBarHeight / 2 ? getMeasuredWidth() - seekBarHeight / 2 : event.getRawX();
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!startImageCheck) {
                    break;
                }
                progress = event.getRawX() < seekBarHeight / 2
                        ? seekBarHeight / 2
                        : event.getRawX() > getMeasuredWidth() - seekBarHeight / 2 ? getMeasuredWidth() - seekBarHeight / 2 : event.getRawX();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (!startImageCheck) {
                    break;
                }
                progress = 0;
                startImageCheck = false;
                invalidate();
                if (onCheckResultCallback == null) {
                    break;
                }
                if (event.getRawX() > puzzle.getX() + puzzle.getWidth() / 2 - puzzle.getWidth() / 20
                        && event.getRawX() < puzzle.getX() + puzzle.getWidth() / 2 + puzzle.getWidth() / 20) {
                    //松手时触摸点在拼图块中心的横坐标左右偏差不超过拼图块宽度的十分之一则验证成功
                    onCheckResultCallback.onSuccess();
                } else {
                    onCheckResultCallback.onFailure();
                }
                break;
        }
        return false;
    }

    public interface OnCheckResultCallback {
        void onSuccess();

        void onFailure();
    }
}
