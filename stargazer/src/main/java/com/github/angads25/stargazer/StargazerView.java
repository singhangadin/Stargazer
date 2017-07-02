package com.github.angads25.stargazer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

    private Point A, B, C, D, E, X1, X2;
    private Path path1, path2;

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
        A = new Point();
        B = new Point();
        C = new Point();
        D = new Point();
        E = new Point();
        X1 = new Point();
        X2 = new Point();
        path1 = new Path();
        path2 = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#FFFFFF"));

        // -----------------Reference Lines Start-----------------
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
            canvas.drawCircle(pt1x, pt1y, radius / 50, paint);

            //Top Most Point
            int pt2x = (int) (centerX + (radius * Math.cos(ang)));
            int pt2y = (int) (centerY + (radius * Math.sin(ang)));
            canvas.drawCircle(pt2x, pt2y, radius / 50, paint);

            canvas.drawLine(pt1x, pt1y, pt2x, pt2y, paint);

            // Right Center Point
            int pt3x = (int) (centerX + ((radius >>> 1) * Math.cos(ang1)));
            int pt3y = (int) (centerY + ((radius >>> 1) * Math.sin(ang1)));
            canvas.drawCircle(pt3x, pt3y, radius / 50, paint);

            // Left Center Point
            int pt4x = (int) (centerX + ((radius >>> 1) * Math.cos(ang2)));
            int pt4y = (int) (centerY + ((radius >>> 1) * Math.sin(ang2)));
            canvas.drawCircle(pt4x, pt4y, radius / 50, paint);

            canvas.drawLine(pt1x, pt1y, pt3x, pt3y, paint);
            canvas.drawLine(pt1x, pt1y, pt4x, pt4y, paint);

            canvas.drawLine(pt2x, pt2y, pt3x, pt3y, paint);
            canvas.drawLine(pt2x, pt2y, pt4x, pt4y, paint);

            // Higher Curve Start Point Left
            A.set(pt4x, pt4y);
            B.set(pt2x, pt2y);

            A = findNonConvexPoints(A, B, 2);

            int p5x = A.x;
            int p5y = A.y;
            canvas.drawCircle(A.x, A.y, radius / 50, paint);

            // Higher Curve End Point Right
            A.set(pt3x, pt3y);
            B.set(pt2x, pt2y);

            A = findNonConvexPoints(A, B, 2);

            int p6x = A.x;
            int p6y = A.y;
            canvas.drawCircle(A.x, A.y, radius / 50, paint);

            // Lower Curve End Point Left
            A.set(pt4x, pt4y);
            B.set(pt1x, pt1y);

            A = findNonConvexPoints(A, B, 3);

            int p7x = A.x;
            int p7y = A.y;
            canvas.drawCircle(A.x, A.y, radius / 50, paint);

            // Lower Curve Start Point Right
            A.set(pt3x, pt3y);
            B.set(pt1x, pt1y);

            A = findNonConvexPoints(A, B, 3);

            int p8x = A.x;
            int p8y = A.y;
            canvas.drawCircle(A.x, A.y, radius / 50, paint);

//            path1.moveTo(p7x, p7y);
//            path1.lineTo(pt4x, pt4y);
//            path1.lineTo(p5x, p5y);
//            path1.quadTo(pt2x, pt2y, p6x, p6y);
//            path1.lineTo(pt3x, pt3y);
//            path1.lineTo(p8x, p8y);
//            path1.quadTo(pt1x, pt1y, p7x, p7y);
//
//            canvas.drawPath(path1, paint);

            //TODO:
            int oldRadii = radius;

            radius = radius - (radius >>> 2);

            A.set(pt2x, pt2y);

            // New Top Point
            pt2x = (int) (centerX + (radius * Math.cos(ang)));
            pt2y = (int) (centerY + (radius * Math.sin(ang)));
            canvas.drawCircle(pt2x, pt2y, oldRadii / 50, paint);

            B.set(pt4x, pt4y);
            C.set(pt3x, pt3y);
            D.set(pt1x, pt1y);
            E.set(pt2x, pt2y);

            // Right new point
            X2 = findRightSidePoint(A, C, D, E);
            canvas.drawCircle(X2.x, X2.y, oldRadii / 50, paint);
            canvas.drawLine(pt2x, pt2y, X2.x, X2.y, paint);

            // Left new point
            X1 = findLeftSidePoint(A, B, D, E);
            canvas.drawCircle(X1.x, X1.y, oldRadii / 50, paint);
            canvas.drawLine(pt2x, pt2y, X1.x, X1.y, paint);

            A = findNonConvexPoints(A, B, 2);
            A = findNonConvexPoints(A, B, 3);

            canvas.drawPath(path2, paint);
            // TODO:

            offset = (offset % 360) + angle;
            radius = oldRadii;
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

    // Thanks to fleeblood for helping with this formula.
    // https://math.stackexchange.com/a/2342342/459190
    private Point findLeftSidePoint(Point A, Point B, Point D, Point E) {
        double distance1 = Math.hypot(D.x - E.x, D.y - E.y);
        double distance2 = Math.hypot(D.x - A.x, D.y - A.y);

        double r = distance1 / distance2;
        double X1x = D.x + (r * (B.x - D.x));
        double X1y = D.y + (r * (B.y - D.y));
        X1.set((int)X1x, (int)X1y);
        return X1;
    }


    // Thanks to fleeblood for helping with this formula.
    // https://math.stackexchange.com/a/2342342/459190
    private Point findRightSidePoint(Point A, Point C, Point D, Point E) {
        double distance1 = Math.hypot(D.x - E.x, D.y - E.y);
        double distance2 = Math.hypot(D.x - A.x, D.y - A.y);

        double r = distance1 / distance2;
        double X2x = D.x + (r * (C.x - D.x));
        double X2y = D.y + (r * (C.y - D.y));
        X2.set((int)X2x, (int)X2y);
        return X2;
    }

    private Point findNonConvexPoints(Point A, Point B, int level) {
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
