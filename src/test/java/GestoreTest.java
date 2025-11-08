import gestorefilm.Gestore;
import gestorefilm.memento.Memento;
import gestorefilm.model.Film;
import gestorefilm.model.StatoVisione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GestoreTest {
    private Gestore gestore;

    @BeforeEach
    void resetGestore(){
        gestore = Gestore.getInstance();
        gestore.setFilms(List.of()); //svuota la collezione prima di ogni test
    }

    @Test
    void testSingleton(){
        Gestore L1 = Gestore.getInstance();
        Gestore L2 = Gestore.getInstance();

        assertSame(L1,L2);
    }

    @Test
    void testAggiungiFilm(){
        Film film = new Film.Builder("Titolo1", "REG1", "2020").build();
        gestore.aggiungiFilm(film);

        assertTrue(gestore.getFilms().contains(film));
    }

    @Test
    void testRimuoviFilm(){
        Film film = new Film.Builder("Titolo1", "Reg1", "2017").build();
        gestore.aggiungiFilm(film);
        gestore.rimuoviFilm(film);
        assertFalse(gestore.getFilms().contains(film));
    }

    @Test
    void testModificaFilm(){
        Film vecchio = new Film.Builder("Titolo1", "Reg1", "2015").build();
        Film nuovo = new Film.Builder("1984", "Regista1", "2015")
                .genere("Horror")
                .valutazione(4)
                .statoVisione(StatoVisione.VISTO)
                .build();
        gestore.aggiungiFilm(vecchio);
        gestore.modificaFilm(vecchio, nuovo);

        List<Film> films = gestore.getFilms();

        assertEquals(1, films.size());
        assertEquals("Regista1", films.getFirst().getRegista());
        assertEquals("Horror", films.getFirst().getGenere());
        assertEquals(4, films.getFirst().getValutazione());
        assertEquals("VISTO", films.getFirst().getStatoVisione());

    }

    @Test
    void testSetFilm(){
        Film film1 = new Film.Builder("Titolo1", "Reg1", "2012").build();
        Film film2 = new Film.Builder("Titolo2", "Reg2", "2012").build();
        gestore.setFilms(List.of(film1, film2));

        List<Film> libri = gestore.getFilms();
        assertEquals(2, libri.size());
        assertTrue(libri.contains(film1));
        assertTrue(libri.contains(film2));

    }

    @Test
    void testMemento(){
        Film film1 = new Film.Builder("Titolo1", "Regista1", "2024").build();
        gestore.aggiungiFilm(film1);

        Memento memento = gestore.creaMemento();

        Film film2 = new Film.Builder("Titolo2", "Reg2", "2019").build();
        gestore.aggiungiFilm(film2);

        assertEquals(2, gestore.getFilms().size());

        gestore.ripristinaDaMemento(memento);

        List<Film> films = gestore.getFilms();
        assertEquals(1, films.size());
        assertTrue(films.contains(film1));
        assertFalse(films.contains(film2));
    }
}
