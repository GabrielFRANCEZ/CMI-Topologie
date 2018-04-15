
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

    /**
     * Convertit un point de la grille en point de la fenêtre
     *
     * @param p un point de la grille
     * @return un point de la fenêtre
     */
    public static Point gridToWindow (Point p) {
        return new Point(
            p.getX() * DIMENSION_CASE + MARGE,
            p.getY() * DIMENSION_CASE + MARGE
        );
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
        Point c = Fenetre.gridToWindow(p);
        // x et y sont les coordonnées du centre du cercle
        // drawEllipse fonctionne en encadrant l'éllipse dans un rectangle
        this.fenetre.drawEllipse(c.getX()-rayon, c.getY()-rayon, estPlein, Color.black, diametre, diametre);
    }

    /**
     * Dessine une ligne entre deux points. <br>
     * Représente une adjacence dans le contexte du programme.
     *
     * @param p1 un point
     * @param p2 un point
     */
    public void dessinerLigne(Point p1, Point p2) {
        Point c1 = Fenetre.gridToWindow(p1);
        Point c2 = Fenetre.gridToWindow(p2);
        this.fenetre.drawLine(
            Color.black,
            c1.getX(), c1.getY(),
            c2.getX(), c2.getY()
        );
    }
}
