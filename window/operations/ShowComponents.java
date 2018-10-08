package window.operations;

import window.operations.Operation;
import java.util.HashMap;

class ShowComponents implements Operation {
  public void processPoint (Point p) {
    return;
  }

  public Map<Point, Paint> makeColorMask (Reseaux r) {
    HashMap<Point, Paint> colorMap = new HashMap<Point, Paint> ();
    r.getComposants();
    return colorMap;
  }
}
