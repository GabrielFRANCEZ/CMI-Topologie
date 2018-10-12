
package window;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Reseaux;
import window.operations.Operation;
import window.operations.NoopOperation;
import model.Point;
import model.Adjacence;
import java.util.Map;

public class GridManager {
  public final static int DIMENSION_CASE = 60;
  /** Marge pour tous les côtés (haut/bas/gauche/droite) */
  public final static int MARGE = 60;

  private Canvas canvas;
  private Reseaux reseaux;
  private Operation operation;
  private boolean linkBlackAndWhite;

  public GridManager (Canvas canvas, int nbLines, int nbColumns, Adjacence adj_black, Adjacence adj_white) {
    this.canvas = canvas;
    this.reseaux = new Reseaux(nbLines, nbColumns, adj_black, adj_white);
    final int width = (nbLines-1)*DIMENSION_CASE + 2*MARGE;
    final int height = (nbColumns-1)*DIMENSION_CASE + 2*MARGE;
    this.canvas.setWidth(width);
    this.canvas.setHeight(height);
    this.operation = new NoopOperation ();
  }

  public Reseaux getReseaux () {
    return this.reseaux; // TODO Copie
  }
  
  public void setM_adjacence (Adjacence adj) {
    this.reseaux.setM_adjacence(adj);
  }
  
  public void setN_adjacence (Adjacence adj) {
    this.reseaux.setN_adjacence(adj);
  }

  public void setOperation(Operation operation) {
    this.operation = operation;
  }

  public void setLinkBlackAndWhite (boolean linkBlackAndWhite) {
    this.linkBlackAndWhite = linkBlackAndWhite;
  }

  public Point gridToWindow (Point p) {
    return new Point (
      p.getX() * DIMENSION_CASE + MARGE,
      p.getY() * DIMENSION_CASE + MARGE
    );
  }

    public Point windowToGrid (Point p) {
      return new Point (
        (p.getX() - MARGE) / DIMENSION_CASE,
        (p.getY() - MARGE) / DIMENSION_CASE
      );
    }

  private void drawPoint (Point p, boolean isBlack) {
    GraphicsContext gc = this.canvas.getGraphicsContext2D();
    final int diametre = DIMENSION_CASE/2;
    final int rayon = diametre/2;
    Point c = this.gridToWindow(p);
    if (isBlack) {
      gc.setFill(Color.BLACK);
      gc.fillOval(c.getX()-rayon, c.getY()-rayon, diametre, diametre);
    } else {
      gc.setStroke(Color.BLACK);
      gc.strokeOval(c.getX()-rayon, c.getY()-rayon, diametre, diametre);
    }
  }

  private void drawLine (Point p1, Point p2) {
    GraphicsContext gc = this.canvas.getGraphicsContext2D();
    p1 = this.gridToWindow(p1);
    p2 = this.gridToWindow(p2);
    gc.setStroke(Color.BLACK);
    gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
  }
  
  private void drawAdjacencies (Point p1) {
    Point[] pointsAround = this.reseaux.pointsAround(p1);
    for (Point p2 : pointsAround) {
      if (this.reseaux.adjacence(p1, p2)
          && (this.linkBlackAndWhite || this.reseaux.getColor(p1) == this.reseaux.getColor(p2))) {
        this.drawLine(p1,p2);
    }}
  }
  
  private void drawColorMask (Point p, Paint paint) {
    p = this.gridToWindow(p);
    p = new Point (p.getX() - DIMENSION_CASE/2, p.getY() - DIMENSION_CASE/2);
    GraphicsContext gc = this.canvas.getGraphicsContext2D();
    gc.setFill(paint);
    gc.fillRect(p.getX(), p.getY(), DIMENSION_CASE, DIMENSION_CASE);
  }

  public void displayGrid () {
    this.displayGrid(this.operation.makeColorMask(this.reseaux), this.linkBlackAndWhite);
  }

  public void displayGrid (Map<Point, Paint> colorMask, boolean linkBlackAndWhite) {
    // effacer tout
    this.canvas.getGraphicsContext2D()
                .clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    
    // dessiner le fond de couleur (si il y en a un)
    for (Map.Entry<Point, Paint> entry : colorMask.entrySet())
      this.drawColorMask(entry.getKey(), entry.getValue());
    
    // dessiner la grille
    for(int i = 0 ; i < this.reseaux.getSizeX() ; i++) {
      for(int j = 0 ; j < this.reseaux.getSizeY() ; j++){
        Point p1 = new Point(i,j);
        this.drawPoint(p1, this.reseaux.getColor(p1));
        this.drawAdjacencies(p1);
    }}
  }

  public void shrinking () {
    this.reseaux.shrinking();
  }

  public void mousePressed_primary(int x, int y) {
    Point p = this.windowToGrid(new Point(x + DIMENSION_CASE/2,y + DIMENSION_CASE/2));
    this.reseaux.setCouleur(p, !this.reseaux.getColor(p));
  }
  
  public void mousePressed_secondary (int x, int y) {
    Point p = this.windowToGrid(new Point(x + DIMENSION_CASE/2,y + DIMENSION_CASE/2));
    this.operation.processPoint(p);
  }
}
