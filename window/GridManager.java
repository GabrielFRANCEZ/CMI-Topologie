
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Reseaux;
import model.Point;
import model.Adjacence;
import java.util.Map;

public class GridManager {
  public final static int DIMENSION_CASE = 60;
  /** Marge pour tous les côtés (haut/bas/gauche/droite) */
  public final static int MARGE = 60;

  private Canvas canvas;
  private Reseaux reseaux;

  public GridManager (Canvas canvas, int nbLines, int nbColumns, Adjacence adj_black, Adjacence adj_white) {
    this.canvas = canvas;
    this.reseaux = new Reseaux(nbLines, nbColumns, adj_black, adj_white);
    final int width = (nbLines-1)*DIMENSION_CASE + 2*MARGE;
    final int height = (nbColumns*-1)*DIMENSION_CASE + 2*MARGE;
    this.canvas.resize(width, height);
  }

  public static Point gridToWindow (Point p) {
    return new Point (
      p.getX() * DIMENSION_CASE + MARGE,
      p.getY() * DIMENSION_CASE + MARGE
    );
  }

    public static Point windowToGrid (Point p) {
      return new Point (
        (p.getX() - MARGE) / DIMENSION_CASE,
        (p.getY() - MARGE) / DIMENSION_CASE
      );
    }

  private void drawPoint (Point p, boolean isBlack) {
    GraphicsContext gc = this.canvas.getGraphicsContext2D();
    final int diametre = DIMENSION_CASE/2;
    final int rayon = diametre/2;
    Point c = GridManager.gridToWindow(p);
    if (isBlack) {
      gc.setFill(Color.BLACK);
      gc.fillOval(c.getX()-rayon, c.getY()-rayon, c.getX()+rayon, c.getY()+rayon);
    } else {
      gc.setStroke(Color.BLACK);
      gc.strokeOval(c.getX()-rayon, c.getY()-rayon, c.getX()+rayon, c.getY()+rayon);
    }
  }

  private void drawLine (Point p1, Point p2) {
    GraphicsContext gc = this.canvas.getGraphicsContext2D();
    gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
  }

  public void displayGrid (Map<Point, Paint> colorMask) {
    for (Map.Entry<Point, Paint> entry : colorMask.entrySet()) {
      Point p = GridManager.gridToWindow(entry.getKey());
      GraphicsContext gc = this.canvas.getGraphicsContext2D();
      gc.setFill(entry.getValue());
      gc.fillRect(p.getX(), p.getY(), DIMENSION_CASE, DIMENSION_CASE);
    }
    for(int i = 0 ; i < this.reseaux.getNbLignes() ; i++) {
      for(int j = 0 ; j < this.reseaux.getNbColonnes() ; j++){
        Point p1 = new Point(i,j);
        this.drawPoint(p1, this.reseaux.getCouleur(p1));
        Point[] voisins = this.reseaux.voisins8(p1);
        for (int k = 0; k < voisins.length; k++) {
          Point p2 = voisins[k];
          if (this.reseaux.adjacence(p1, p2)) {
            this.drawLine(p1,p2);
          }
        }
      }
    }
  }

//  private void displayModel () {
//    this.canvas.getGraphicsContext2D()
//         .clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
//     this.displayHighlight();
//     this.displayGrid();
//  }
}
