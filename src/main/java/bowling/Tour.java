package bowling;

import java.util.ArrayList;

public class Tour {

    private int numeroTour;
    private ArrayList<Lancement> lancers;

    public Tour(int numeroTour) {
        this.numeroTour = numeroTour;
        this.lancers = new ArrayList<>();
    
    }

    public ArrayList<Lancement> getLancers() {
        return lancers;
    }

    public boolean enregistrementLancer(Lancement lancer) {
        lancers.add(lancer);

        if (numeroTour < 10) {
            // on verifie si on a  STRIKE : on n'a pas le droit d'arrêter le tour
            if (lancers.size() == 1 && lancer.getNombreDeQuillesAbattues() == 10) {
                return false;
            }
            // fin du tour après 2 lancers
            if (lancers.size() == 2) {
                return false;
            }
            return true; // Le joueur peut rejouer
        }

        // ---------- 10e TOUR ----------
        if (lancers.size() == 1) return true;

        if (lancers.size() == 2) {
            int total = lancers.get(0).getNombreDeQuillesAbattues() +
                        lancers.get(1).getNombreDeQuillesAbattues();
            
            return total >= 10;
        }

        // 3 lancers au 10e tour → terminé
        return false;
    }

    public boolean strike() {
        return lancers.size() >= 1 && lancers.get(0).getNombreDeQuillesAbattues() == 10;
    }

    public boolean spare() {
        return lancers.size() >= 2 &&
               lancers.get(0).getNombreDeQuillesAbattues() +
               lancers.get(1).getNombreDeQuillesAbattues() == 10 &&
               !strike();
    }

    public boolean estFini() {
        if (numeroTour < 10) {
            return strike() || lancers.size() == 2;
        } else {
            if (lancers.size() < 2) return false;

            if (lancers.size() == 2) {
                int total = lancers.get(0).getNombreDeQuillesAbattues() +
                            lancers.get(1).getNombreDeQuillesAbattues();
                return total < 10;
            }

            return true;
        }
    }

}
