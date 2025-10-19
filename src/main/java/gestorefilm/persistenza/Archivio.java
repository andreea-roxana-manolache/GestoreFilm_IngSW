package gestorefilm.persistenza;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import gestorefilm.model.Film;
import gestorefilm.model.StatoVisione;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Archivio {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Film.class, new FilmTypeAdapter())
            .setPrettyPrinting()
            .create();

    public static void salvaSuFileJSon(List<Film> films, String percorso){
        try (Writer writer = new FileWriter(percorso)) {
            gson.toJson(films, writer);
        }catch (IOException e) {
            System.err.println("Errore durante il salvataggio in JSON: " + e.getMessage());
        }
    }

    public static List<Film> caricaDaFileJSon(String percorso){
        try (Reader reader = new FileReader(percorso)) {
            Type listType = new TypeToken<List<Film>>(){}.getType();
            List<Film> lista = gson.fromJson(reader, listType);
            return (lista != null) ? lista : new ArrayList<>();
        }catch (IOException | JsonSyntaxException e) {
            System.err.println("Errore durante il caricamento da JSON: " + e.getMessage());
            return new ArrayList<>(); // ritorna lista vuota in caso di errore
        }
    }

    public static void salvaSuFileCSV(List<Film> films, String percorso){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(percorso))) {
            // Intestazione colonne
            writer.write("\"Titolo\",\"Regista\",\"Anno\",\"Genere\",\"Valutazione\",\"StatoVisione\"\n");
            for (Film film : films) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",%d,%s\n",
                        film.getTitolo().replace("\"", "\"\""),
                        film.getRegista().replace("\"", "\"\""),
                        film.getAnnoUscita().replace("\"", "\"\""),
                        film.getGenere().replace("\"", "\"\""),
                        film.getValutazione(),
                        film.getStatoVisione() != null ? film.getStatoVisione() : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio in CSV: " + e.getMessage());
        }
    }
    public static List<Film> caricaDaFileCSV(String percorso) {
        List<Film> films = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(percorso))) {
            reader.readLine(); // salta intestazione
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campi = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (campi.length < 6) {
                    System.err.println("Linea malformata: " + linea);
                    continue;
                }
                for (int i = 0; i < campi.length; i++) {
                    campi[i] = campi[i].replaceAll("^\"|\"$", "").replace("\"\"", "\"");
                }
                try {
                    String titolo = campi[0];
                    String regista = campi[1];
                    String anno = campi[2];
                    String genere = campi[3];
                    int valutazione = campi[4].isEmpty() ? -1 : Integer.parseInt(campi[4]);
                    StatoVisione statoVisione = campi[5].isEmpty() ? null : StatoVisione.valueOf(campi[5]);

                    films.add(new Film.Builder(titolo, regista, anno)
                            .genere(genere)
                            .valutazione(valutazione)
                            .statoVisione(statoVisione)
                            .build());
                } catch (Exception e) {
                    System.err.println("Errore nel parsing della riga: " + linea + " → " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento da CSV: " + e.getMessage());
        }
        return films;
    }
}
