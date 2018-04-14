import java.util.ArrayList;
import info.graphics.Line;
import info.graphics.Window;
import info.graphics.Color;
import info.graphics.Point;

/**
 * Représente un réseau discret de points. <br>
 * Propose des méthodes pour manipuler les conceptes topologiques liés.
 */
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
     * @param nbLignes Nombre de lignes
     * @param nbColonnes Nombre de colonnes
     * @param madj adjacence entre deux points noirs
     * @param nadj adjacence entre deux points blancs, ou entre un point noir et un point blanc
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
     * Donne la couleur du point dans la matrice (NOIR ou BLANC)
     *
     * @param point coordonnées du point
     * @return la couleur du point (NOIR ou BLANC)
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
     * Modifie la couleur d'un point dans la matrice.
     *
     * @param point coordonnées du point
     * @param couleur couleur du point (NOIR ou BLANC)
     */
    public void setCouleur (Point point, boolean couleur) {
        int x = point.getX();
        int y = point.getY();
        this.couleurs[x][y] = couleur;
    }

    /**
     * Filtre les points donnés selon la couleur donnée
     *
     * @param points les points
     * @param couleur la couleur souhaitée
     * @return les points dans la liste, qui sont de la couleur souhaitée
     */
    public Point[] filterColor (Point[] points, boolean couleur) {
        ArrayList<Point> pointsFiltres = new ArrayList<Point>();
        for (int i = 0; i < points.length; i++) {
            if (this.getCouleur(points[i]) == couleur) {
                pointsFiltres.add(points[i]);
            }
        }
        return pointsFiltres.toArray(new Point[pointsFiltres.size()]);
    }

    /**
     * Calcule la liste des composants du réseau.
     *
     * @return la liste des composants du réseau
     */
    public ArrayList<ArrayList<Point>> getComposants () {
        ArrayList<Point> points = new ArrayList<Point> ();
        for (int x = 0; x < this.couleurs.length; x++) {
            for (int y = 0; y < this.couleurs[0].length; y++) {
                points.add(new Point(x,y));
            }
        }
        Point[] liste_points = new Point[points.size()];
        return this.getComposants(points.toArray(liste_points));
    }

    /**
     * Calcule la liste des composants des points donnés.
     *
     * @param liste_points liste des points
     * @return la liste des composants des points donnés
     */
    public ArrayList<ArrayList<Point>> getComposants (Point[] liste_points) {
        ArrayList<Point> points = new ArrayList<Point>(liste_points.length);
        for (int i = 0; i < liste_points.length; i++) {
            points.add(liste_points[i]);
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
     * Méthode d'aide interne pour déterminer le type d'adjacence possible.
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
     * Détermine si deux points donnés sont adjacents.
     *
     * @param p1 un point
     * @param p2 un point
     * @return true s'ils sont voisins, false sinon
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
     * Donne les voisins selon la 8-adjacence : N(p) <br>
     * Ignore l'adjacence réelle des points.
     *
     * @param p un point
     * @return la liste des voisins de p selon la 8-adjacence
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
    
    /**
     * Donne les voisins d'un point
     *
     * @param p un point
     * @return la liste des voisins de p
     */
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
     * Dessine le réseau de points dans la fenetre
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
     * Détermine si un chemin est possible entre deux points
     *
     * @param p1 un point
     * @param p2 un point
     * @return true si un chemin est possible
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
     * Détermine si un point donné est isolé (forme un composant de taille 1)
     *
     * @param p1 un point
     * @return true si le point est isolé, false sinon
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
     * Détermine si la liste de points donnée forme bien un arc noir <br>
     * C'est à dire :
     * <ul>
     *  <li> Tous les points de la liste sont noirs
     *  <li> Il y a deux extrêmités, qui ont chacun seulement 1 voisin dans la liste de point
     *  <li> Les autres points de la liste ont chacun 2 voisins
     * </ul>
     *
     * @param points la liste de point
     * @return true si les points forment un arc, false sinon
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
     * Détermine si la liste de points donnée forme bien une courbe. <br>
     * C'est à dire que les points de la liste ont chacun 2 voisins dans la liste de points.
     *
     * @param points la liste de points
     * @return true si les points forment une courbe, false sinon
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

    /**
     * Détermine si un point noir fait partie d'une bordure avec un des composants blancs
     *
     * @param p un point
     * @return vrai si le point est sur une bordure, false sinon
     */
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