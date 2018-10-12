package model;

import java.util.ArrayList;
import java.util.Iterator;

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

  /**
   * Reseaux Constructor
   *
   * @param nbLignes Nombre de lignes
   * @param nbColonnes Nombre de colonnes
   * @param madj adjacence entre deux points noirs
   * @param nadj adjacence entre deux points blancs, ou entre un point noir et un point blanc
   */
  public Reseaux(int nbLignes, int nbColonnes, Adjacence madj, Adjacence nadj) throws IllegalArgumentException {
    if (nbLignes <= 0 || nbColonnes <= 0)
      throw new IllegalArgumentException("nbLinges et nbColonnes doivent être positifs et non nuls");

    // matrice de taille fixe pour l'instant
    this.couleurs = new boolean[nbLignes][nbColonnes];
    this.n_adjacence = nadj;
    this.m_adjacence = madj;

    // initialisation de la matrice
    for (int i = 0; i < nbLignes; i++)
      for (int j = 0; j < nbColonnes; j++)
        this.couleurs[i][j] = BLANC;
  }

  // Accesseurs

  public int getSizeX () { return this.couleurs.length; }
  public int getSizeY () { return this.couleurs[0].length; }
  public Adjacence getM_adjacence () { return m_adjacence; }    
  public Adjacence getN_adjacence () { return n_adjacence; }

  /**
   * Donne la couleur du point dans la matrice (NOIR ou BLANC). <br>
   * Inclus le fond blanc entourant la grille.
   *
   * @param point coordonnées du point
   * @return la couleur du point (NOIR ou BLANC)
   */
  public boolean getColor (Point point) {
    int x = point.getX();
    int y = point.getY();
    // Le "background" blanc
    if (x < 0 || x >= this.getSizeX()
        ||y < 0 || y >= this.getSizeY() // [0] ?
        ) return BLANC;
    return this.couleurs[x][y];
  }

  // Modificateurs

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

  public void setM_adjacence (Adjacence m_adjacence) {
    this.m_adjacence = m_adjacence;
  }

  public void setN_adjacence (Adjacence n_adjacence) {
    this.n_adjacence = n_adjacence;
  }

  // Méthodes Outils

  public boolean sameColor (Point p1, Point p2) {
    return this.getColor(p1) == this.getColor(p2);
  }

  public boolean isInGrid (Point p) {
    return p.getX() >= 0 && p.getX() < this.getSizeX()
        && p.getY() >= 0 && p.getY() < this.getSizeY();
  }

  // N'a aucun sens de mettre en public
  // car les deux points donnés peuvent ne pas être voisins
  /**
   * Méthode d'aide interne pour déterminer le type d'adjacence possible.
   */
  private Adjacence guessAdjacencyType (Point p1, Point p2) {
    if (this.getColor(p1) == NOIR && this.getColor(p2) == NOIR)
      return this.m_adjacence;
    else
      return this.n_adjacence;
  }

  /**
   * Filtre les points donnés selon la couleur donnée
   *
   * @param points les points
   * @param color la couleur souhaitée
   * @return les points dans la liste, qui sont de la couleur souhaitée
   */
  public Point[] filterColor (Point[] points, boolean color) {
    ArrayList<Point> res = new ArrayList<Point>();
    for (Point p : points)
      if (this.getColor(p) == color)
        res.add(p);
    return res.toArray(new Point[res.size()]);
  }

  /**
   * Donne les composants de la matrice composés de points noirs.
   *
   * @return un tableau de composants, qui contiennent des points
   */
  public Point[][] computeBlackComponents(){
    ArrayList<ArrayList<Point>> components = computeComponents();

    Iterator<ArrayList<Point>> it = components.iterator();
    while (it.hasNext()) {
      Point premier = it.next().get(0);
      if (this.getColor(premier) == BLANC) {
        it.remove();
    }}

    Point[][] blackComponents = new Point[components.size()][];

    int i = 0; // post-incrément i++ : retourne i et affecte i+1
    for (ArrayList<Point> comp : components)
      blackComponents[i++] = comp.toArray(new Point[comp.size()]);

    return blackComponents;
  }

  // méthodes pour calculer les composants

  /**
   * Calcule la liste des composants du réseau. Background proche inclus.
   *
   * @return la liste des composants du réseau
   */
  public ArrayList<ArrayList<Point>> computeComponents () {
    int nbl = this.getSizeX() + 2;
    int nbc = this.getSizeY() + 2;
    Point[] liste_points = new Point [nbl * nbc];
    for (int x=(-1); x < nbl-1; x++) {
      for (int y=(-1); y < nbc-1; y++) {
        liste_points[(x+1)*nbl + (y+1)] = new Point(x,y);
    }}
    return this.computeComponents(liste_points);
  }

  /**
   * Calcule la liste des composants des points donnés.
   *
   * @param liste_points liste des points
   * @return la liste des composants des points donnés
   */
  public ArrayList<ArrayList<Point>> computeComponents (Point[] liste_points) {
    ArrayList<Point> pointsToDistribute; // points sans composants couramment
    ArrayList<ArrayList<Point>> composantList; // résultat final
    ArrayList<Point> currentComp; // composant en remplissage
    Point pcomp; // point appartenant à currentComp
    Point ptest; // point testé sur l'adjacence aux points de currentComp
    Iterator<Point> it;

    pointsToDistribute = new ArrayList<Point>(liste_points.length);

    for (Point p : liste_points)
      pointsToDistribute.add(p);

    composantList = new ArrayList<ArrayList<Point>>();

    while (pointsToDistribute.size() > 0) {
      currentComp = new ArrayList<Point> ();
      currentComp.add(pointsToDistribute.remove(0));

      // currentComp se remplit
      // donc iterator interdit
      // rappel : size() est calculée à chaque tour
      for (int i=0; i < currentComp.size(); i++)
      {
        pcomp = currentComp.get(i);
        it = pointsToDistribute.iterator();
        while (it.hasNext()) {
          ptest = it.next();
          if (this.sameColor(pcomp, ptest) && this.adjacence(pcomp, ptest)) {
            currentComp.add(ptest);
            it.remove();
        }}
      }

      composantList.add(currentComp);
    }
    return composantList;
  }

  // méthodes sur le modèle

  /**
   * Détermine si deux points donnés sont adjacents.
   *
   * @param p1 un point
   * @param p2 un point
   * @return true s'ils sont voisins, false sinon
   */
  public boolean adjacence (Point p1, Point p2) {
    final int dx = Math.abs(p2.getX() - p1.getX());
    final int dy = Math.abs(p2.getY() - p1.getY());
    boolean isNeighbor = false;
    switch (this.guessAdjacencyType(p1,p2)) {
      case ADJ4: isNeighbor = dx+dy == 1; break;
      case ADJ8: isNeighbor = dx+dy == 1 || (dx==1 && dy==1); break;
    }
    return isNeighbor;
  }

  /**
   * Donne les voisins selon la 8-adjacence : N(p) <br>
   * Ignore l'adjacence réelle des points.
   *
   * @param p un point
   * @return la liste des voisins de p selon la 8-adjacence
   */
  public Point[] pointsAround (Point p) {
    final int x = p.getX();
    final int y = p.getY();
    // Voir le code comme un dessin des points autour
    Point[] pointsAround = {
        new Point(x-1, y-1), new Point(x, y-1), new Point(x+1, y-1),
        new Point(x-1, y  ), /*  le point p  */ new Point(x+1, y  ),
        new Point(x-1, y+1), new Point(x, y+1), new Point(x+1, y+1)
    };
    return pointsAround;
  }

  /**
   * Donne les voisins d'un point
   *
   * @param p1 un point
   * @return la liste des voisins de p
   */
  public Point[] neighbors (Point p) {
    Point[] pointsAround = this.pointsAround(p);
    ArrayList<Point> neighbors = new ArrayList<Point>();
    for (Point p2 : pointsAround)
      if (this.adjacence(p, p2))
        neighbors.add(p2);
    return neighbors.toArray(new Point[neighbors.size()]);
  }

  //(modèle) test sur plusieurs points
  /**
   * Détermine si un chemin est possible entre deux points
   *
   * @param p1 un point
   * @param p2 un point
   * @return true si un chemin est possible
   */
  public boolean pathIsPossible(Point p1, Point p2){
    // calculer les composants, voir si p1 et p2 sont dans le même
    boolean resultat = false;
    int compteur = 0;

    for (ArrayList<Point> comp : this.computeComponents())
    {
      if (compteur != 0)
        break;
      for (Point pcomp : comp)
        if (pcomp.equals(p1) || pcomp.equals(p2))
          compteur++;
    }

    if(compteur==2)
      resultat = true;

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
    int compteur = 0; // adjacences entre un point et les autres
    ArrayList<Point> extremite = new ArrayList<Point>(); // 1 adj
    ArrayList<Point> interne = new ArrayList<Point>(); // 2 adj
    boolean allBlack = true;

    for(Point p : points) {
      if (this.getColor(p) == BLANC){
        allBlack = false;
        break;
    }}

    if (!allBlack)
      return false;

    for (Point p1 : points)
    {
      compteur = 0;
      for (Point p2 : points)
        if (!p1.equals(p2) && this.adjacence(p1, p2))
          compteur++;

      if(compteur == 1)
        extremite.add(p1);
      if (compteur == 2)
        interne.add(p1);
    }

    if(extremite.size() == 2 && interne.size() == points.length-2)
      resultat = true;

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
    int compteur = 0; // adjacences entre un point et les autres
    ArrayList<Point> interne = new ArrayList<Point>(); // 2 adj
    boolean allBlack = true;

    for (Point p : points) {
      if (this.getColor(p) == BLANC){
        allBlack = false;
        break;
    }}

    if (!allBlack)
      return false;

    for (Point p1 : points)
    {
      compteur = 0;
      for (Point p2 : points)
        if (!p1.equals(p2) && adjacence (p1, p2))
          compteur++;

      if (compteur == 2)
        interne.add(p1);
    }

    if (interne.size() == points.length)
      resultat = true;

    return resultat;
  }

  //(modèle) tests sur un point

  /**
   * Détermine si un point donné est isolé (forme un composant de taille 1)
   *
   * @param p1 un point
   * @return true si le point est isolé, false sinon
   */
  public boolean isAnIsolatedPoint (Point p){
    boolean res = true;
    for (Point pn : this.neighbors(p))
      res = res && this.getColor(pn) == BLANC;
    return res;
  }



  /**
   * Détermine si un point noir fait partie d'une bordure avec un des composants blancs
   *
   * @param p un point
   * @return vrai si le point est sur une bordure, false sinon
   */
  public boolean isABorderPoint (Point p) {
    if (this.getColor(p) == BLANC) return false;

    boolean res = false;
    for(Point pn : this.neighbors(p))
      res = res || this.getColor(pn) == BLANC;

    return res;
  }

  /**
   * Détermine si un point noir est simple. <br>
   * C'est à dire que donner une couleur blanche à ce point n'altère pas la topologie de la matrice.
   *
   * @param p un point
   */
  public boolean isSimple (Point p) {
    if (this.getColor(p) == BLANC) return false;

    boolean res = false;
    int nbCompBlanc = 0;
    int nbCompNoir = 0;
    for (ArrayList<Point> comp : this.computeComponents())
    {
      if (this.getColor(comp.get(0)) == BLANC)
        nbCompBlanc++;
      else
        nbCompNoir++;
    }

    this.setCouleur(p, BLANC);

    int nbNewCompBlanc = 0;
    int nbNewCompNoir = 0;
    for (ArrayList<Point> comp : this.computeComponents())
    {
      if (this.getColor(comp.get(0)) == BLANC)
        nbNewCompBlanc++;
      else
        nbNewCompNoir++;
    }

    this.setCouleur(p, NOIR);

    if (nbCompNoir == nbNewCompNoir && nbCompBlanc == nbNewCompBlanc)
      res = true;

    return res;
  }

  // traitements sur la grille

  /**
   * Enlève tous les points simple de la matrice.
   */
  public void shrinking(){
    for (Point[] comp : this.computeBlackComponents())
    {
      //int nbPointsCourbe = comp.length;
      //ArrayList<Point> pointsEnTrop = new ArrayList<Point>();
      // On veut une courbe ou un point isolé
      boolean changement = true;
      while( changement )
      {
        changement = false;
        for (Point p : comp){
          if (isSimple(p)) {
            //pointsEnTrop.add(p);
            this.setCouleur(p, BLANC);
            changement = true;
        }}
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
    Point[][] composantsNoirs = computeBlackComponents();
    ArrayList<Point> liste_traitement = new ArrayList<Point>();
    ArrayList<Point> points_traites = new ArrayList<Point>();
    do
    {
      for (Point[] comp : composantsNoirs)
        for (Point p : comp)
          if (this.getColor(p) == NOIR
          && this.isABorderPoint(p)
          && !points_traites.contains(p)
          && !liste_traitement.contains(p)
              )
            liste_traitement.add(p);

      Point p = liste_traitement.remove(0);
      points_traites.add(p);
      if(isSimple(p))
        this.setCouleur(p, BLANC);
    } while (liste_traitement.size() > 0);
  }
}
