package window.operations;

import javafx.scene.paint.Paint;
import model.Point;
import model.Reseaux;

public class ShowIsolatedPoints extends OperationOnAllPoints {

  @Override
  public Paint computePoint(Reseaux r, Point p) {
    if (r.isAnIsolatedPoint(p))
      return OperationOnAllPoints.COLOR_TRUE;
    else
      return OperationOnAllPoints.NO_COLOR;
  }

}
