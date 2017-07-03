/*
 * Copyright (C) 2017 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.angads25.stargazer.utils;

import android.graphics.Point;

/**
 * <p>
 * Created by Angad on 03-07-2017.
 * </p>
 */

public class Utility {

    // Thanks to fleeblood for helping with this formula.
    // https://math.stackexchange.com/a/2342342/459190
    public static Point findLeftSidePoint(Point A, Point B, Point D, Point E, Point X1) {
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
    public static Point findRightSidePoint(Point A, Point C, Point D, Point E, Point X2) {
        double distance1 = Math.hypot(D.x - E.x, D.y - E.y);
        double distance2 = Math.hypot(D.x - A.x, D.y - A.y);

        double r = distance1 / distance2;
        double X2x = D.x + (r * (C.x - D.x));
        double X2y = D.y + (r * (C.y - D.y));
        X2.set((int)X2x, (int)X2y);
        return X2;
    }

    public static Point findNonConvexPoints(Point A, Point B, Point D, int level) {
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
        D.set(x3, y3);
        return D;
    }
}
