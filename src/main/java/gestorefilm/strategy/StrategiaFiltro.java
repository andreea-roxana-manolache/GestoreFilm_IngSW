package gestorefilm.strategy;
import java.util.*;
import gestorefilm.model.Film;
public interface StrategiaFiltro {
    List<Film> filtra(List<Film> films);
}

