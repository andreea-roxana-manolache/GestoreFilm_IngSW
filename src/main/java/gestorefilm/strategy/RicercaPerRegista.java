package gestorefilm.strategy;
import gestorefilm.model.Film;
import java.util.*;

public class RicercaPerRegista implements StrategiaRicerca {
    @Override
    public List<Film> ricerca(List<Film> films, String chiave) {
        List<Film> res = new ArrayList<>();
        String chiaveN = chiave == null ? "" : chiave.toUpperCase();
        for (Film film: films){
            String regista = film.getRegista();
            if (regista != null && regista.toUpperCase().contains(chiaveN)){
                res.add(film);
            }
        }
        return res;
    }
}

