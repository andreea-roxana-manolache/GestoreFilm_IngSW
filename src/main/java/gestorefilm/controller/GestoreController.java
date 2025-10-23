package gestorefilm.controller;
import gestorefilm.Gestore;
import gestorefilm.command.*;
import gestorefilm.memento.Caretaker;
import gestorefilm.persistenza.Archivio;
import gestorefilm.strategy.*;
import gestorefilm.model.Film;

import java.util.List;

public class GestoreController {
    private final Gestore gestore;
    private final Caretaker caretaker;

    public GestoreController(){
        this.gestore = Gestore.getInstance();
        this.caretaker = new Caretaker(gestore);
    }

    public List<Film> getFilms(){
        return gestore.getFilms();
    }

    public boolean aggiungiFilm(Film film){
        for(Film l: gestore.getFilms())
            if(l.equals(film))
                return false;
        caretaker.eseguiComando(new AggiungiFilmCommand(gestore, film));
        return true;
    }

    public boolean modificaFilm(Film oldFilm, Film newFilm){
        if(!oldFilm.equals(newFilm)){
            for(Film f: gestore.getFilms()){
                if(f.equals(newFilm)) return false;
            }
        }
        caretaker.eseguiComando(new ModificaFilmCommand(gestore, oldFilm, newFilm));
        return true;
    }

    public void rimuoviFilm(Film film){
        caretaker.eseguiComando(new RimuoviFilmCommand(gestore, film));
    }

    public boolean undo(){
        if (caretaker.puoFareUndo()){
            caretaker.undo();
            return true;
        }
        return false;
    }

    public boolean redo(){
        if(caretaker.puoFareRedo()){
            caretaker.redo();
            return true;
        }
        return false;
    }

    public boolean caricaDaJson(String path){
        List<Film> caricati = Archivio.caricaDaFileJSon(path);
        if(caricati.isEmpty()) return false;
        gestore.setFilms(caricati);
        return true;
    }

    public boolean caricaDaCsv(String path){
        List<Film> lista = Archivio.caricaDaFileCSV(path);
        if(lista.isEmpty()) return false;
        gestore.setFilms(lista);
        return true;
    }

    public void salvaSuJson(List<Film> films, String path){
        Archivio.salvaSuFileJSon(films, path);
    }

    public void salvaSuCsv(List<Film> films, String path){
        Archivio.salvaSuFileCSV(films, path);
    }

    public List<Film> ricercaPerTitolo(String chiave){
        gestore.setStrategiaRicerca(new RicercaPerTitolo());
        return gestore.ricerca(chiave);
    }

    public List<Film> ricercaPerRegista(String chiave){
        gestore.setStrategiaRicerca(new RicercaPerRegista());
        return gestore.ricerca(chiave);
    }

    public List<Film> filtraPerGenere(String genere){
        gestore.setStrategiaFiltro(new FiltroPerGenere(genere));
        return gestore.filtra();
    }

    public List<Film> filtraPerStato(String statoVisione){
        gestore.setStrategiaFiltro(new FiltroStatoVisione(statoVisione));
        return gestore.filtra();
    }

    public List<Film> ordinaPerTitolo(){
        gestore.setStrategiaOrdinamento(new OrdinamentoPerTitolo());
        return gestore.ordina();
    }
    public List<Film> ordinaPerValutazione(){
        gestore.setStrategiaOrdinamento(new OrdinamentoPerValutazione());
        return gestore.ordina();
    }
}

