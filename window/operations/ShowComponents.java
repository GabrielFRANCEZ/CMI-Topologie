package window.operations;

import window.operations.Operation;
import java.util.HashMap;

class ShowComponents extends OperationOnAllPoints {
  public void processPoint (Point p) {
    return;
  }

  public Map<Point, Paint> makeColorMask (Reseaux r) {
    HashMap<Point, Paint> colorMap = new HashMap<Point, Paint> ();
    ArrayList<ArrayList<Point>> composants = r.getComposants();
    for (int i = 0; i < composants.size(); i++) {
      ArrayList<Point> comp = composants.get(i);
      for (int j = 0; j < comp.size(); j++) {
        Point p = new Point (i,j);
        colorMap.put(p, Color.BLUE);.
      }
    }
    return colorMap;
  }

  public Map<Point, Paint> makeColorMask (Reseaux r) {
    return null;
  }
}
