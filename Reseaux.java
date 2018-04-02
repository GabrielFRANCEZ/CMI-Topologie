import info.graphics.Line;
import info.graphics.Window;
import info.graphics.Color;

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

    public Reseaux() {
        // matrice de taille fixe pour l'instant
        this.couleurs = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                couleurs[i][j] = BLANC;
            }
        }

        this.fenetre = new Fenetre("Discrete Topology");

        this.n_adjacence = Adjacence.ADJ4;
        this.m_adjacence = Adjacence.ADJ8;

        this.fenetre.afficher();
    }

    public boolean getCouleur (int x, int y) {
        // Le "background" blanc
        if (x < 0 || x >= this.couleurs.length
        || y < 0 || y >= this.couleurs[0].length) { // [0] ?
            return BLANC;
        }
        return this.couleurs[x][y];
    }

    public void setCouleur (int x, int y, boolean couleur) {
        this.couleurs[x][y] = couleur;
    }

    // N'a aucun sens de mettre en public
    // car les deux points donnés peuvent ne pas être voisins
    /**
     * Méthode d'aide interne pour déterminer le type d'adjacence possible
     */
    private Adjacence getTypeAdjacence(int x1, int y1, int x2, int y2) {
        Adjacence adjacence;
        if (this.getCouleur(x1,y1) == NOIR && this.getCouleur(x2,y2) == NOIR) {
            adjacence = this.m_adjacence;
        } else {
            adjacence = this.n_adjacence;
        }
        return adjacence;
    }

    public boolean adjacence (int x1, int y1, int x2, int y2) {
        final int dx = Math.abs(x2-x1);
        final int dy = Math.abs(y2-y1);
        boolean estVoisin = false;
        switch (this.getTypeAdjacence(x1,y1,x2,y2)) {
            case ADJ4: estVoisin = dx + dy == 1; break;
            case ADJ8: estVoisin = dx + dy == 1 || (dx == 1 && dy == 1); break;
        }
        return estVoisin;
    }

    /**
     * Donne les voisins selon la 8-adjacence : N(p)
     */
    public int[][] voisinsN (int x, int y) {
        int[][] voisins = {
                {x+1,y},{x-1,y},{x,y+1},{x,y-1},
                {x+1,y+1},{x+1,y-1},{x-1,y+1},{x-1,y-1}
            };
        return voisins;
    }

    public void dessinerReseau(){
        for(int i = 0 ; i < this.couleurs.length ; i++) {
            for(int j = 0 ; j < this.couleurs[i].length ; j++){
                this.fenetre.dessinerPoint(i, j, this.couleurs[i][j]);
                int[][] voisins = this.voisinsN(i, j);
                for (int k = 0; k < voisins.length; k++) {
                    final int x2 = voisins[k][0];
                    final int y2 = voisins[k][1];
                    if (this.adjacence(i, j, x2, y2)) { 
                        this.fenetre.dessinerLigne(
                            i, j,
                            voisins[k][0], voisins[k][1]
                        );
                    }
                }
            }
        }

    }
}