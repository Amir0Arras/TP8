package bowling;

import java.util.ArrayList;
import java.util.List;


/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancers successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 */
public class PartieMonoJoueur {
	
	private ArrayList<Tour> tours = new ArrayList<>();
	private int tourActuel; 

	
	public PartieMonoJoueur() {
		this.tours = new ArrayList<Tour>();
		for (int i = 1; i <= 10; i++) {
            tours.add(new Tour(i));
        }
        tourActuel = 0;
	}	

	public ArrayList<Integer> getTousLancers() {
        ArrayList<Integer> lancers = new ArrayList<>();
        for (Tour t : tours) {
            for (Lancement l : t.getLancers()) {
                lancers.add(l.getNombreDeQuillesAbattues());
            }
        }
        return lancers;
    }

	/**
	 * Cette méthode doit être appelée à chaque lancer de boule
	 *
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
	 * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux sinon	
	 */
	public boolean enregistreLancer(int nombreDeQuillesAbattues) {
		if (estTerminee()) {
            throw new IllegalStateException("La partie est terminée,on a fait 10 tours !!");
        }

        Lancement lancer = new Lancement(nombreDeQuillesAbattues);
        boolean tourContinue = tours.get(tourActuel).enregistrementLancer(lancer);

        // si on'a pas le cas de stride, spare, dernier tours.. ,donc la tour est fini
        if (!tourContinue) {
            tourActuel++;
        }

        return tourContinue;
	}

	/**
	 * Cette methode donne le score du joueur.
	 * Si la partie n'est pas terminee, on considere que les lancers restants
	 * abattent 0 quille.
	 * @return Le score du joueur
	 */
	public int score() {
		ArrayList<Integer> lancers = getTousLancers();
        int score = 0;
        int indexLancer = 0;

		// On évalue 10 frames, ou moins si on n'a pas assez de lancers
        for (int frame = 0; frame < 10 && indexLancer < lancers.size(); frame++) {
            int premier = lancers.get(indexLancer);

            // le cas de strike
            if (premier == 10) {
                score += 10 + getLancerSuivant(lancers, indexLancer + 1) 
                             + getLancerSuivant(lancers, indexLancer + 2);
                indexLancer += 1;
            }
            // le cas de spare
            else if (premier + getLancerSuivant(lancers, indexLancer + 1) == 10) {
                score += 10 + getLancerSuivant(lancers, indexLancer + 2);
                indexLancer += 2;
            }
            // deux lancers simples
            else {
                score += premier + getLancerSuivant(lancers, indexLancer + 1);
                indexLancer += 2;
            }
        }

		return score;
	}
	
	/**
	 * Retourne le lancer à l'index donne ou 0 si l'index est hors limites.
	 */
	private int getLancerSuivant(ArrayList<Integer> lancers, int index) {
		if (index >= 0 && index < lancers.size()) {
			return lancers.get(index);
		}
		return 0;
	}
	
	/**
	 * @return 
	 */
	public boolean estTerminee() {
		return tourActuel >= 10;
	}

	/**
	 * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
	 */
	public int numeroTourCourant() {
		return estTerminee() ? 0 : tourActuel + 1;
	}

	/**
	 * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le jeu
	 *         est fini
	 */
	public int numeroProchainLancer() {
		return estTerminee() ? 0 : tours.get(tourActuel).getLancers().size() + 1;
	}

}
