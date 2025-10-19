package gestorefilm.command;
import gestorefilm.Gestore;
import gestorefilm.model.Film;

public class RimuoviFilmCommand implements Command {
    private final Gestore gestore; //Reciever
    private final Film film;

    public RimuoviFilmCommand(Gestore gestore, Film film) {
        this.gestore = gestore;
        this.film = film;
    }
    @Override
    public void esegui() {
        gestore.rimuoviFilm(film); //chiama metodo su reciever
    }
}