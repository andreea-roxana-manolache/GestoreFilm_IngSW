package gestoreFilm.strategy;
import gestoreFilm.model.Film;
import java.util.*;

public class RicercaPerTitolo implements StrategiaRicerca {

    @Override
    public List<Film> ricerca(List<Film> films, String chiave) {
        List<Film> res = new ArrayList<>();
        String chiaveN = chiave == null ? "" : chiave.toUpperCase();
        for (Film film: films){
            String titolo = film.getTitolo();
            if (titolo != null && titolo.toUpperCase().contains(chiaveN)){
                res.add(film);
            }
        }
        return res;
    }
}