/**
 * Représente un point avec une couleur. <br>
 * Est i
 */
public class Point extends info.graphics.Point {
    private boolean couleur;

    public Point (int x, int y, boolean couleur) {
        super(x, y);
        this.couleur = couleur;
    }

    public Point (Point p) {
        super(p);
        this.couleur = p.couleur;
    }

    public boolean getCouleur () {
        return this.couleur;
    }

    /**
     * Méthode equals
     * Remarque : Ne vérifie pas l'égalité de la couleur
     */
    @Override
    public boolean equals (Object obj) {
        boolean eq = false;
        if (this == obj) eq = true;
        else if (obj instanceof Point) {
            Point p = (Point) obj;
            eq = super.equals(p) /* && this.couleur == p.couleur */;
        }
        return eq;
    }

    @Override
    public String toString () {
        int x = this.getX();
        int y = this.getY();
        String c = this.couleur==true ? "Noir" : "Blanc";
        return "("+x+","+y+","+c+")";
    }
}
