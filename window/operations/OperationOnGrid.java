public abstract class OperationOnAllPoints implements Operation {
  public void processPoint (Point p) {
    return;
  }

  public Map<Point, Paint> makeColorMask (Reseaux r) {
    Map<Point, Paint> colorMask = new HashMap<Point, Paint> ();
    for (int i = 0; i < r.getNbLignes(); i++) {
      for (int j = 0; j < r.getNbColonnes(); j++) {
        Point p = new Point (i, j);
        Paint paint = this.computePoint(p);
        if (paint != null) {
          colorMask.put(p, paint);
        }
      }
    }
  }

  public abstract Paint computePoint (Point p);
}
