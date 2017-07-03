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

package com.github.angads25.stargazer.model;

import android.graphics.Path;
import android.graphics.Point;

import com.github.angads25.stargazer.utils.PolygonUtils;
import com.github.angads25.stargazer.utils.Utility;

/**
 * <p>
 * Created by Angad on 02-07-2017.
 * </p>
 */

public class StarLeaf {
    private int leafRadii;

    private Path path;
    private Point points[];

    public StarLeaf() {
        points = new Point[8];
        for(int i = 0; i < points.length; i++) {
            points[i] = new Point();
        }
        path = new Path();
    }

    public void setPoints(Point H, Point B, Point D, Point F) {
        points[7].set(H.x, H.y);
        points[1].set(B.x, B.y);
        points[3].set(D.x, D.y);
        points[5].set(F.x, F.y);

        points[0] = Utility.findNonConvexPoints(B, H, points[0], 3);
        points[6] = Utility.findNonConvexPoints(F, H, points[6], 3);
        points[2] = Utility.findNonConvexPoints(B, D, points[2], 2);
        points[4] = Utility.findNonConvexPoints(F, D, points[4], 2);
    }

    public void setProgressPoints(Point H, Point B, Point D, Point F, Point E) {
        points[7].set(H.x, H.y);
        points[3].set(E.x, E.y);
        points[1] = Utility.findLeftSidePoint(D, B, H, E, points[1]);
        points[5] = Utility.findRightSidePoint(D, F, H, E, points[5]);

        points[0] = Utility.findNonConvexPoints(points[1], points[7], points[0], 3);
        points[6] = Utility.findNonConvexPoints(points[5], points[7], points[6], 3);
        points[2] = Utility.findNonConvexPoints(points[1], points[3], points[2], 2);
        points[4] = Utility.findNonConvexPoints(points[5], points[3], points[4], 2);
    }

    public Path getPath() {
        path.reset();
        path.moveTo(points[0].x, points[0].y);              //A
        path.lineTo(points[1].x, points[1].y);              //B
        path.lineTo(points[2].x, points[2].y);              //C
        path.quadTo(points[3].x, points[3].y,               //D
                points[4].x, points[4].y);                  //E
        path.lineTo(points[5].x, points[5].y);              //F
        path.lineTo(points[6].x, points[6].y);              //G
        path.quadTo(points[7].x, points[7].y,               //H
                points[0].x, points[0].y);                  //A
        return path;
    }

    public int getLeafRadii() {
        return leafRadii;
    }

    public void setLeafRadii(int leafRadii) {
        this.leafRadii = leafRadii;
    }

    public boolean contains(float x, float y) {
        return PolygonUtils.isInside(points, new Point((int)x, (int)y));
    }
}
