
public class Main {

    public static void main (String[] args) {
        Reseaux reseau = new Reseaux(4,4);
        reseau.setCouleur(0, 0, reseau.NOIR);
        reseau.setCouleur(2, 3, reseau.NOIR);
        reseau.setCouleur(2, 2, reseau.NOIR);
        reseau.setCouleur(3, 2, reseau.NOIR);
        reseau.setCouleur(3, 3, reseau.NOIR);
        reseau.dessinerReseau();
    }

}
