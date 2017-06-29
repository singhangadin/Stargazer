package com.github.angads25.stargazer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
    private float downX, downY;

    private Point A, B;

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
        A = new Point();
        B = new Point();
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

            // Bottom Most Point
            int pt1x = (int) (centerX + ((radius / 16) * Math.cos(ang)));
            int pt1y = (int) (centerY + ((radius / 16) * Math.sin(ang)));

            //Top Most Point
            int pt2x = (int) (centerX + (radius * Math.cos(ang)));
            int pt2y = (int) (centerY + (radius * Math.sin(ang)));

            canvas.drawCircle(pt1x, pt1y, radius / 50, paint);
            canvas.drawCircle(pt2x, pt2y, radius / 50, paint);

            canvas.drawLine(pt1x, pt1y, pt2x, pt2y, paint);

            // Right Center Point
            int pt3x = (int) (centerX + ((radius >>> 1) * Math.cos(ang1)));
            int pt3y = (int) (centerY + ((radius >>> 1) * Math.sin(ang1)));

            // Left Center Point
            int pt4x = (int) (centerX + ((radius >>> 1) * Math.cos(ang2)));
            int pt4y = (int) (centerY + ((radius >>> 1) * Math.sin(ang2)));

            canvas.drawCircle(pt3x, pt3y, radius / 50, paint);
            canvas.drawCircle(pt4x, pt4y, radius / 50, paint);

            canvas.drawLine(pt1x, pt1y, pt3x, pt3y, paint);
            canvas.drawLine(pt1x, pt1y, pt4x, pt4y, paint);

            canvas.drawLine(pt2x, pt2y, pt3x, pt3y, paint);
            canvas.drawLine(pt2x, pt2y, pt4x, pt4y, paint);

            // Higher Curve Start Point Left
            A.set(pt4x, pt4y);
            B.set(pt2x, pt2y);

            A = findX3Y3(A, B, 2);
            canvas.drawCircle(A.x, A.y, radius / 50, paint);

            // Higher Curve Start Point Right
            A.set(pt3x, pt3y);
            B.set(pt2x, pt2y);

            A = findX3Y3(A, B, 2);
            canvas.drawCircle(A.x, A.y, radius / 50, paint);

            // Lower Curve Start Point Left
            A.set(pt4x, pt4y);
            B.set(pt1x, pt1y);

            A = findX3Y3(A, B, 3);
            canvas.drawCircle(A.x, A.y, radius / 50, paint);

            // Lower Curve Start Point Right
            A.set(pt3x, pt3y);
            B.set(pt1x, pt1y);

            A = findX3Y3(A, B, 3);
            canvas.drawCircle(A.x, A.y, radius / 50, paint);

            offset = (offset % 360) + angle;
        }

        paint.setColor(Color.RED);
        offset = 90;
        for(int i = 0; i < 5; i++) {
            double ang = Math.toRadians(offset);
            int pt1 = (int) (centerX + (radius * Math.cos(ang)));
            int pt2 = (int) (centerY + (radius * Math.sin(ang)));
            canvas.drawLine(centerX, centerY, pt1, pt2, paint);
            offset = (offset % 360) + angle;
        }
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
        radius = bounds - ((bounds >>> 1) / 5);
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

    private Point findX3Y3(Point A, Point B, int level) {
        int x1 = A.x;
        int y1 = A.y;
        int x2 = B.x;
        int y2 = B.y;

        int x3 = 0;
        int y3 = 0;

        int tempx = x1;
        int tempy = y1;
        for(int i = 0; i < level; i++) {
            x3 = (x2 + tempx) >>> 1;
            y3 = (y2 + tempy) >>> 1;
            tempx = x3;
            tempy = y3;
        }
        A.set(x3, y3);
        return A;
    }
}
