package com.github.angads25.stargazer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private int bounds;
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
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.WHITE);

        // -----------------Reference Lines Start-----------------
        int angle = 72;
        int offset = -18;
        for(int i = 0; i < 5; i++) {
            double ang = Math.toRadians(offset);
            double ang1 = Math.toRadians(offset + 32);
            double ang2 = Math.toRadians(offset - 32);
            int pt1 = (int) (centerX + ((radius / 16) * Math.cos(ang)));
            int pt2 = (int) (centerY + ((radius / 16) * Math.sin(ang)));
            int pt3 = (int) (centerX + (radius * Math.cos(ang)));
            int pt4 = (int) (centerY + (radius * Math.sin(ang)));

            canvas.drawCircle(pt1, pt2, radius / 50, paint);
            canvas.drawCircle(pt3, pt4, radius / 50, paint);

            canvas.drawLine(pt1, pt2, pt3, pt4, paint);

            int pt5 = (int) (centerX + ((radius >>> 1) * Math.cos(ang1)));
            int pt6 = (int) (centerY + ((radius >>> 1) * Math.sin(ang1)));

            int pt7 = (int) (centerX + ((radius >>> 1) * Math.cos(ang2)));
            int pt8 = (int) (centerY + ((radius >>> 1) * Math.sin(ang2)));

            canvas.drawCircle(pt5, pt6, radius / 50, paint);
            canvas.drawCircle(pt7, pt8, radius / 50, paint);

            canvas.drawLine(pt1, pt2, pt5, pt6, paint);
            canvas.drawLine(pt1, pt2, pt7, pt8, paint);

            canvas.drawLine(pt3, pt4, pt5, pt6, paint);
            canvas.drawLine(pt3, pt4, pt7, pt8, paint);

            offset = (offset % 360) + angle;
        }

//        paint.setColor(Color.RED);
//        offset = 90;
//        for(int i = 0; i < 5; i++) {
//            double ang = Math.toRadians(offset);
//            int pt1 = (int) (centerX + (radius * Math.cos(ang)));
//            int pt2 = (int) (centerY + (radius * Math.sin(ang)));
//            canvas.drawLine(centerX, centerY, pt1, pt2, paint);
//            offset = (offset % 360) + angle;
//        }
        // -----------------Reference Lines Ends------------------
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        bounds = Math.min(width, height);
        setMeasuredDimension(bounds, bounds);
        bounds = bounds >>> 1;
        radius = bounds - ((bounds >>>1) / 5);
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
                                        if(diff < 250) {
                                            //TODO: Consider Touch Case
                                        }
                                        break;
        }
        return super.onTouchEvent(event);
    }
}
