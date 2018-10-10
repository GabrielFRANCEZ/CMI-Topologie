package window;

// Accès aux chaînes de caractères internationales
import java.util.ResourceBundle;

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
import window.operations.*;

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

  /** Modèle de la Grille affichée */
  private GridManager gridManager;

  /** Opération sur la grille en cours (bouton de souris droit enfoncé) */
  private Operation operation;

  /** Constructeur */
  public TopologyController() {
      ResourceBundle.getBundle("interface");
      this.operation = new NoopOperation ();
  }

  public void setStage (Stage stage) {
      this.stage = stage;
  }

  public void onWindowShown () {
      this.gridManager = new GridManager (this.canvas, 5, 5, Adjacence.ADJ4, Adjacence.ADJ8);
      this.gridManager.displayGrid();
  }

  @FXML
  public void onMousePressed (MouseEvent event) {
    if (event.getButton() == MouseButton.PRIMARY) {
      int x = (int) event.getX();
      int y = (int) event.getY();
      this.gridManager.mousedown_primary(x,y);
    }
    this.gridManager.displayGrid();
  }

  @FXML
  public void onToggleChange () {
    Operation op;
    if (this.toggleComponents.isSelected()) op = new ShowComponents ();
    else if (this.toggleArc.isSelected()) op = new HighlightBlackArc ();
    else if (this.togglePath.isSelected()) op = new HighlightPath ();
    else if (this.toggleCurve.isSelected()) op = new HighlightBlackCurve ();
    else if (this.toggleBorder.isSelected()) op = new ShowBorders ();
    else if (this.toggleSimple.isSelected()) op = new ShowSimplePoints ();
    else if (this.toggleIsolated.isSelected()) op = new ShowIsolatedPoints ();
    else op = new NoopOperation ();
    this.operation = op;
    this.gridManager.setOperation(this.operation);
    this.gridManager.displayGrid();
  }

  @FXML
  public void onShrinking () {
    this.gridManager.shrinking();
    this.gridManager.displayGrid();
  }

 }
