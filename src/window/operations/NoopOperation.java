package window.operations;

import window.operations.Operation;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Paint;
import model.Point;
import model.Reseaux;

public class NoopOperation implements Operation {
  public void processPoint (Point p) {
    return;
  }

  @Override
  public Map<Point, Paint> makeColorMask(Reseaux r) {
    return new HashMap<Point, Paint> ();
  }
}
