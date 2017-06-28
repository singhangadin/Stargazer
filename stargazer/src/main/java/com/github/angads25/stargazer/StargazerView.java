package com.github.angads25.stargazer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <p>
 * Created by Angad on 28-06-2017.
 * </p>
 */

public class StargazerView extends View {
    private Paint paint;
    private int radius;
    private int centerX;
    private int centerY;

    private long downTime;
    private float downX;
    private float downY;

    public StargazerView(Context context) {
        super(context);
        initView();
    }

    public StargazerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StargazerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StargazerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        radius = Math.min(width, height);
        setMeasuredDimension(radius, radius);
        radius = radius >>> 1;
        centerX = (getRight() - getLeft()) >>> 1;
        centerY = (getBottom() - getTop()) >>> 1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   downTime = System.currentTimeMillis();
                                            downX = x; downY = y;
                                            break;

            case MotionEvent.ACTION_UP: long diff = System.currentTimeMillis() - downTime;
                                        if(diff < 200) {
                                            //TODO: Consider Touch Case
                                        }
                                        break;
        }
        return super.onTouchEvent(event);
    }
}
