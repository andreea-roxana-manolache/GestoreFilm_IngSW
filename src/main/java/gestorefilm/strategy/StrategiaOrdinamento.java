package gestorefilm.strategy;
import java.util.*;
import gestorefilm.model.Film;

public interface StrategiaOrdinamento {
    List<Film> ordina(List<Film> films);
}
