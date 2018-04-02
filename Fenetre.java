
import info.graphics.Color;

public class Fenetre {
    public final static int DIMENSION_CASE = 60;
    
    /** Marge dans tous les côtés (haut/bas/gauche/droite) */
    public final static int MARGE = 60;

    private info.graphics.Window fenetre;
    
    public Fenetre (int nbLignes, int nbColonnes, String titre) {
        final int largeur = (nbLignes-1)*DIMENSION_CASE + 2*MARGE;
        final int hauteur = (nbColonnes-1)*DIMENSION_CASE + 2*MARGE;
        this.fenetre = new info.graphics.Window(largeur, hauteur, titre);
    }
    
    public void afficher() {
       this.fenetre.open(); 
    }

    // Méthodes pour convertir de coordonnées de grille à coordonnées de dessin.
    // Attention : code à changer si les coordonnées ne sont pas indépendantes

    public static int dessinX (int x) {
        return x*DIMENSION_CASE + MARGE;
    }

    public static int dessinY (int y) {
        return y*DIMENSION_CASE + MARGE;
    }
    
    // Méthodes de dessin, prennent tous des coordonnées de grilles.

    public void dessinerPoint(int x, int y, boolean estNoir) {
        final boolean estPlein = estNoir;
        final int diametre = DIMENSION_CASE/2;
        final int rayon = diametre/2;
        x = this.dessinX(x);
        y = this.dessinY(y);
        // x et y sont les coordonnées du centre du cercle
        // drawEllipse fonctionne en encadrant l'éllipse dans un rectangle
        this.fenetre.drawEllipse(x-rayon, y-rayon, estPlein, Color.black, diametre, diametre);
    }

    public void dessinerLigne(int x1, int y1, int x2, int y2) {
        this.fenetre.drawLine(
            Color.black,
            this.dessinX(x1), this.dessinY(y1),
            this.dessinX(x2), this.dessinY(y2)
        );
    }
}
