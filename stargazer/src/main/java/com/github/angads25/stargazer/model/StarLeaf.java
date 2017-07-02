package com.github.angads25.stargazer.model;

import android.graphics.Path;
import android.graphics.Point;

import com.github.angads25.stargazer.utils.Utility;

/**
 * <p>
 * Created by Angad on 02-07-2017.
 * </p>
 */

public class StarLeaf {
    private Path path;
    private Point A, B, C, D;
    private Point E, F, G, H;

    public StarLeaf() {
        A = new Point();
        C = new Point();
        E = new Point();
        G = new Point();

        path = new Path();
    }

    public void setPoints(Point H, Point B, Point D, Point F) {
        this.H = H;
        this.B = B;
        this.D = D;
        this.F = F;

        this.A = Utility.findNonConvexPoints(B, H, A, 3);
        this.G = Utility.findNonConvexPoints(F, H, G, 3);
        this.C = Utility.findNonConvexPoints(B, D, C, 2);
        this.E = Utility.findNonConvexPoints(F, D, E, 2);
    }

    public void setProgressPoints(Point H, Point B, Point D, Point F, Point E) {
        this.H = H;
        this.D = E;
        this.B = Utility.findLeftSidePoint(D, B, H, E, this.B);
        this.F = Utility.findRightSidePoint(D, F, H, E, this.F);

        this.A = Utility.findNonConvexPoints(this.B, this.H, this.A, 3);
        this.G = Utility.findNonConvexPoints(this.F, this.H, this.G, 3);
        this.C = Utility.findNonConvexPoints(this.B, this.D, this.C, 2);
        this.E = Utility.findNonConvexPoints(this.F, this.D, this.E, 2);
    }

    public Path getPath() {
        path.reset();
        path.moveTo(A.x, A.y);              //A
        path.lineTo(B.x, B.y);              //B
        path.lineTo(C.x, C.y);              //C
        path.quadTo(D.x, D.y, E.x, E.y);    //D, E
        path.lineTo(F.x, F.y);              //F
        path.lineTo(G.x, G.y);              //G
        path.quadTo(H.x, H.y, A.x, A.y);    //H
        return path;
    }
}
