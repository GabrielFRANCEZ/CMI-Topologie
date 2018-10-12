package model;

import java.util.ArrayList;
import model.Point;

/**
 * Représente un réseau discret de points. <br>
 * Propose des méthodes pour manipuler les conceptes topologiques liés.
 */
public class Reseaux {
    public static final boolean BLANC = false;
    public static final boolean NOIR = true;

    /** Adjacence entre deux points noirs */
    private Adjacence m_adjacence;

    /** Adjacence entre deux points blancs, ou un point noir et un point blanc */
    private Adjacence n_adjacence;

    /** point noir == true; point blanc == false */
    public boolean[][] couleurs;

    ///** fenetre de dessin, facilite les conversions de coordonnées */
    //public Fenetre fenetre;

    /**
     * Reseaux Constructor
     *
     * @param nbLignes Nombre de lignes
     * @param nbColonnes Nombre de colonnes
     * @param madj adjacence entre deux points noirs
     * @param nadj adjacence entre deux points blancs, ou entre un point noir et un point blanc
     */
    public Reseaux(int nbLignes, int nbColonnes, Adjacence madj, Adjacence nadj) throws IllegalArgumentException {
        if (nbLignes <= 0 || nbColonnes <= 0) {
          throw new IllegalArgumentException("nbLinges et nbColonnes doivent être positifs et non nuls");
        }
        // matrice de taille fixe pour l'instant
        this.couleurs = new boolean[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                this.couleurs[i][j] = BLANC;
            }
        }

        //this.fenetre = new Fenetre(nbLignes, nbColonnes, "Discrete Topology");

        this.n_adjacence = nadj;
        this.m_adjacence = madj;

        //this.fenetre.afficher();
    }

    public boolean isInGrid (Point p) {
      return p.getX() >= 0 && p.getY() >= 0
          && p.getX() < this.getNbLignes() && p.getY() < this.getNbColonnes();
    }

    public int getNbLignes() {
        return this.couleurs.length;
    }

    public int getNbColonnes () {
        return this.couleurs[0].length;
    }

    public Adjacence getM_adjacence() {
      return m_adjacence;
    }

    public void setM_adjacence(Adjacence m_adjacence) {
      this.m_adjacence = m_adjacence;
    }

    public Adjacence getN_adjacence() {
      return n_adjacence;
    }

    public void setN_adjacence(Adjacence n_adjacence) {
      this.n_adjacence = n_adjacence;
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
     * Calcule la liste des composants du réseau. Background proche inclus.
     *
     * @return la liste des composants du réseau
     */
    public ArrayList<ArrayList<Point>> getComposants () {
        ArrayList<Point> points = new ArrayList<Point> ();
        for (int x = -1; x < this.couleurs.length + 1; x++) {
            for (int y = -1; y < this.couleurs[0].length + 1; y++) {
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
                Point pointComposant = composant.get(i);;
                for (int j = 0; j < points.size(); j++) {
                    Point point = points.get(j);
                    if (this.getCouleur(point) == this.getCouleur(pointComposant) && this.adjacence(pointComposant, point)) {
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

    //
    // /**
    //  * Dessine le réseau de points dans la fenetre. <br>
    //  * Si le réseau est déjà dessiné, il le corrige.
    //  */
    // public void dessinerReseau(){
    //     for(int i = 0 ; i < this.couleurs.length ; i++) {
    //         for(int j = 0 ; j < this.couleurs[i].length ; j++){
    //             Point p1 = new Point(i,j);
    //             this.fenetre.dessinerPoint(p1, this.couleurs[i][j]);
    //             Point[] voisins = this.voisins8(p1);
    //             for (int k = 0; k < voisins.length; k++) {
    //                 Point p2 = voisins[k];
    //                 if (this.adjacence(p1, p2)) {
    //                     this.fenetre.dessinerLigne(p1,p2);
    //                 } else {
    //                     this.fenetre.effacerLigne(p1,p2);
    //                 }
    //             }
    //         }
    //     }
    //
    // }
    //
    // public void effacerReseau() {
    //     this.fenetre.effacerFenetre();
    // }

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

    /**
     * Donne les composants de la matrice composés de points noirs.
     *
     * @return un tableau de composants, qui contiennent des points
     */
    public Point[][] getComposantsNoirs(){
        ArrayList<ArrayList<Point>> composants = getComposants();

        for(int i = 0; i < composants.size(); i++){
            Point premier = composants.get(i).get(0);
            if (this.getCouleur(premier) == BLANC) {
                composants.remove(i);
            }
        }

        Point[][] composantsNoir = new Point[composants.size()][];

        for (int i = 0; i < composants.size(); i++) {
            ArrayList<Point> comp = composants.get(i);
            composantsNoir[i] = comp.toArray(new Point[comp.size()]);
        }

        return composantsNoir;
    }

    /**
     * Détermine si un point noir est simple. <br>
     * C'est à dire que donner une couleur blanche à ce point n'altère pas la topologie de la matrice.
     *
     * @param p un point
     */
    public boolean estSimple(Point p){
        boolean res = false;
        if (this.getCouleur(p)){
            int nbCompBlanc = 0;
            int nbCompNoir = 0;
            for (ArrayList<Point> comp : this.getComposants()) {
              if (this.getCouleur(comp.get(0)) == BLANC)
                nbCompBlanc++;
              else
                nbCompNoir++;
            }
            this.setCouleur(p, BLANC);
            int nbNewCompBlanc = 0;
            int nbNewCompNoir = 0;
            for (ArrayList<Point> comp : this.getComposants()) {
              if (this.getCouleur(comp.get(0)) == BLANC)
                nbNewCompBlanc++;
              else
                nbNewCompNoir++;
            }
            if (nbCompNoir == nbNewCompNoir && nbCompBlanc == nbNewCompBlanc){
                res = true;
            }
            this.setCouleur(p, NOIR);
        }
        return res;
    }

    /**
     * Enlève tous les points simple de la matrice.
     */
    public void shrinking(){
        Point[][] composantsNoirs= getComposantsNoirs();
        for (int i=0; i<composantsNoirs.length;i++) {
            Point[] comp = composantsNoirs[i];
            //int nbPointsCourbe = comp.length;
            //ArrayList<Point> pointsEnTrop = new ArrayList<Point>();
            // On veut une courbe ou un point isolé
            boolean changement = true;
            while( changement ) {
                changement = false;
                for(int j = 0; j < comp.length; j++){
                    Point p = comp[j];
                    if(estSimple(p)) {
                        //pointsEnTrop.add(p);
                        this.setCouleur(p, BLANC);
                        changement = true;
                        // temporaire, pour visualiser ce qui ce passe
                        //this.dessinerReseau();
                        //try {Thread.sleep(500);} // 500 ms
                        //catch (InterruptedException ie) { }
                    }
                }
                /*
                for (int j = 0; j < pointsEnTrop.size() && nbPointsCourbe > 1; j++) {
                    this.setCouleur(pointsEnTrop.get(j), BLANC);
                    nbPointsCourbe--;
                    // temporaire, pour visualiser ce qui ce passe
                    this.dessinerReseau();
                    try {Thread.sleep(500);} // 500 ms
                    catch (InterruptedException ie) { }
                }
                */
                //pointsEnTrop.clear();
            }
        }
    }

    /**
     * Enlève tous les points simple de la matrice.
     */
    public void shrinking_liste_traitement(){
        Point[][] composantsNoirs = getComposantsNoirs();
        ArrayList<Point> liste_traitement = new ArrayList<Point>();
        ArrayList<Point> points_traites = new ArrayList<Point>();
        do {
            for (int i = 0; i < composantsNoirs.length; i++) {
                for (int j = 0; j < composantsNoirs[i].length; j++) {
                    Point p = composantsNoirs[i][j];
                    if (this.getCouleur(p) == NOIR && this.isABorderPoint(p) && !points_traites.contains(p) && !liste_traitement.contains(p)) {
                        liste_traitement.add(p);
                    }
                }
            }
            Point p = liste_traitement.remove(0);
            points_traites.add(p);
            if(estSimple(p)) {
                this.setCouleur(p, BLANC);

                // temporaire, pour visualiser ce qui ce passe
                //this.dessinerReseau();
                //try {Thread.sleep(500);} // 500 ms
                //catch (InterruptedException ie) { }
            }
        } while (liste_traitement.size() > 0);
    }
}
