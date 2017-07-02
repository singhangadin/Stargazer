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

import com.github.angads25.stargazer.model.StarLeaf;

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

    private int minRadii;

    private long downTime;
    private float downX, downY;

    private Point H, B, D, F, E;
    private StarLeaf[] leaves;


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
        paint.setDither(true);

        H = new Point();
        B = new Point();
        D = new Point();
        F = new Point();
        E = new Point();

        leaves = new StarLeaf[5];
        for (int i = 0; i < 5; i++) {
            leaves[i] = new StarLeaf();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#FFFFFF"));

        int angle = 72;
        int offset = -18;
        for(int i = 0; i < 5; i++) {
            paint.setColor(Color.WHITE);
            double ang = Math.toRadians(offset);
            double ang1 = Math.toRadians(offset + 32);
            double ang2 = Math.toRadians(offset - 32);

            // Bottom Most Point
            int pt1x = (int) (centerX + ((radius / 16) * Math.cos(ang)));
            int pt1y = (int) (centerY + ((radius / 16) * Math.sin(ang)));
            H.set(pt1x, pt1y);

            // Left Center Point
            int pt4x = (int) (centerX + ((radius >>> 1) * Math.cos(ang2)));
            int pt4y = (int) (centerY + ((radius >>> 1) * Math.sin(ang2)));
            B.set(pt4x, pt4y);

            // Top Most Point
            int pt2x = (int) (centerX + (radius * Math.cos(ang)));
            int pt2y = (int) (centerY + (radius * Math.sin(ang)));
            D.set(pt2x, pt2y);

            // Right Center Point
            int pt3x = (int) (centerX + ((radius >>> 1) * Math.cos(ang1)));
            int pt3y = (int) (centerY + ((radius >>> 1) * Math.sin(ang1)));
            F.set(pt3x, pt3y);

            leaves[i].setPoints(H, B, D, F);
            canvas.drawPath(leaves[i].getPath(), paint);


            // Progress Star
            int oldRadii = radius;

            radius = radius >>> 1;

            // Top Most Point
            pt2x = (int) (centerX + (radius * Math.cos(ang)));
            pt2y = (int) (centerY + (radius * Math.sin(ang)));
            E.set(pt2x, pt2y);

            paint.setColor(Color.parseColor("#FFD861"));

            leaves[i].setProgressPoints(H, B, D, F, E);
            canvas.drawPath(leaves[i].getPath(), paint);

            radius = oldRadii;

            offset = (offset % 360) + angle;
        }
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
        minRadii = radius / 16;
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
