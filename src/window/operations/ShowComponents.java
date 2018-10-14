package window.operations;

import model.Point;
import java.util.HashMap;
import java.util.Iterator;

import model.Reseaux;
import java.util.Map;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class ShowComponents implements Operation {
  
  private boolean showBlackComponents;
  private boolean showWhiteComponents;

  public void setShowBlackComponents(boolean showBlackComponents) {
    this.showBlackComponents = showBlackComponents;
  }

  public void setShowWhiteComponents(boolean showWhiteComponents) {
    this.showWhiteComponents = showWhiteComponents;
  }

  public void processPoint (Point p) {
    return;
  }

  public Map<Point, Paint> makeColorMask (Reseaux r) {
    HashMap<Point, Paint> colorMap = new HashMap<Point, Paint> ();
    ArrayList<ArrayList<Point>> composants = r.computeComponents();
    Iterator<ArrayList<Point>> it = composants.iterator();

    // si rien à afficher
    if (!this.showBlackComponents && !this.showWhiteComponents)
      return colorMap;

    // enlève les composants qu'on affiche pas
    while (it.hasNext())
    {
      ArrayList<Point> comp = it.next();
      if (r.getColor(comp.get(0)) == Reseaux.BLANC && !this.showWhiteComponents)
        it.remove();
      else if (r.getColor(comp.get(0)) == Reseaux.NOIR && !this.showBlackComponents)
        it.remove();
    }

    // au cas où...
    if (composants.size() == 0)
      return colorMap;

    Color[] palette = this.makeRainbowPalette(composants.size());
    for (int i = 0; i < composants.size(); i++) {
      ArrayList<Point> comp = composants.get(i);
      for (Point p : comp) {
        if (r.isInGrid(p)) {
          colorMap.put(p, palette[i]);
    }}}
    return colorMap;
  }

  private Color[] makeRainbowPalette (int nbColors) throws IllegalArgumentException {
    if (nbColors <= 0)
      throw new IllegalArgumentException ("le nombre de couleurs doit être positif et non nul");

    Color[] palette = new Color [nbColors];
    double baseHue = 360/nbColors;
    for (int i = 0; i < nbColors; i++) {
      palette[i] = Color.hsb((i+1) * baseHue, 0.5, 0.5);
    }
    return palette;
  }
}
