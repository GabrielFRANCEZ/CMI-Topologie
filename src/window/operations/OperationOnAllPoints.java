package window.operations;

import java.util.Map;
import java.util.HashMap;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import model.Point;
import model.Reseaux;

public abstract class OperationOnAllPoints implements Operation {
  
  public static final Color COLOR_FALSE = Color.RED;
  public static final Color COLOR_TRUE = Color.GREEN;

  public void processPoint (Point p) {
    return;
  }

  public Map<Point, Paint> makeColorMask (Reseaux r) {
    Map<Point, Paint> colorMask = new HashMap<Point, Paint> ();
    for (int i = 0; i < r.getSizeX(); i++) {
      for (int j = 0; j < r.getSizeY(); j++) {
        Point p = new Point (i,j);
        colorMask.put(p, this.computePoint(r, p));
      }
    }
    return colorMask;
  }

  public abstract Paint computePoint (Reseaux r, Point p);
}
