
package bowling;

import java.util.HashMap;
import java.util.Map;

public class PartieMultiJoueur implements IPartieMultiJoueur {

    private String[] joueurs;
    private Map<String, PartieMonoJoueur> parties;
    private int joueurActuel;     
    private boolean demarree;     // c'est pour gerer les exceptions(tests)
    private int tourActuel;       

    @Override
    public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
        if (nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
            throw new IllegalArgumentException("Le tableau des noms de joueurs est vide ou null");
        }

        this.joueurs = nomsDesJoueurs;
        this.parties = new HashMap<>();

        for (String j : joueurs) {
            parties.put(j, new PartieMonoJoueur());// associer chaque joueur a son partie mono-joueur
        }

        this.joueurActuel = 0;
        this.tourActuel = 1;
        this.demarree = true;

        return prochainTir();
    }

    @Override
    public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
        if (!demarree) {
            throw new IllegalStateException("La partie n'est pas démarrée");
        }

        String joueur = joueurs[joueurActuel];
        PartieMonoJoueur partieJoueur = parties.get(joueur);

        boolean continuerTour = partieJoueur.enregistreLancer(nombreDeQuillesAbattues);

        // on change le joueur si son tour est fini
        if (!continuerTour) {
            joueurActuel++;

            // Si on a passé le dernier joueur, on passe au tour suivant
            if (joueurActuel == joueurs.length) {
                joueurActuel = 0;
                tourActuel++;
            }
        }

        // Si la partie est finie pour tout le monde
        if (tourActuel > 10) {
            demarree = false;
            return "Partie terminée";
        }

        return prochainTir();
    }

    @Override
    public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
        if (!parties.containsKey(nomDuJoueur)) {
            throw new IllegalArgumentException("Le joueur " + nomDuJoueur + " ne fait pas partie de la partie");
        }
        return parties.get(nomDuJoueur).score();
    }

    // ------------------------------------------------------

    private String prochainTir() {
        String joueur = joueurs[joueurActuel];
        PartieMonoJoueur partie = parties.get(joueur);

        return "Prochain tir : joueur " + joueur +
               ", tour n° " + partie.numeroTourCourant() +
               ", boule n° " + partie.numeroProchainLancer();
    }
}




