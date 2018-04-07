import java.util.ArrayList;

public class Main {

    public static void main (String[] args) {
        Reseaux reseau = new Reseaux(4,4, Adjacence.ADJ8, Adjacence.ADJ4);
        reseau.setCouleur(0, 0, reseau.NOIR);
        reseau.setCouleur(2, 3, reseau.NOIR);
        reseau.setCouleur(2, 2, reseau.NOIR);
        reseau.setCouleur(3, 2, reseau.NOIR);
        reseau.setCouleur(3, 3, reseau.NOIR);
        ArrayList<ArrayList<int[]>> composants = reseau.getComposants();
        for (int i = 0; i < composants.size(); i++) {
            ArrayList<int[]> comp = composants.get(i);
            System.out.println("Composant " + i);
            for (int j = 0; j < comp.size(); j++) {
                int[] coords = comp.get(j);
                System.out.println(coords[0] + "," + coords[1]);
            }
            System.out.println();
        }
        reseau.dessinerReseau();
    }

}
