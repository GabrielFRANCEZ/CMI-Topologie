package window.operations;

import java.util.ArrayList;
import model.Point;
import model.Reseaux;
import java.util.Map;
import javafx.scene.paint.Paint;


public abstract class OperationOnPoints implements Operation {
  protected ArrayList<Point> points;

  public OperationOnPoints () {
    this.points = new ArrayList<Point> ();
  }

  @Override
  public void processPoint (Point p) {
    if (this.points.contains(p))
      this.points.remove(p);
    else
      this.points.add(p);
  }

  public abstract Map<Point, Paint> makeColorMask (Reseaux r);
}
