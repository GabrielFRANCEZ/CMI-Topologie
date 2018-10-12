package window.operations;

import javafx.scene.paint.Paint;
import model.Point;
import model.Reseaux;

public class ShowSimplePoints extends OperationOnAllPoints {

  @Override
  public Paint computePoint(Reseaux r, Point p) {
    if (r.isSimple(p)) {
      return OperationOnAllPoints.COLOR_TRUE;
    } else {
      return OperationOnAllPoints.COLOR_FALSE;
    }
  }

}
