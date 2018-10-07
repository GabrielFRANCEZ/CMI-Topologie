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

// Modèle de données
import Reseaux;

/**
 * Classe contrôleur pour l'interface décrite dans le document <b>interface.fxml</b>.
 *
 * Cette classe contient principalement les méthodes appelées en réaction aux événements utilisateur.
 * Une instance de cette classe est créée automatiquement chaque fois que le document <b>interface.fxml</b> est chargé.
 */
public class InterfaceController {
  @FXML private Stage stage;

  /** Modèle de la Grille affichée */
  private Reseaux model;

  /** Type d'opération sur la grille */
  private OperationType operationType;
  /** Opération sur la grille en cours (bouton de souris droit enfoncé) */
  private DrawOperation operation;

  /** Constructeur */
  public PaintFxController() {
      this.bundle = ResourceBundle.getBundle("interface");
      this.model = new Reseaux(1,1);
      this.operationType = noop;
      this.operation = null;
  }

 }
