package window;

// Accès aux chaînes de caractères internationales
import java.util.ResourceBundle;

// Lien entre le java et le fxml
import javafx.fxml.FXML;

// Gestion de la souris
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

// Elements présents dans paint_fx.fxml
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.canvas.Canvas;

// Modèle de données
import model.Adjacence;
import window.operations.*;

/**
 * Classe contrôleur pour l'interface décrite dans le document <b>interface.fxml</b>.
 *
 * Cette classe contient principalement les méthodes appelées en réaction aux événements utilisateur.
 * Une instance de cette classe est créée automatiquement chaque fois que le document <b>interface.fxml</b> est chargé.
 */
public class TopologyController {
  
  @FXML private CheckBox ckShBComp;
  @FXML private CheckBox ckShWComp;
  @FXML private ToggleButton toggleComponents;
  @FXML private ToggleButton togglePath;
  @FXML private ToggleButton toggleIsolated;
  @FXML private ToggleButton toggleArc;
  @FXML private ToggleButton toggleCurve;
  @FXML private ToggleButton toggleBorder;
  @FXML private ToggleButton toggleSimple;
  @FXML private RadioButton radioBlackComponentAdj4;
  @FXML private RadioButton radioBlackComponentAdj8;
  @FXML private RadioButton radioWhiteComponentAdj4;
  @FXML private RadioButton radioWhiteComponentAdj8;
  @FXML private CheckBox ckLinkBlackAndWhite;
  

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

  @FXML
  public void initialize () {
      this.gridManager = new GridManager (this.canvas, 5, 5, Adjacence.ADJ4, Adjacence.ADJ8);
      this.gridManager.displayGrid();
      this.radioBlackComponentAdj4.setSelected(true);
      this.radioWhiteComponentAdj8.setSelected(true);
      this.ckShBComp.setSelected(true);
      this.ckShWComp.setSelected(true);
  }

  @FXML
  public void onMousePressed (MouseEvent event) {
    int x = (int) event.getX();
    int y = (int) event.getY();
    if (event.getButton() == MouseButton.PRIMARY) {
      this.gridManager.mousePressed_primary(x,y);
    }
    else if (event.getButton() == MouseButton.SECONDARY) {
      this.gridManager.mousePressed_secondary(x,y);
    }
    this.gridManager.displayGrid();
  }

  @FXML
  private void onToggleChange () {
    Operation op;
    if (this.toggleComponents.isSelected()) op = new ShowComponents ();
    else if (this.toggleArc.isSelected()) op = new HighlightBlackArc ();
    else if (this.togglePath.isSelected()) op = new HighlightPath ();
    else if (this.toggleCurve.isSelected()) op = new HighlightBlackCurve ();
    else if (this.toggleBorder.isSelected()) op = new ShowBorders ();
    else if (this.toggleSimple.isSelected()) op = new ShowSimplePoints ();
    else if (this.toggleIsolated.isSelected()) op = new ShowIsolatedPoints ();
    else op = new NoopOperation ();
    
    if (op instanceof ShowComponents) {
      ShowComponents sc = (ShowComponents) op;
      sc.setShowBlackComponents(this.ckShBComp.isSelected());
      sc.setShowWhiteComponents(this.ckShWComp.isSelected());
    }
    this.operation = op;
    this.gridManager.setOperation(this.operation);
    this.gridManager.displayGrid();
  }
  
  @FXML
  private void onRadioAdjCompChange () {
    Adjacence adjBlack;
    Adjacence adjWhite;
    if (this.radioBlackComponentAdj4.isSelected()) adjBlack = Adjacence.ADJ4;
    else adjBlack = Adjacence.ADJ8;
    if (this.radioWhiteComponentAdj4.isSelected()) adjWhite = Adjacence.ADJ4;
    else adjWhite = Adjacence.ADJ8;
    this.gridManager.setM_adjacence(adjBlack);
    this.gridManager.setN_adjacence(adjWhite);
    this.gridManager.displayGrid();
  }
  
  @FXML
  private void onCheckboxLinkBlackAndWhiteChange () {
    this.gridManager.setLinkBlackAndWhite(this.ckLinkBlackAndWhite.isSelected());
    this.gridManager.displayGrid();
  }
  
  @FXML
  private void onCheckboxShownComponents () {
    if (this.operation instanceof ShowComponents) {
      ShowComponents op = (ShowComponents) this.operation;
      op.setShowBlackComponents(this.ckShBComp.isSelected());
      op.setShowWhiteComponents(this.ckShWComp.isSelected());
      this.gridManager.displayGrid();
    }
  }

  @FXML
  public void onShrinking () {
    this.gridManager.shrinking();
    this.gridManager.displayGrid();
  }

 }
