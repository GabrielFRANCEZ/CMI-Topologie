package window.operations;

import window.operations.Operation;
import java.util.HashMap;

class NoopOperation implements Operation {
  public void processPoint (Point p) {
    return;
  }
  
  public Map<Point, Paint> makeColorMask (Reseaux r) {
    return new HashMap<Point, Paint> ();
  }
}
