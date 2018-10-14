package window.operations;

import javafx.scene.paint.Paint;
import model.Reseaux;
import model.Point;

public class ShowBorders extends OperationOnAllPoints {
  @Override
  public Paint computePoint (Reseaux r, Point p) {
    if (r.isABorderPoint(p)) {
      return OperationOnAllPoints.COLOR_TRUE;
    } else {
      return OperationOnAllPoints.NO_COLOR;
    }
  }
}
