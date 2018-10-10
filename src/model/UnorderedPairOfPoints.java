package model;

import model.Point;

public class UnorderedPairOfPoints {
    private Point p1;
    private Point p2;

    public UnorderedPairOfPoints (Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getPoint1() {
        return this.p1;
    }

    public Point getPoint2() {
        return this.p2;
    }

    @Override
    public boolean equals (Object obj) {
        boolean eq = false;
        if (this == obj)
            eq = true;
        else if (obj instanceof UnorderedPairOfPoints) {
            UnorderedPairOfPoints pair = (UnorderedPairOfPoints) obj;
            eq = (this.p1.equals(pair.p1) && this.p2.equals(pair.p2))
              || (this.p1.equals(pair.p2) && this.p2.equals(pair.p1));
        }
        return eq;
    }

    @Override
    public int hashCode() {
        return this.p1.hashCode() * this.p2.hashCode();
    }
}
