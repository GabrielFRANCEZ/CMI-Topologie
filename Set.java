import java.util.ArrayList;
/**
 * Représente un conteneur de Points
 */
public abstract class Set {

    public final boolean BLANC = false;
    public final boolean NOIR = true;

    /** Adjacence entre deux points noirs */
    protected Adjacence m_adjacence;

    /** Adjacence entre deux points blancs, ou un point noir et un point blanc */
    protected Adjacence n_adjacence;

    public Set (Adjacence m_adj, Adjacence n_adj) {
        this.m_adjacence = m_adj;
        this.n_adjacence = n_adj;
    }


    /** méthode permettant de laisser libre le choix du stockage de Point */
    public abstract Point getPoint (int x, int y);

    /** Remplace un point du set */
    public abstract void setCouleur (int x, int y, boolean couleur);

    /**
     * Permet d'ajouter un point au set
     * @return true si l'insertion est effectuée, false sinon
     */
    public abstract boolean ajouterPoint (Point p);

    /** indique si un point est au coordonnées données*/
    public abstract boolean pointPresent (int x, int y);

    /** indique si un point est contenu dans le set (couleur comprise) */
    public boolean contientPointAvecCouleur (Point p) {
        boolean contient = false;
        int x = p.getX();
        int y = p.getY();
        if (this.pointPresent(x, y)) {
            Point pSet = this.getPoint(x, y);
            contient = pSet.equals(p) && pSet.getCouleur() == p.getCouleur();
        }
        return contient;
    }

    // N'a aucun sens de mettre en public
    // car les deux points donnés peuvent ne pas être voisins
    /** Méthode d'aide interne pour déterminer le type d'adjacence possible */
    protected Adjacence getTypeAdjacence(Point p1, Point p2) {
        Adjacence adjacence;
        if (p1.getCouleur() == NOIR && p2.getCouleur() == NOIR) {
            adjacence = this.m_adjacence;
        } else {
            adjacence = this.n_adjacence;
        }
        return adjacence;
    }

    public boolean adjacence (Point p1, Point p2) {
        final int dx = Math.abs(p2.getX()-p1.getX());
        final int dy = Math.abs(p2.getY()-p1.getY());
        boolean estVoisin = false;
        switch (this.getTypeAdjacence(p1, p2)) {
            case ADJ4: estVoisin = dx + dy == 1; break;
            case ADJ8: estVoisin = dx + dy == 1 || (dx == 1 && dy == 1); break;
        }
        return estVoisin;
    }

    /**
     * Donne les voisins selon la 8-adjacence : N(p) <br>
     * Tolère que le point donné ne soit pas dans le set
     */
    public ArrayList<Point> voisins8 (Point p) {
        ArrayList<Point> voisins = new ArrayList<Point>();
        int x = p.getX();
        int y = p.getY();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (this.pointPresent(x+i, y+j)) {
                    voisins.add(this.getPoint(x+i,y+j));
                }
            }
        }
        return voisins;
    }
}
