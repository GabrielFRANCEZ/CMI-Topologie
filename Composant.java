import java.util.ArrayList;
/**
 * Représente un composant
 */
public class Composant extends Set {
    private ArrayList<Point> points;

    public Composant(Adjacence m_adj, Adjacence n_adj) {
        super(m_adj, n_adj);
    }


    /**
     * Méthode interne qui facilite l'accès aux points
     * @return index dans le tableau de points
     */
    private int getIndex (int x, int y) {
        /*
        int index = -1;
        for (int i = 0; i < points.size() && index == -1 ; i++) {
            Point p = points.get(i);
            if (p.getX() == x && p.getY() == y) {
               index = i;
            }
        }
        return index;
        */
       return this.points.indexOf(new Point(x, y, false));
    }

    @Override
    public Point getPoint (int x, int y) throws IllegalArgumentException {
        int index = this.getIndex(x, y);
        if (index == -1) {
            throw new IllegalArgumentException("Point non trouvé : " + x + " " + y);
        }
        return this.points.get(index);
    }

    @Override
    public void setCouleur (int x, int y, boolean couleur) throws IllegalArgumentException {
        // TODO ne pas utiliser une exception
        int index = this.getIndex(x, y);
        if (index == -1) {
            throw new IllegalArgumentException("Point non trouvé : " + x + " " + y);
        }
        this.points.set(index, new Point(x,y,couleur));
    }

    @Override
    public boolean ajouterPoint (Point p) {
        if(this.pointPresent(p.getX(), p.getY()))
            return false;
        this.points.add(p);
        return true;
    }

    @Override
    public boolean pointPresent (int x, int y) {
        return this.getIndex(x, y) != -1;
    }
}
