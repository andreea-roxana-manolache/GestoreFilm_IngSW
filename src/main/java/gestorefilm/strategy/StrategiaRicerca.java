package gestorefilm.strategy;
import java.util.*;
import gestorefilm.model.Film;

public interface StrategiaRicerca {
    List<Film> ricerca(List<Film> films, String chiave);
}