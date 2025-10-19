package gestorefilm.command;
import gestorefilm.Gestore;
import gestorefilm.model.Film;

public class ModificaFilmCommand implements Command {
    private final Gestore gestore; //Reciever
    private final Film oldFilm;
    private final Film newFilm;

    public ModificaFilmCommand(Gestore gestore, Film oldFilm, Film newFilm) {
        this.gestore = gestore;
        this.oldFilm = oldFilm;
        this.newFilm = newFilm;
    }
    @Override
    public void esegui() {
        gestore.modificaFilm(oldFilm, newFilm); //chiama metodo su reciever
    }
}
