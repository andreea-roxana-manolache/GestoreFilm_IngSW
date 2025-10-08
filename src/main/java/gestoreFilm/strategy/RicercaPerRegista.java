package gestoreFilm.strategy;
import gestoreFilm.model.Film;
import java.util.*;

public class RicercaPerRegista implements StrategiaRicerca {
    @Override
    public List<Film> ricerca(List<Film> films, String chiave) {
        List<Film> res = new ArrayList<>();
        String chiaveN = chiave == null ? "" : chiave.toUpperCase();
        for (Film libro: films){
            String autore = libro.getRegista();
            if (autore != null && autore.toUpperCase().contains(chiaveN)){
                res.add(libro);
            }
        }
        return res;
    }
}
