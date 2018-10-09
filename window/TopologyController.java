package window;

// Accès aux chaînes de caractères internationales
import java.util.ResourceBundle;

// Permet de quitter l'application (exit)
import javafx.application.Platform;

// Lien entre le java et le fxml
import javafx.fxml.FXML;

// Gestion de la souris
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

// Elements présents dans paint_fx.fxml
import javafx.stage.Stage;
import javafx.scene.control.ToggleButton;
import javafx.scene.canvas.Canvas;

// Modèle de données
import model.Reseaux;
import model.Adjacence;
import model.Point;
import window.operations.OperationType;
import window.operations.Operation;

// dessin dans le canvas
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe contrôleur pour l'interface décrite dans le document <b>interface.fxml</b>.
 *
 * Cette classe contient principalement les méthodes appelées en réaction aux événements utilisateur.
 * Une instance de cette classe est créée automatiquement chaque fois que le document <b>interface.fxml</b> est chargé.
 */
public class TopologyController {
  @FXML private Stage stage;
  @FXML private ToggleButton toggleComponents;
  @FXML private ToggleButton togglePath;
  @FXML private ToggleButton toggleIsolated;
  @FXML private ToggleButton toggleArc;
  @FXML private ToggleButton toggleCurve;
  @FXML private ToggleButton toggleBorder;
  @FXML private ToggleButton toggleSimple;
  @FXML private Canvas canvas;

  private ResourceBundle bundle;

  /** Modèle de la Grille affichée */
  private GridManager gridManager;

  /** Type d'opération sur la grille */
  private OperationType operationType;
  /** Opération sur la grille en cours (bouton de souris droit enfoncé) */
  private Operation operation;

  /** Constructeur */
  public TopologyController() {
      this.bundle = ResourceBundle.getBundle("interface");
      //this.gridManager = new GridManager (this.canvas, 1, 1, Adjacence.ADJ4, Adjacence.ADJ8);
      //this.operationType = OperationType.NOOP;
      //this.gridManager.displayGrid();
      this.operation = new window.operations.ShowBorders ();
  }

  public void setStage (Stage stage) {
      this.stage = stage;
  }

  public void onWindowShown () {
      this.gridManager = new GridManager (this.canvas, 5, 5, Adjacence.ADJ4, Adjacence.ADJ8);
      Reseaux r = this.gridManager.getReseaux();
      this.gridManager.displayGrid(this.operation.makeColorMask(r));
  }

  @FXML
  public void onMousePressed (MouseEvent event) {
    if (event.getButton() == MouseButton.PRIMARY) {
      int x = (int) event.getX();
      int y = (int) event.getY();
      this.gridManager.mousedown_primary(x,y);
    }
    Reseaux r = this.gridManager.getReseaux();
    this.gridManager.displayGrid(this.operation.makeColorMask(r));
  }

  @FXML
  public void onToggleChange () {
    if (this.toggleComponents.isSelected());
    else if (this.toggleArc.isSelected());
    else if (this.togglePath.isSelected());
    else if (this.toggleCurve.isSelected());
    else if (this.toggleBorder.isSelected());
    else if (this.toggleSimple.isSelected());
    else if (this.toggleIsolated.isSelected());
    else;

  }

  @FXML
  public void onShrinking () {
    this.gridManager.shrinking();
    Reseaux r = this.gridManager.getReseaux();
    this.gridManager.displayGrid(this.operation.makeColorMask(r));
  }

 }
