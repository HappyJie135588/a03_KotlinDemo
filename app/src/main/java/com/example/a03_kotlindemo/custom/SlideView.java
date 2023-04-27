package com.example.a03_kotlindemo.custom;


import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.a03_kotlindemo.R;
import com.example.a03_kotlindemo.databinding.SlideActionBinding;


public class SlideView extends ViewGroup implements View.OnClickListener {
    public static final String TAG = "SlideView";

    private View contentView;
    private View actionView;
    private float downX;
    private boolean isopen;
    private int actionWidth;

    private OnActionClickListener listener;
    private com.example.a03_kotlindemo.databinding.SlideActionBinding binding;

    public SlideView(Context context) {
        this(context, null);
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        binding = SlideActionBinding.inflate(LayoutInflater.from(getContext()), this, false);
        actionView = binding.getRoot();
        addView(actionView);
        initActionView();
    }

    private void initActionView() {
        binding.tvTop.setOnClickListener(this);
        binding.tvRead.setOnClickListener(this);
        binding.tvDelete.setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        LayoutParams layoutParams = contentView.getLayoutParams();
        if (layoutParams.height == LayoutParams.MATCH_PARENT) {
            contentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
        } else if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
            contentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
        } else {
            contentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY));
        }
        int contentHeight = contentView.getMeasuredHeight();
        actionWidth = widthSize * 3 / 4;
        actionView.measure(MeasureSpec.makeMeasureSpec(actionWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(contentHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(widthSize + actionWidth, contentHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int contentLeft = 0;
        int contentTop = 0;
        int contentRight = contentLeft + contentView.getMeasuredWidth();
        int contentBottom = contentTop + contentView.getMeasuredHeight();
        contentView.layout(contentLeft, contentTop, contentRight, contentBottom);
        int actionLeft = contentRight;
        int actionTop = contentTop;
        int actionRight = actionLeft + actionView.getMeasuredWidth();
        int actionBottom = actionTop + contentView.getMeasuredHeight();
        actionView.layout(actionLeft, actionTop, actionRight, actionBottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float dx = moveX - downX;
                if (getScrollX() - dx <= 0) {
                    scrollTo(0, 0);
                } else if (getScrollX() - dx >= actionWidth) {
                    scrollTo(actionWidth, 0);
                } else {
                    scrollBy((int) -dx, 0);
                }
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                if (isopen) {
                    if (getScrollX() < actionWidth * 9 / 10) {
                        close();
                    } else {
                        open();
                    }
                } else {
                    if (getScrollX() < actionWidth * 1 / 10) {
                        close();
                    } else {
                        open();
                    }
                }

                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float interceptMoveX = ev.getX();
                if (Math.abs(interceptMoveX - downX) > 0) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    public void close() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getScrollX(), 0);
        valueAnimator.setDuration(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(value, 0);
            }
        });
        valueAnimator.start();
        isopen = false;
    }

    public void open() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getScrollX(), actionWidth);
        valueAnimator.setDuration(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(value, 0);
            }
        });
        valueAnimator.start();
        isopen = true;
    }

    public void setListener(OnActionClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTop:
                if (listener != null) {
                    listener.onTopClick();
                }
                break;
            case R.id.tvRead:
                if (listener != null) {
                    listener.onReadClick();
                }
                break;
            case R.id.tvDelete:
                if (listener != null) {
                    listener.onDeleteClick();
                }
                break;
        }
    }

    public interface OnActionClickListener {
        void onTopClick();

        void onReadClick();

        void onDeleteClick();
    }
}
