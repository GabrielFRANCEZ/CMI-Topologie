import java.util.ArrayList;
import info.graphics.Line;
import info.graphics.Window;
import info.graphics.Color;

public class Picture extends Set {

    /** point noir == true; point blanc == false */
    public boolean[][] couleurs;

    /** fenetre de dessin, facilite les conversions de coordonnées */
    public Fenetre fenetre;

    public Picture (int nbLignes, int nbColonnes, Adjacence m_adj, Adjacence n_adj) {
        super(m_adj, n_adj);
        // matrice de taille fixe pour l'instant
        this.couleurs = new boolean[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                couleurs[i][j] = BLANC;
            }
        }

        this.fenetre = new Fenetre(nbLignes, nbColonnes, "Discrete Topology");

        this.fenetre.afficher();
    }

    @Override
    public Point getPoint (int x, int y) {
        boolean c;
        // Le "background" blanc
        if (this.pointPresent(x, y)) {
            c = this.couleurs[x][y];
        } else { c = BLANC; }
        return new Point(x,y,c);
    }

    @Override
    public void setCouleur (int x, int y, boolean couleur) {
        if (this.pointPresent(x,y)) {
            this.couleurs[x][y] = couleur;
        }
    }

    @Override
    public void ajouterPoint (Point p) {
        // TODO Implementer
    }

    @Override
    public boolean pointPresent (int x, int y) {
        return x >= 0 && x < this.couleurs.length
            && y >= 0 && y < this.couleurs[0].length;
    }

    public void dessinerReseau(){
        for(int i = 0 ; i < this.couleurs.length ; i++) {
            for(int j = 0 ; j < this.couleurs[i].length ; j++){
                Point p = this.getPoint(i, j);
                this.fenetre.dessinerPoint(i, j, this.couleurs[i][j]);
                ArrayList<Point> voisins = this.voisins8(p);
                for (int k = 0; k < voisins.size(); k++) {
                    Point voisin = voisins.get(k);
                    if (this.adjacence(p, voisin)) { 
                        this.fenetre.dessinerLigne(
                            p.getX(), p.getY(),
                            voisin.getX(), voisin.getY()
                        );
                    }
                }
            }
        }

    }
}