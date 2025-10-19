package gestorefilm.persistenza;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import gestorefilm.model.Film;
import gestorefilm.model.StatoVisione;

import java.io.IOException;

public class FilmTypeAdapter extends TypeAdapter<Film> {

    @Override
    public void write(JsonWriter out, Film film) throws IOException {
        out.beginObject();
        out.name("titolo").value(film.getTitolo());
        out.name("regista").value(film.getRegista());
        out.name("anno").value(film.getAnnoUscita());
        out.name("genere").value(film.getGenere());
        out.name("valutazione").value(film.getValutazione());
        out.name("statoVisione").value(
                film.getStatoVisione() != null ? film.getStatoVisione() : null
        );
        out.endObject();
    }

    @Override
    public Film read(JsonReader in) throws IOException {
        String titolo = null;
        String regista = null;
        String anno = null;
        String genere = "";
        int valutazione = -1;
        StatoVisione statoVisione = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "titolo" -> titolo = in.nextString();
                case "regista" -> regista = in.nextString();
                case "genere" -> genere = in.nextString();
                case "anno" -> anno = in.nextString();
                case "valutazione" -> valutazione = in.nextInt();
                case "statoVisione" -> {
                    if (in.peek() != com.google.gson.stream.JsonToken.NULL) {
                        String statoStr = in.nextString();
                        statoVisione = StatoVisione.valueOf(statoStr);
                    } else {
                        in.nextNull();
                    }
                }
                default -> in.skipValue();
            }
        }
        in.endObject();

        if (titolo == null || regista == null || anno == null) {
            throw new IOException("Campi obbligatori mancanti nel JSON: titolo, regista o anno");
        }

        return new Film.Builder(titolo, regista, anno)
                .genere(genere)
                .valutazione(valutazione)
                .statoVisione(statoVisione)
                .build();
    }
}
