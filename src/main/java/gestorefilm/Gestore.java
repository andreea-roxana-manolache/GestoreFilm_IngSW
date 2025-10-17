package gestorefilm;

import gestorefilm.model.Film;
import gestorefilm.strategy.StrategiaRicerca;
import gestorefilm.strategy.StrategiaOrdinamento;
import gestorefilm.strategy.StrategiaFiltro;
import gestorefilm.memento.Memento;
import gestorefilm.memento.Originator;
import java.util.*;

public class Gestore implements Originator {
    private static Gestore instance;
    private List<Film> films;

    //Strategy: context mantiene un riferimento alla strategia
    private StrategiaRicerca strategiaRicerca;
    private StrategiaFiltro strategiaFiltro;
    private StrategiaOrdinamento strategiaOrdinamento;

    private Gestore(){
        films = new ArrayList<>();
    }
    public static synchronized Gestore getInstance(){
        if(instance == null){
            instance = new Gestore();
        }
        return instance;
    }

    //Reciever, sa come gestire l'operazione
    public void aggiungiFilm(Film film){
        films.add(film);
    }
    public void rimuoviFilm(Film film){
        films.remove(film);
    }
    public void modificaFilm(Film vecchio, Film nuovo){
        int indice = films.indexOf(vecchio);
        if (indice >= 0){
            films.set(indice, nuovo);
        }
    }
    public List<Film> getFilms(){
        return new ArrayList<>(films);
    }

    public void setFilms(List<Film> films){this.films = new ArrayList<>(films);}

    @Override
    public Memento creaMemento() {
        return new GestoreMemento(new ArrayList<>(films));
    }

    @Override
    public void ripristinaDaMemento(Memento memento) {
        if (memento instanceof GestoreMemento(List<Film> stato)){
            this.films = new ArrayList<>(stato);
        }else throw new IllegalArgumentException("Memento non valido");

    }
    //Record privato Memento concreto
    private record GestoreMemento(List<Film> stato) implements Memento{

    }

    public void setStrategiaRicerca(StrategiaRicerca strategia){
        this.strategiaRicerca = strategia;
    }
    public List<Film> ricerca(String chiave) {
        if (strategiaRicerca == null) return getFilms();
        return strategiaRicerca.ricerca(films, chiave);
    }
    public void setStrategiaFiltro(StrategiaFiltro strategia){
        this.strategiaFiltro = strategia;
    }
    public List<Film> filtra(){
        if (strategiaFiltro == null) return getFilms();
        return strategiaFiltro.filtra(films);
    }
    public void setStrategiaOrdinamento(StrategiaOrdinamento strategia){
        this.strategiaOrdinamento = strategia;
    }
    public List<Film> ordina(){
        if (strategiaOrdinamento == null) return getFilms();
        return strategiaOrdinamento.ordina(films);
    }

}

