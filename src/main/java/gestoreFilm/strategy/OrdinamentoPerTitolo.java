package gestoreFilm.strategy;
import gestoreFilm.model.Film;
import java.util.*;

public class OrdinamentoPerTitolo implements StrategiaOrdinamento {
    @Override
    public List<Film> ordina(List<Film> films) {
        List<Film> res = new ArrayList<>(films);
        Collections.sort(res, new Comparator<Film>() {
            @Override
            public int compare(Film l1, Film l2) {
                return l1.getTitolo().compareToIgnoreCase(l2.getTitolo());
            }
        });
        return res;
    }
}
