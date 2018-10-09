package window.operations;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import model.Reseaux;
import model.Point;

public class ShowBorders extends OperationOnAllPoints {
  public Paint computePoint (Reseaux r, Point p) {
    if (r.isABorderPoint(p)) {
      return Color.GREEN;
    } else {
      return Color.RED;
    }
  }
}
