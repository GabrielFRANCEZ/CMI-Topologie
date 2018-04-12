
import info.graphics.Color;
import info.graphics.Point;

/**
 * Regroupe le réglage de la fenêtre et les méthodes de dessin.
 */
public class Fenetre {
    public final static int DIMENSION_CASE = 60;
    
    /** Marge pour tous les côtés (haut/bas/gauche/droite) */
    public final static int MARGE = 60;

    private info.graphics.Window fenetre;
    
    /**
     * Constructeur de Fenetre
     *
     * @param nbLignes nombre de lignes de la matrice
     * @param nbColonnes nombre de colonnes de la matrice
     * @param titre le titre de la fenêtre
     */
    public Fenetre (int nbLignes, int nbColonnes, String titre) {
        final int largeur = (nbLignes-1)*DIMENSION_CASE + 2*MARGE;
        final int hauteur = (nbColonnes-1)*DIMENSION_CASE + 2*MARGE;
        this.fenetre = new info.graphics.Window(largeur, hauteur, titre);
    }
    
    /**
     * Affiche la fenêtre
     */
    public void afficher() {
       this.fenetre.open(); 
    }

    // Méthodes pour convertir de coordonnées de grille à coordonnées de dessin.
    // Attention : code à changer si les coordonnées ne sont pas indépendantes

    /**
     * Convertit le numéro de colonne en abscisse de la fenêtre
     *
     * @param x le numéro de colonne (commençant à 0)
     * @return l'abscisse dans la fenêtre
     */
    public static int dessinX (int x) {
        return x*DIMENSION_CASE + MARGE;
    }

    /**
     * Convertit le numéro de ligne en ordonnée de la fenêtre
     *
     * @param y le numéro de liigne
     * @return l'ordonnée dans la fenêtre
     */
    public static int dessinY (int y) {
        return y*DIMENSION_CASE + MARGE;
    }
    
    // Méthodes de dessin, prennent tous des coordonnées de grilles.

    /**
     * Dessine un point. <br>
     * Utilise des cercles remplis (NOIR) ou non (BLANC).
     *
     * @param p un point
     * @param estNoir true si le point est noir, false s'il est blanc
     */
    public void dessinerPoint(Point p, boolean estNoir) {
        final boolean estPlein = estNoir;
        final int diametre = DIMENSION_CASE/2;
        final int rayon = diametre/2;
        int x = this.dessinX(p.getX());
        int y = this.dessinY(p.getY());
        // x et y sont les coordonnées du centre du cercle
        // drawEllipse fonctionne en encadrant l'éllipse dans un rectangle
        this.fenetre.drawEllipse(x-rayon, y-rayon, estPlein, Color.black, diametre, diametre);
    }

    /**
     * Dessine une ligne entre deux points. <br>
     * Représente une adjacence dans le contexte du programme.
     *
     * @param p1 un point
     * @param p2 un point
     */
    public void dessinerLigne(Point p1, Point p2) {
        this.fenetre.drawLine(
            Color.black,
            this.dessinX(p1.getX()), this.dessinY(p1.getY()),
            this.dessinX(p2.getX()), this.dessinY(p2.getY())
        );
    }
}
