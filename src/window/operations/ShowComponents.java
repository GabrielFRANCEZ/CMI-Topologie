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
    Color[] palette = this.makeRainbowPalette(composants.size());
    for (int i = 0; i < composants.size(); i++) {
      ArrayList<Point> comp = composants.get(i);
      for (Point p : comp) {
        if (r.isInGrid(p)) {
          colorMap.put(p, palette[i]);
        }
      }
    }
    return colorMap;
  }

  private Color[] makeRainbowPalette (int nbColors) {
    Color[] palette = new Color [nbColors];
    double baseHue = 360/nbColors;
    for (int i = 0; i < nbColors; i++) {
      palette[i] = Color.hsb((i+1) * baseHue, 0.5, 0.5);
    }
    return palette;
  }
}
