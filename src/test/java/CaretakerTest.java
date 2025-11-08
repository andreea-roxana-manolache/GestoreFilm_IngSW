import gestorefilm.Gestore;
import gestorefilm.command.AggiungiFilmCommand;
import gestorefilm.command.ModificaFilmCommand;
import gestorefilm.command.RimuoviFilmCommand;
import gestorefilm.memento.Caretaker;
import gestorefilm.model.Film;
import gestorefilm.model.StatoVisione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CaretakerTest {
    private Gestore gestore;
    private Caretaker caretaker;

    @BeforeEach
    public void inizializza(){
        gestore = Gestore.getInstance();
        gestore.setFilms(List.of());
        caretaker = new Caretaker(gestore);
    }

    @Test
    public void testAggiungiFilm(){
        Film film = new Film.Builder("Titolo test", "Regista test", "2023").build();
        AggiungiFilmCommand cmd = new AggiungiFilmCommand(gestore, film);

        caretaker.eseguiComando(cmd);

        assertEquals(1, gestore.getFilms().size());
        assertTrue(gestore.getFilms().contains(film));
        assertTrue(caretaker.puoFareUndo());
        assertFalse(caretaker.puoFareRedo());
    }

    @Test
    public void testRipristinaStatoPrecedente(){
        Film film = new Film.Builder("Titolo1", "Regista1", "2024").build();
        AggiungiFilmCommand cmd = new AggiungiFilmCommand(gestore, film);

        caretaker.eseguiComando(cmd);
        caretaker.undo();

        assertEquals(0, gestore.getFilms().size());
        assertTrue(caretaker.puoFareRedo());
    }

    @Test
    public void testRipristinaDopoUndo(){
        Film film = new Film.Builder("Titolo1", "Regista1", "2024").build();
        AggiungiFilmCommand cmd = new AggiungiFilmCommand(gestore, film);

        caretaker.eseguiComando(cmd);
        caretaker.undo();
        caretaker.redo();

        List<Film> films = gestore.getFilms();
        assertEquals(1, films.size());
        assertEquals("2024", films.getFirst().getAnnoUscita());
    }

    @Test
    public void testUndoRedoMultipli(){
        Film film1 = new Film.Builder("Titolo1", "Reg1", "2021").build();
        Film film2 = new Film.Builder("Titolo2", "Reg2", "2021").build();

        caretaker.eseguiComando(new AggiungiFilmCommand(gestore,film1));
        caretaker.eseguiComando(new AggiungiFilmCommand(gestore, film2));

        assertEquals(2, gestore.getFilms().size());

        caretaker.undo(); //rimuove libro2
        assertEquals(1, gestore.getFilms().size());
        assertTrue(gestore.getFilms().contains(film1));

        caretaker.undo();
        assertEquals(0, gestore.getFilms().size());

        caretaker.redo(); //ripristina libro1
        assertEquals(1, gestore.getFilms().size());
        assertTrue(gestore.getFilms().contains(film1));

        caretaker.redo(); //ripristina libro2
        assertEquals(2, gestore.getFilms().size());
        assertTrue(gestore.getFilms().contains(film2));
    }

    @Test
    public void testModificaLibro(){
        Film vecchio = new Film.Builder("Titolo1", "Regista1", "2023").build();
        caretaker.eseguiComando(new AggiungiFilmCommand(gestore, vecchio));

        Film nuovo = new Film.Builder("Tit.1", "Regista1", "2023")
                .genere("Sci-fi")
                .valutazione(3)
                .statoVisione(StatoVisione.VISTO)
                .build();

        caretaker.eseguiComando(new ModificaFilmCommand(gestore, vecchio, nuovo));

        assertEquals(1, gestore.getFilms().size());
        assertEquals("Tit.1", gestore.getFilms().getFirst().getTitolo());
        assertEquals("Sci-fi", gestore.getFilms().getFirst().getGenere());

        caretaker.undo();
        assertEquals("Titolo1", gestore.getFilms().getFirst().getTitolo());
        assertEquals("", gestore.getFilms().getFirst().getGenere());

        caretaker.redo();
        assertEquals("Tit.1", gestore.getFilms().getFirst().getTitolo());

    }

    @Test
    public void testRimuoviLibro(){
        Film l1 = new Film.Builder("Titolo1", "Regista1", "2015").build();
        Film l2 = new Film.Builder("Titolo2", "Regista2", "2015").build();

        caretaker.eseguiComando(new AggiungiFilmCommand(gestore, l1));
        caretaker.eseguiComando(new AggiungiFilmCommand(gestore, l2));
        assertEquals(2, gestore.getFilms().size());

        caretaker.eseguiComando(new RimuoviFilmCommand(gestore, l1));
        assertEquals(1, gestore.getFilms().size());
        assertFalse(gestore.getFilms().contains(l1));
        assertTrue(gestore.getFilms().contains(l2));

        caretaker.undo();
        assertEquals(2, gestore.getFilms().size());
        assertTrue(gestore.getFilms().contains(l1));

        caretaker.redo();
        assertEquals(1, gestore.getFilms().size());
        assertFalse(gestore.getFilms().contains(l1));
    }
}
