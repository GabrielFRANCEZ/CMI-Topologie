package window.operations;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Point;
import model.Reseaux;

public class HighlightBlackCurve extends OperationOnPoints {

  @Override
  public Map<Point, Paint> makeColorMask(Reseaux r) {
    Map<Point, Paint> colorMask = new HashMap<Point, Paint> ();
    Point points [] = this.points.toArray(new Point [this.points.size()]);
    Color c;
    if (r.isASimpleBlackCurve(points)) {
      c = Color.GREEN;
    } else {
      c = Color.RED;
    }
    for (Point p : points) {
      colorMask.put(p, c);
    }
    return colorMask;
  }

}
