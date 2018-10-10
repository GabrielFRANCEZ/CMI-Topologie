package window.operations;

import java.util.Map;
import java.util.HashMap;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import model.Point;
import model.Reseaux;

public class HighlightPath extends OperationOnPoints {

  @Override
  public Map<Point, Paint> makeColorMask(Reseaux r) {
    Map<Point, Paint> colorMask = new HashMap<Point, Paint> ();
    Color color;
    return colorMask;
  }

}
