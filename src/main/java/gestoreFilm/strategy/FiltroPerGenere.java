package gestoreFilm.strategy;
import java.util.*;
import gestoreFilm.model.Film;

public class FiltroPerGenere implements StrategiaFiltro {
    public final String genere;

    public FiltroPerGenere(String genere) {
        this.genere = genere.toUpperCase();
    }

    @Override
    public List<Film> filtra(List<Film> films) {
        List<Film> res = new ArrayList<>();
        for (Film film: films){
            String g = film.getGenere();
            if (g != null && g.toUpperCase().contains(this.genere)){
                res.add(film);
            }
        }
        return res;
    }
}
