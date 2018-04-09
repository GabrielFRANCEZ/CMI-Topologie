import java.util.ArrayList;
import info.graphics.Line;
import info.graphics.Window;
import info.graphics.Color;
import info.graphics.Point;
public class Reseaux {
    public final boolean BLANC = false;
    public final boolean NOIR = true;

    /** Adjacence entre deux points noirs */
    private Adjacence m_adjacence;

    /** Adjacence entre deux points blancs, ou un point noir et un point blanc */
    private Adjacence n_adjacence;

    /** point noir == true; point blanc == false */
    public boolean[][] couleurs;

    /** fenetre de dessin, facilite les conversions de coordonnées */
    public Fenetre fenetre;

    /**
     * Reseaux Constructor
     *
     * @param nbLignes un paramètre
     * @param nbColonnes un paramètre
     * @param madj un paramètre
     * @param nadj un paramètre
     */
    public Reseaux(int nbLignes, int nbColonnes, Adjacence madj, Adjacence nadj) {
        // matrice de taille fixe pour l'instant
        this.couleurs = new boolean[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                couleurs[i][j] = BLANC;
            }
        }

        this.fenetre = new Fenetre(nbLignes, nbColonnes, "Discrete Topology");

        this.n_adjacence = nadj;
        this.m_adjacence = madj;

        this.fenetre.afficher();
    }

    /**
     * Méthode getCouleur
     *
     * @param point un paramètre
     * @return le retour
     */
    public boolean getCouleur (Point point) {
        // Le "background" blanc
        int x = point.getX();
        int y = point.getY();
        if (x < 0 || x >= this.couleurs.length
        || y < 0 || y >= this.couleurs[0].length) { // [0] ?
            return BLANC;
        }
        return this.couleurs[x][y];
    }

    /**
     * Méthode setCouleur
     *
     * @param point un paramètre
     * @param couleur un paramètre
     */
    public void setCouleur (Point point, boolean couleur) {
        int x = point.getX();
        int y = point.getY();
        this.couleurs[x][y] = couleur;
    }

    /**
     * Méthode getComposants
     *
     * @return le retour
     */
    public ArrayList<ArrayList<Point>> getComposants () {
        ArrayList<Point> points = new ArrayList<Point> ();
        for (int x = 0; x < this.couleurs.length; x++) {
            for (int y = 0; y < this.couleurs[0].length; y++) {
                points.add(new Point(x,y));
            }
        }
        ArrayList<ArrayList<Point>> comps = new ArrayList<ArrayList<Point>>();
        while (points.size() > 0) {
            ArrayList<Point> composant = new ArrayList<Point> ();
            composant.add(points.remove(0));
            for (int i = 0; i < composant.size(); i++) {
                Point pointComposant = composant.get(i);
                int x1 = pointComposant.getX(); int y1 = pointComposant.getY();
                for (int j = 0; j < points.size(); j++) {
                    Point point = points.get(j);
                    int x2 = point.getX(); int y2 = point.getY();
                    if (this.couleurs[x1][y1] == this.couleurs[x2][y2] && this.adjacence(pointComposant, point)) {
                        composant.add(point);
                        points.remove(j);
                        j--;
                    }
                }
            }
            comps.add(composant);
        }
        return comps;
    }

    // N'a aucun sens de mettre en public
    // car les deux points donnés peuvent ne pas être voisins
    /**
     * Méthode d'aide interne pour déterminer le type d'adjacence possible
     */
    private Adjacence getTypeAdjacence(Point p1, Point p2) {
        Adjacence adjacence;
        if (this.getCouleur(p1) == NOIR && this.getCouleur(p2) == NOIR) {
            adjacence = this.m_adjacence;
        } else {
            adjacence = this.n_adjacence;
        }
        return adjacence;
    }

    /**
     * Méthode adjacence
     *
     * @param p1 un paramètre
     * @param p2 un paramètre
     * @return le retour
     */
    public boolean adjacence (Point p1, Point p2) {
        final int dx = Math.abs(p2.getX()-p1.getX());
        final int dy = Math.abs(p2.getY()-p1.getY());
        boolean estVoisin = false;
        switch (this.getTypeAdjacence(p1,p2)) {
            case ADJ4: estVoisin = dx + dy == 1; break;
            case ADJ8: estVoisin = dx + dy == 1 || (dx == 1 && dy == 1); break;
        }
        return estVoisin;
    }

    /**
     * Donne les voisins selon la 8-adjacence : N(p)
     */
    public Point[] voisins8 (Point p) {
        int x = p.getX();
        int y = p.getY();
        Point[] voisins = {
                new Point (x+1,y),new Point(x-1,y),new Point(x,y+1),new Point(x,y-1),
                new Point (x+1,y+1),new Point (x+1,y-1),new Point(x-1,y+1),new Point(x-1,y-1)
            };
        return voisins;
    }
    
    public Point[] voisins (Point p) {
        Point[] voisins8 = this.voisins8(p);
        ArrayList<Point> voisins = new ArrayList<Point>();
        for (int i = 0; i < voisins8.length; i++) {
            if (this.adjacence(p, voisins8[i])) {
                voisins.add(voisins8[i]);
            }
        }
        return voisins.toArray(new Point[voisins.size()]);
    }

    /**
     * Méthode dessinerReseau
     *
     */
    public void dessinerReseau(){
        for(int i = 0 ; i < this.couleurs.length ; i++) {
            for(int j = 0 ; j < this.couleurs[i].length ; j++){
                Point p1 = new Point(i,j);
                this.fenetre.dessinerPoint(p1, this.couleurs[i][j]);
                Point[] voisins = this.voisins8(p1);
                for (int k = 0; k < voisins.length; k++) {
                    Point p2 = voisins[k];
                    if (this.adjacence(p1, p2)) { 
                        this.fenetre.dessinerLigne(p1,p2);
                    }
                }
            }
        }

    }

    /**
     * Méthode pathIsPossible
     *
     * @param p1 un paramètre
     * @param p2 un paramètre
     * @return le retour
     */
    public boolean pathIsPossible(Point p1, Point p2){
        boolean resultat = false;
        int compteur = 0;
        ArrayList<ArrayList<Point>> composants = this.getComposants();
        for (int i = 0; i < composants.size()&& compteur == 0; i++) {
            ArrayList<Point> comp = composants.get(i);
            for (int j = 0; j < comp.size() ; j++) {
                Point coords = comp.get(j);
                if(coords.equals(p1)){
                    compteur++;
                }
                else if (coords.equals (p2)){
                    compteur++;
                }
            }
        }
        if(compteur==2){
            resultat = true;
            System.out.println("Il existe bien un chemin possible entre "+p1+" et " + p2 );
        }
        else{
            System.out.println("Il n'existe pas de chemin possible entre "+p1+" et " + p2 );
        }
        return resultat;

    }

    /**
     * Méthode isAnIsolatedPoint
     *
     * @param p1 un paramètre
     * @return le retour
     */
    public boolean isAnIsolatedPoint(Point p1){
        boolean resultat = false;
        int compteur = 0;
        ArrayList<ArrayList<Point>> composants = this.getComposants();
        for (int i = 0; i < composants.size()&& compteur == 0; i++) {
            ArrayList<Point> comp = composants.get(i);
            Point p2 = comp.get(0);
            if(comp.size() == 1 && p1.equals(p2) && this.getCouleur(p2) == NOIR){ // on ne compte pas les trous comme point isolé
                compteur++;
            }
        }
        if(compteur==1){
            resultat = true;
            System.out.println(""+p1+" est un point isolé ");
        }
        else{
            System.out.println(""+p1+" n'est pas un point isolé ");
        }
        return resultat;

    }

    /**
     * Méthode isASimpleBlackArc
     *
     * @param points un paramètre
     * @return le retour
     */
    public boolean isASimpleBlackArc(Point[] points){
        boolean resultat = false;
        int compteur = 0;
        ArrayList<Point> extremite = new ArrayList<Point>();
        ArrayList<Point> interne = new ArrayList<Point>();
        boolean sontTousNoirs = true;
        for(int x = 0; x<points.length; x++){
            if (this.getCouleur(points[x]) == BLANC){
                sontTousNoirs = false;
            }
        }
        if (sontTousNoirs) {
            for (int i = 0; i<points.length; i++){
                compteur = 0;
                for (int j = 0; j<points.length; j++){
                    if(i != j && adjacence (points[i],points[j])) {
                        compteur++;
                    }
                }
                if(compteur == 1){
                    extremite.add(points[i]);
                }
                if (compteur == 2){
                    interne.add(points[i]);
                }
            }
            if(extremite.size()==2 && interne.size() == (points.length-2)){
                resultat = true;
            }
        }
        return resultat;
    }

    /**
     * Méthode isASimpleBlackCurve
     *
     * @param points un paramètre
     * @return le retour
     */
    public boolean isASimpleBlackCurve(Point[] points){
        boolean resultat = false;
        int compteur = 0;
        ArrayList<Point> interne = new ArrayList<Point>();
        boolean sontTousNoirs = true;
        for(int x = 0; x<points.length; x++){
            if (this.getCouleur(points[x]) == BLANC){
                sontTousNoirs = false;
            }
        }
        if (sontTousNoirs) {
            for (int i = 0; i<points.length; i++){
                compteur = 0;
                for (int j = 0; j<points.length; j++){
                    if(i != j && adjacence (points[i],points[j])) {
                        compteur++;
                    }
                }
                if (compteur == 2){
                    interne.add(points[i]);
                }
            }
            if( interne.size() == (points.length)){
                resultat = true;
            }
        }
        return resultat;
    }

    public boolean isABorderPoint(Point p){
        boolean resultat = false;
        Point [] voisins = this.voisins(p);
        if (this.getCouleur(p) == NOIR){
            for(int i=0; i<voisins.length; i++){
                if(this.getCouleur(voisins[i]) == BLANC){
                    resultat = true;
                }

            }

        }
        return resultat;
    }
}