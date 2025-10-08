package gestoreFilm.strategy;
import gestoreFilm.model.Film;
import java.util.*;

public class OrdinamentoPerValutazione implements StrategiaOrdinamento {
    @Override
    public List<Film> ordina(List<Film> films) {
        List<Film> res = new ArrayList<>(films);
        Collections.sort(res, new Comparator<Film>() {
            @Override
            public int compare(Film l1, Film l2) {
                return Integer.compare(l2.getValutazione(), l1.getValutazione()); // decrescente
            }
        });
        return res;
    }
}