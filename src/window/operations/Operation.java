package window.operations;

import model.Point;
import model.Reseaux;
import javafx.scene.paint.Paint;
import java.util.Map;


public interface Operation {
  public void processPoint (Point p);

  public Map<Point, Paint> makeColorMask (Reseaux r);
}
