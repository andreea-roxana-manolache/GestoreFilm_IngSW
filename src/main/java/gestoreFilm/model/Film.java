package gestoreFilm.model;

public class Film {
    private final String titolo;
    private final String regista;
    private final String genere;
    private final String annoUscita;
    private final int valutazione; //da 1 a 5
    private final StatoVisione statoVisione;

    public static class Builder{
        //parametri obbligatori
        private final String titolo;
        private final String regista;
        private final String annoUscita;

        //parametri opzionali
        private String genere = "";
        private int valutazione = -1;
        private StatoVisione statoVisione = null;

        public Builder(String titolo, String regista, String annoUscita){
            this.titolo = titolo;
            this.regista = regista;
            this.annoUscita = annoUscita;
        }
        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }
        public Builder valutazione(int valutazione){
            this.valutazione = valutazione;
            return this;
        }
        public Builder statoVisione(StatoVisione statoVisione){
            this.statoVisione = statoVisione;
            return this;
        }
        public Film build(){
            return new Film(this);
        }
    } //Builder

    private Film(Builder builder){
        this.titolo = builder.titolo;
        this.regista = builder.regista;
        this.genere = builder.genere;
        this.annoUscita = builder.annoUscita;
        this.valutazione = builder.valutazione;
        this.statoVisione = builder.statoVisione;
    }

    public String getTitolo(){return titolo;}
    public String getRegista(){return regista;}
    public String getGenere(){return genere;}
    public String getAnnoUscita(){return annoUscita;}
    public int getValutazione(){return valutazione;}
    public String getStatoVisione() {
        if (statoVisione != null) return statoVisione.name();
        return null;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return film.titolo.equals(this.titolo) && film.regista.equals(this.regista) &&
                film.annoUscita.equals(this.annoUscita);
    }

    @Override
    public String toString() {
        return "["+ "titolo=" + titolo +", regista=" + regista +", genere=" + genere +", anno=" + annoUscita
                +", valutazione=" + valutazione +", stato=" + statoVisione +"]";
    }
}
