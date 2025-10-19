package gestorefilm.command;
import gestorefilm.Gestore;
import gestorefilm.model.Film;

public class AggiungiFilmCommand implements Command {
    private final Gestore gestore; //Reciever
    private final Film film;

    public AggiungiFilmCommand(Gestore gestore, Film film) {
        this.gestore = gestore;
        this.film = film;
    }
    @Override
    public void esegui() {
        gestore.aggiungiFilm(film); //chiama metodo su reciever
    }
}

