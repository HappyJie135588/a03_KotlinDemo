package com.example.a03_kotlindemo.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.a03_kotlindemo.R;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义表盘
 */
public class WatchFace extends View {
    //刻度画笔
    private Paint mScalePain;
    //时针画笔
    private Paint mHourPaint;
    //分针画笔
    private Paint mMinPaint;
    //秒针画笔
    private Paint mSecondPaint;

    //刻度颜色
    private int mScaleColor;
    //时针颜色
    private int mHourColor;
    //分针颜色
    private int mMinColor;
    //秒针颜色
    private int mSecondColor;

    //背景资源
    private int mBgResId;
    //是否显示刻度
    private boolean isShowScale;//默认显示刻度
    //背景图片
    private Bitmap mBgBitmap = null;

    //表盘宽度
    private int mWidth;
    //表盘高度
    private int mHeight;
    //表盘背景图片的区域
    private Rect mSrcRect;
    //表盘背景需要绘制的区域
    private Rect mDesRect;
    //表盘半径
    private int mRadius;
    //表盘刻度外圆半径
    private int mOuterC;
    //表盘刻度内圆半径
    private int mInnerC;
    //表盘中心小圆圈半径
    private int mInnerCircleRadius;
    //时针半径
    private int mHourRadius;
    //分针半径
    private int mMinRadius;
    //秒针半径
    private int mSecondRadius;

    //当前日历对象
    private Calendar mCalendar;
    //当前时间
    private int mHourValue;
    //当前分钟
    private int mMinValue;
    //当前秒钟
    private int mSecondValue;

    //定时器
    private Timer mTimer;
    //定时任务
    private TimerTask mTimerTask;

    public WatchFace(Context context) {
        this(context, null);
    }

    public WatchFace(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchFace(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化属性
        initAttrs(context, attrs);
        //初始化画笔
        initPaints();
        //初始化当前日历对象
        mCalendar = Calendar.getInstance();
        mCalendar.setTimeZone(TimeZone.getDefault());
        //初始化定时器及定时任务
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                //重绘制
                invalidate();
            }
        };
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTimer.schedule(mTimerTask, 1000 - System.currentTimeMillis() % 1000, 1000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTimer.cancel();
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WatchFace);
        mHourColor = a.getColor(R.styleable.WatchFace_hourColor, 0xFF0000);
        mMinColor = a.getColor(R.styleable.WatchFace_minColor, 0x00FF00);
        mSecondColor = a.getColor(R.styleable.WatchFace_secondColor, 0x0000FF);
        mScaleColor = a.getColor(R.styleable.WatchFace_scaleColor, 0x000000);
        mBgResId = a.getResourceId(R.styleable.WatchFace_bg, -1);
        //默认显示刻度
        isShowScale = a.getBoolean(R.styleable.WatchFace_scaleShow, true);
        if (mBgResId != -1) {
            mBgBitmap = BitmapFactory.decodeResource(getResources(), mBgResId);
        }
        //回收属性容器
        a.recycle();
    }

    private void initPaints() {
        //表盘背景、刻度、中心圆圈画笔
        mScalePain = new Paint();
        mScalePain.setColor(mScaleColor);
        mScalePain.setStyle(Paint.Style.STROKE);
        mScalePain.setStrokeWidth(UiUtils.dp2px(5));
        mScalePain.setAntiAlias(true);
        //时针画笔
        mHourPaint = new Paint();
        mHourPaint.setColor(mHourColor);
        mHourPaint.setStyle(Paint.Style.STROKE);
        mHourPaint.setStrokeWidth(UiUtils.dp2px(10));
        //mHourPaint.setStrokeCap(Paint.Cap.ROUND);
        mHourPaint.setAntiAlias(true);
        //分针画笔
        mMinPaint = new Paint();
        mMinPaint.setColor(mMinColor);
        mMinPaint.setStyle(Paint.Style.STROKE);
        mMinPaint.setStrokeWidth(UiUtils.dp2px(6));
        mMinPaint.setAntiAlias(true);
        //秒针画笔
        mSecondPaint = new Paint();
        mSecondPaint.setColor(mSecondColor);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mSecondPaint.setStrokeWidth(UiUtils.dp2px(2));
        mSecondPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int targetSize = Math.min(widthSize, heightSize);
        //测量自己
        setMeasuredDimension(targetSize, targetSize);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //初始化背景绘制区域
        initRect();
        //计算半径
        mRadius = (int) (mWidth / 2f);
        mInnerC = (int) (mRadius * 0.8f);
        mOuterC = (int) (mRadius * 0.95f);
        mInnerCircleRadius = UiUtils.dp2px(5);
        mHourRadius = (int) (mRadius * 0.5f);
        mMinRadius = (int) (mRadius * 0.6f);
        mSecondRadius = (int) (mRadius * 0.7f);
    }

    /**
     * 初始化背景绘制区域
     */
    private void initRect() {
        if (mBgBitmap == null) {
            return;
        }
        mSrcRect = new Rect();
        mSrcRect.left = 0;
        mSrcRect.top = 0;
        mSrcRect.right = mBgBitmap.getWidth();
        mSrcRect.bottom = mBgBitmap.getHeight();
        mDesRect = new Rect();
        mDesRect.left = 0;
        mDesRect.top = 0;
        mDesRect.right = mWidth;
        mDesRect.bottom = mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //刷新当前时间
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mHourValue = mCalendar.get(Calendar.HOUR);
        mMinValue = mCalendar.get(Calendar.MINUTE);
        mSecondValue = mCalendar.get(Calendar.SECOND);
        //绘制黑色底色
        canvas.drawColor(0x000000);
        //绘制背景刻度等
        drawBgScale(canvas);
        if (mSecondValue == 0) {
            //绘制秒针
            drawSecond(canvas);
            //绘制分针
            drawMin(canvas);
            //绘制时针
            drawHour(canvas);
        } else {
            //绘制时针
            drawHour(canvas);
            //绘制分针
            drawMin(canvas);
            //绘制秒针
            drawSecond(canvas);
        }
    }

    /**
     * 绘制背景刻度等
     *
     * @param canvas
     */
    private void drawBgScale(Canvas canvas) {
        if (mBgBitmap != null) {
            //绘制背景图
            canvas.save();

            Path path =new Path();
            path.addCircle(mRadius,mRadius,mRadius, Path.Direction.CW);
            canvas.clipPath(path);
            canvas.drawBitmap(mBgBitmap, mSrcRect, mDesRect, mSecondPaint);
            canvas.restore();
        }
        //绘制刻度
        if (isShowScale) {
            //绘制中心圆圈
            canvas.drawCircle(mRadius, mRadius, mInnerCircleRadius, mScalePain);
            canvas.save();
            for (int i = 0; i < 12; i++) {
                canvas.drawLine(mRadius, mRadius - mOuterC, mRadius, mRadius - mInnerC, mScalePain);
                canvas.rotate(30, mRadius, mRadius);
            }
            canvas.restore();
        }
    }

    /**
     * 绘制时针
     *
     * @param canvas
     */
    private void drawHour(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (mHourValue * 30 + mMinValue * 0.5), mRadius, mRadius);
        canvas.drawLine(mRadius, mRadius - mHourRadius, mRadius, mRadius - mInnerCircleRadius, mHourPaint);
        canvas.restore();
    }

    /**
     * 绘制分针
     *
     * @param canvas
     */
    private void drawMin(Canvas canvas) {
        canvas.save();
        canvas.rotate(mMinValue * 6, mRadius, mRadius);
        canvas.drawLine(mRadius, mRadius - mMinRadius, mRadius, mRadius - mInnerCircleRadius, mMinPaint);
        canvas.restore();
    }

    /**
     * 绘制秒针
     *
     * @param canvas
     */
    private void drawSecond(Canvas canvas) {
        canvas.save();
        canvas.rotate(mSecondValue * 6, mRadius, mRadius);
        canvas.drawLine(mRadius, mRadius - mSecondRadius, mRadius, mRadius - mInnerCircleRadius, mSecondPaint);
        canvas.restore();
    }
}