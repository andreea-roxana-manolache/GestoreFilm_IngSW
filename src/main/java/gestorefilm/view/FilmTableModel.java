package gestorefilm.view;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import gestorefilm.model.Film;

public class FilmTableModel extends AbstractTableModel {
    private final String[] colonne = {"Titolo", "Regista", "Anno", "Genere", "Valutazione", "Stato Visione"};
    private List<Film> films;

    public FilmTableModel(List<Film> films) {
        this.films = films;
    }

    @Override
    public int getRowCount() {
        return films.size();
    }

    @Override
    public int getColumnCount() {
        return colonne.length;
    }

    @Override
    public String getColumnName(int column) {
        return colonne[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Film film = films.get(rowIndex);
        return switch(columnIndex){
            case 0 -> film.getTitolo();
            case 1 -> film.getRegista();
            case 2 -> film.getAnnoUscita();
            case 3 -> film.getGenere();
            case 4 -> film.getValutazione();
            case 5 -> film.getStatoVisione();
            default -> "";
        };
    }

    public Film getFilmAt(int row) {
        return films.get(row);
    }

    public void aggiorna(List<Film> nuoviFilms) {
        this.films = nuoviFilms;
        fireTableDataChanged();
    }
}
