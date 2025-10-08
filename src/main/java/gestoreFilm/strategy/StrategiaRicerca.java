package gestoreFilm.strategy;
import java.util.*;
import gestoreFilm.model.Film;

public interface StrategiaRicerca {
    List<Film> ricerca(List<Film> films, String chiave);
}