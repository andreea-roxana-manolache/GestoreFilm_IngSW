package gestoreFilm.strategy;
import java.util.*;
import gestoreFilm.model.Film;
public interface StrategiaFiltro {
    List<Film> filtra(List<Film> films);
}

