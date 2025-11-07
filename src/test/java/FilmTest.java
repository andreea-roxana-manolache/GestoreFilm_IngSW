import org.junit.jupiter.api.Test;
import gestorefilm.model.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmTest {
    @Test
    void testCostruzioneMinima(){
        Film film = new Film.Builder("Film1", "Regista1", "2012").build();

        assertEquals("Film1", film.getTitolo());
        assertEquals("Regista1", film.getRegista());
        assertEquals("2012", film.getAnnoUscita());
        assertEquals("",film.getGenere());
        assertEquals(-1, film.getValutazione());
        assertNull(film.getStatoVisione());
    }

    @Test
    void testCostruzioneCompleta(){
        Film film = new Film.Builder("Film2", "Regista2", "2013")
                .genere("Fantasy")
                .statoVisione(StatoVisione.DA_VEDERE)
                .build();

        assertEquals("Film2", film.getTitolo());
        assertEquals("Regista2", film.getRegista());
        assertEquals("2013", film.getAnnoUscita());
        assertEquals("Fantasy", film.getGenere());
        assertEquals(StatoVisione.DA_VEDERE.toString(), film.getStatoVisione());
    }

    @Test
    void testEquals(){
        Film film1 = new Film.Builder("Titolo1", "Regista1", "2010").build();
        Film film2 = new Film.Builder("Titolo1", "Regista1", "2010").build();
        Film film3 = new Film.Builder("Titolo3", "Regista3", "2020").build();

        assertEquals(film1, film2);
        assertNotEquals(film1, film3);
    }

    @Test
    void testToStringNotNull(){
        Film film = new Film.Builder("Film10", "Regista1", "2025")
                .genere("Commedia")
                .valutazione(4)
                .statoVisione(StatoVisione.IN_VISIONE)
                .build();

        String stringa = film.toString();

        assertNotNull(stringa);
        assertTrue(stringa.contains("Film10"));
        assertTrue(stringa.contains("Regista1"));
        assertTrue(stringa.contains("2025"));
        assertTrue(stringa.contains("Commedia"));
        assertTrue(stringa.contains("4"));
        assertTrue(stringa.contains("IN_VISIONE"));

    }
}
