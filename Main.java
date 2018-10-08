import java.util.Scanner;
import java.util.ArrayList;
import window.TopologyApplication;

public class Main {

    public static void main (String[] args) {
        TopologyApplication.launch();
        /*
        Reseaux reseau = new Reseaux(5,5, Adjacence.ADJ8, Adjacence.ADJ4);
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 3);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(4,4);
        Point p5 = new Point(3,2);
        Point p6 = new Point(4,2);
        Point p7 = new Point(4,3);
        Point p8 = new Point(2,3);
        Point p9 = new Point(2,4);
        Point p10 = new Point(3,4);
        Point p11 = new Point(2,1);

        //  0   1   2   3   4
        //0 p1  OO  OO  OO  OO
        //1 OO  OO  p11 OO  OO
        //2 OO  OO  p3  p5  p6
        //3 OO  OO  p8  p2  p7
        //4 OO  OO  p9  p10 p4

        Point[] liste1 = {p1,p2,p3};
        Point[] liste2 = {p4,p2,p3};
        Point[] liste3 = {p4,p2,p3,p5};
        Point[] liste4 = {p3,p5,p6,p7,p4,p10,p9,p8};

        reseau.setCouleur(p1, reseau.NOIR);
        reseau.setCouleur(p2, reseau.NOIR);
        reseau.setCouleur(p3, reseau.NOIR);
        reseau.setCouleur(p4, reseau.NOIR);
        reseau.setCouleur(p5, reseau.NOIR);
        reseau.setCouleur(p6, reseau.NOIR);
        reseau.setCouleur(p7, reseau.NOIR);
        reseau.setCouleur(p8, reseau.NOIR);
        reseau.setCouleur(p9, reseau.NOIR);
        reseau.setCouleur(p10, reseau.NOIR);
        reseau.setCouleur(p11, reseau.NOIR);

        reseau.pathIsPossible(p1,p2);
        reseau.pathIsPossible(p2,p3);
        reseau.pathIsPossible(p1,p3);

        reseau.isAnIsolatedPoint(p1);
        reseau.isAnIsolatedPoint(p2);
        reseau.isAnIsolatedPoint(p3);

        System.out.println("arc "+reseau.isASimpleBlackArc(liste1));
        System.out.println("arc "+reseau.isASimpleBlackArc(liste2));
        System.out.println("arc "+reseau.isASimpleBlackArc(liste3));
        System.out.println("arc "+reseau.isASimpleBlackArc(liste4));
        System.out.println("courbe " + reseau.isASimpleBlackCurve(liste4));

        System.out.println("point non border "+reseau.isABorderPoint(p2));
        System.out.println("point non border "+reseau.isABorderPoint(p7));

        ArrayList<ArrayList<Point>> composants = reseau.getComposants();
        for (int i = 0; i < composants.size(); i++) {
            ArrayList<Point> comp = composants.get(i);
            System.out.println("Composant " + i);
            for (int j = 0; j < comp.size(); j++) {
                Point coords = comp.get(j);
                System.out.print(coords + " ");
            }
            System.out.println();
        }
        reseau.dessinerReseau();

        try {Thread.sleep(1000);} // 1000 ms
        catch (InterruptedException ie) { }

        //reseau.shrinking_liste_traitement();
        reseau.dessinerReseau();

        //reseau.effacerReseau(); // pas besoin
        //reseau.setCouleur(p4, reseau.BLANC);
        //reseau.setCouleur(new Point(3,1), reseau.NOIR);
        //reseau.dessinerReseau();
        System.out.println("fin");
        */

    }

}
