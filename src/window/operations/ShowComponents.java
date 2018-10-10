package window.operations;

import model.Point;
import java.util.HashMap;
import model.Reseaux;
import java.util.Map;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class ShowComponents implements Operation {

  public void processPoint (Point p) {
    return;
  }

  public Map<Point, Paint> makeColorMask (Reseaux r) {
    HashMap<Point, Paint> colorMap = new HashMap<Point, Paint> ();
    ArrayList<ArrayList<Point>> composants = r.getComposants();
    for (int i = 0; i < composants.size(); i++) {
      ArrayList<Point> comp = composants.get(i);
      for (int j = 0; j < comp.size(); j++) {
        Point p = comp.get(j);
        if (p.getX() >= 0 && p.getY() >= 0 && p.getX() < r.getNbLignes() && p.getY() < r.getNbColonnes()) {
          colorMap.put(p, Color.RED);
        }

      }
    }
    return colorMap;
  }
}
