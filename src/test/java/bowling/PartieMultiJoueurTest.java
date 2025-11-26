package bowling;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartieMultiJoueurTest {

    private IPartieMultiJoueur partie;

    @BeforeEach
    void setUp() {
        partie = new PartieMultiJoueur();
    }

    @Test
    void testDeroulementExempleDonne() {

        String[] players = { "Pierre", "Paul" };

        // Démarrage partie
        assertEquals(
            "Prochain tir : joueur Pierre, tour n° 1, boule n° 1",
            partie.demarreNouvellePartie(players)
        );

        // Pierre joue
        assertEquals(
            "Prochain tir : joueur Pierre, tour n° 1, boule n° 2",
            partie.enregistreLancer(5)
        );

        assertEquals(
            "Prochain tir : joueur Paul, tour n° 1, boule n° 1",
            partie.enregistreLancer(3)
        );

        // Paul fait un strike
        assertEquals(
            "Prochain tir : joueur Pierre, tour n° 2, boule n° 1",
            partie.enregistreLancer(10)
        );

        // Pierre joue au tour 2
        assertEquals(
            "Prochain tir : joueur Pierre, tour n° 2, boule n° 2",
            partie.enregistreLancer(7)
        );

        assertEquals(
            "Prochain tir : joueur Paul, tour n° 2, boule n° 1",
            partie.enregistreLancer(3)
        );

        // Vérification des scores
        assertEquals(18, partie.scorePour("Pierre"));
        assertEquals(10, partie.scorePour("Paul"));
    }

    @Test
    void joueurInexistantDoitLeverException() {
        String[] joueurs = { "Pierre", "Paul" };
        partie.demarreNouvellePartie(joueurs);

        assertThrows(IllegalArgumentException.class, () -> {
            partie.scorePour("Jacques");
        });
    }
}

