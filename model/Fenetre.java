package model;


import java.util.Map;
import java.util.Hashtable;
import info.graphics.Color;
import info.graphics.Point;
import info.graphics.Line;
import info.graphics.Ellipse;

/**
 * Regroupe le réglage de la fenêtre et les méthodes de dessin.
 */
public class Fenetre {
    public final static int DIMENSION_CASE = 60;
    
    /** Marge pour tous les côtés (haut/bas/gauche/droite) */
    public final static int MARGE = 60;

    private info.graphics.Window fenetre;
    private Map<UnorderedPairOfPoints, Line> lignes;
    private Map<Point, Ellipse> points;
    
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
        this.lignes = new Hashtable<>();
        this.points = new Hashtable<>();
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
        Ellipse cercle = this.points.get(p);
        if (cercle != null && cercle.isFilled() != estPlein) {
            this.effacerPoint(p);
            cercle = null;
        }
        if (cercle == null) {
            // x et y sont les coordonnées du centre du cercle
            // drawEllipse fonctionne en encadrant l'éllipse dans un rectangle
            cercle = this.fenetre.drawEllipse(c.getX()-rayon, c.getY()-rayon, estPlein, Color.black, diametre, diametre);
            this.points.put(p, cercle);
        }
    }

    /**
     * Efface un point
     *
     * @param p un point
     */
    public void effacerPoint(Point p) {
        Ellipse cercle = this.points.remove(p);
        if (cercle != null) {
            this.fenetre.erase(cercle);
        }
    }

    /**
     * Dessine une ligne entre deux points. <br>
     * Représente une adjacence dans le contexte du programme.
     *
     * @param p1 un point
     * @param p2 un point
     */
    public void dessinerLigne(Point p1, Point p2) {
        UnorderedPairOfPoints pair = new UnorderedPairOfPoints(p1, p2);
        Line ligne = this.lignes.get(pair);
        if (ligne == null) {
            Point c1 = Fenetre.gridToWindow(p1);
            Point c2 = Fenetre.gridToWindow(p2);
            ligne = this.fenetre.drawLine(
                Color.black,
                c1.getX(), c1.getY(),
                c2.getX(), c2.getY()
            );
            this.lignes.put(pair, ligne);
        }
    }

    public void effacerLigne(Point p1, Point p2) {
        UnorderedPairOfPoints pair = new UnorderedPairOfPoints(p1, p2);
        Line ligne = this.lignes.remove(pair);
        if (ligne != null) {
            this.fenetre.erase(ligne);
        }
    }

    public void effacerFenetre() {
        for (Ellipse cercle : this.points.values())
            this.fenetre.erase(cercle);
        for (Line ligne : this.lignes.values())
            this.fenetre.erase(ligne);
        this.points.clear();
        this.lignes.clear();
    }
}
