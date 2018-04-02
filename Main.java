
public class Main {

    public static void main (String[] args) {
        Reseaux reseau = new Reseaux();
        reseau.setCouleur(0, 0, reseau.NOIR);
        reseau.setCouleur(2, 2, reseau.NOIR);
        reseau.setCouleur(3, 3, reseau.NOIR);
        reseau.dessinerReseau();
    }

}
