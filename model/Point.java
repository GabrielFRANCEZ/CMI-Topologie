package model;

public class Point {
  protected int x;
  protected int y;

  public Point (int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point (Point p) {
    this.x = p.x;
    this.y = p.y;
  }

  public int getX () {
    return this.x;
  }

  public int getY () {
    return this.y;
  }

  public int hashCode () {
    return this.toString().hashCode();
  }

  public String toString () {
    return "Point(" + this.x + ", " + this.y + ")";
  }

  public boolean equals (Object o) {
    boolean res = false;
    if (this == o) res = true;
    else if (o instanceof Point) {
      Point p = (Point) o;
      res = this.x == p.x
        &&  this.y == p.y;
    }
    return res;
  }
}
