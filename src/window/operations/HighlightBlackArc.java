package window.operations;

import java.util.Map;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Point;
import model.Reseaux;

public class HighlightBlackArc extends OperationOnPoints {

  @Override
  public Map<Point, Paint> makeColorMask(Reseaux r) {
    Map<Point, Paint> colorMask = new HashMap<Point, Paint> ();
    Point[] points = this.points.toArray(new Point [this.points.size()]);
    Color c;
    if (r.isASimpleBlackArc(points)) {
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
