package com.github.angads25.stargazer.utils;

import android.graphics.Point;

/**
 * <p>
 * Created by Angad on 04-07-2017.
 * </p>
 */

// Reference
// http://www.sanfoundry.com/java-program-check-whether-given-point-lies-given-polygon/
public class PolygonUtils {
    private static boolean onSegment(Point p, Point q, Point r) {
        return q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x)
                && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y);
    }

    private static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0)
            return 0;
        return (val > 0) ? 1 : 2;
    }

    private static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        return  o1 != o2 && o3 != o4 ||
                o1 == 0 && onSegment(p1, p2, q1) ||
                o2 == 0 && onSegment(p1, q2, q1) ||
                o3 == 0 && onSegment(p2, p1, q2) ||
                o4 == 0 && onSegment(p2, q1, q2);
    }

    public static boolean isInside(Point polygon[], Point p) {
        int n = polygon.length;
        int INF = 10000;
        if (n < 3)
            return false;

        Point extreme = new Point(INF, p.y);

        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;
            if (doIntersect(polygon[i], polygon[next], p, extreme)) {
                if (orientation(polygon[i], p, polygon[next]) == 0)
                    return onSegment(polygon[i], p, polygon[next]);
                count++;
            }
            i = next;
        } while (i != 0);
        return (count & 1) == 1;
    }
}