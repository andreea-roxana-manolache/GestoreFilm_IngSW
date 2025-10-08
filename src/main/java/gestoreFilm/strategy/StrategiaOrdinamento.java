package gestoreFilm.strategy;
import java.util.*;
import gestoreFilm.model.Film;

public interface StrategiaOrdinamento {
    List<Film> ordina(List<Film> films);
}
