package gestoreFilm.strategy;
import gestoreFilm.model.Film;

import java.util.*;

public class FiltroStatoVisione implements StrategiaFiltro{
    private final String statoVisione;

    public FiltroStatoVisione(String statoVisione) {
        this.statoVisione = statoVisione.toUpperCase();
    }

    @Override
    public List<Film> filtra(List<Film> films) {
        List<Film> res = new ArrayList<>();
        for (Film film: films){
            String stato = film.getStatoVisione();
            if (stato != null && stato.toUpperCase().equals(this.statoVisione)){
                res.add(film);
            }
        }
        return res;
    }
}