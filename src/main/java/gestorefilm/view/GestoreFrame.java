package gestorefilm.view;
import gestorefilm.controller.GestoreController;
import gestorefilm.model.Film;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GestoreFrame extends JFrame{
    private final GestoreController controller;
    private final FilmTableModel modello;
    private final JTable tabella;

    public GestoreFrame(){
        super("Gestore Film personale");

        controller = new GestoreController();
        modello = new FilmTableModel(controller.getFilms());
        tabella = new JTable(modello);

        setLayout(new BorderLayout());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JScrollPane(tabella), BorderLayout.CENTER);
        add(creaBarraStrumenti(), BorderLayout.NORTH);
        add(creaBarraStrumentiSalva(), BorderLayout.SOUTH);
        add(creaBarraStrumentiStrategy(), BorderLayout.EAST);

        setVisible(true);
    }

    private JToolBar creaBarraStrumenti(){
        JToolBar toolbar = new JToolBar();

        JButton aggiungBtn = new JButton("Aggiungi");
        JButton modificaBtn = new JButton("Modifica");
        JButton rimuoviBtn = new JButton("Rimuovi");
        JButton undoBtn = new JButton("Undo");
        JButton redoBtn = new JButton("Redo");

        aggiungBtn.addActionListener(e -> {
            FilmDialog dialog = new FilmDialog(this);
            dialog.setVisible(true);
            if(dialog.isConfermato()){
                Film nuovoFilm = dialog.getFilm();
                if(!controller.aggiungiFilm(nuovoFilm)){
                    JOptionPane.showMessageDialog(this, "Film già presente.");
                } else {
                    modello.aggiorna(controller.getFilms());
                }
            }
        });

        modificaBtn.addActionListener(e -> {
            int row = tabella.getSelectedRow();
            if(row >= 0){
                Film filmOriginale = modello.getFilmAt(row);
                FilmDialog dialog = new FilmDialog(this, filmOriginale);
                dialog.setVisible(true);
                if(dialog.isConfermato()){
                    boolean ok =controller.modificaFilm(filmOriginale, dialog.getFilm());
                    if(!ok){
                        JOptionPane.showMessageDialog(this, "Film già presente.");
                    }else{
                        modello.aggiorna(controller.getFilms());
                    }
                }
            }
        });

        rimuoviBtn.addActionListener(e -> {
            int row = tabella.getSelectedRow();
            if(row >= 0){
                Film film = modello.getFilmAt(row);
                controller.rimuoviFilm(film);
                modello.aggiorna(controller.getFilms());
            }
        });

        undoBtn.addActionListener(e -> {
            if(!controller.undo()){
                JOptionPane.showMessageDialog(this, "Nessuna operazione da annullare.");
            } else{
                modello.aggiorna(controller.getFilms());
            }
        });

        redoBtn.addActionListener(e -> {
            if(!controller.redo()){
                JOptionPane.showMessageDialog(this, "Nessuna operazione da ripetere.");
            } else{
                modello.aggiorna(controller.getFilms());
            }
        });


        toolbar.add(aggiungBtn);
        toolbar.add(modificaBtn);
        toolbar.add(rimuoviBtn);
        toolbar.addSeparator();
        toolbar.add(undoBtn);
        toolbar.add(redoBtn);

        return toolbar;
    }

    private JToolBar creaBarraStrumentiSalva(){
        JToolBar toolbar = new JToolBar();
        JButton salvaJsonBtn = new JButton("SalvaJson");
        JButton salvaCsvBtn = new JButton("SalvaCsv");
        JButton caricaJsonBtn = new JButton("CaricaJson");
        JButton caricaCsvBtn = new JButton("CaricaCsv");

        salvaJsonBtn.addActionListener(e -> {
            controller.salvaSuJson(controller.getFilms(), "gestorefilm.json");
            JOptionPane.showMessageDialog(this, "Collezione film salvata.");
        });

        salvaCsvBtn.addActionListener(e -> {
            controller.salvaSuCsv(controller.getFilms(), "gestorefilm.csv");
            JOptionPane.showMessageDialog(this, "Collezione film salvata.");
        });

        caricaJsonBtn.addActionListener(e -> {
            if(controller.caricaDaJson("gestorefilm.json")){
                modello.aggiorna(controller.getFilms());
            }else {
                JOptionPane.showMessageDialog(this, "Nessun file json trovato o collezione vuota.");
            }
        });

        caricaCsvBtn.addActionListener(e -> {
            if(controller.caricaDaCsv("gestorefilm.csv")){
                modello.aggiorna(controller.getFilms());
            } else{
                JOptionPane.showMessageDialog(this, "Nessun file csv trovato o collezione vuota.");
            }
        });

        toolbar.add(salvaJsonBtn);
        toolbar.add(salvaCsvBtn);
        toolbar.addSeparator();
        toolbar.add(caricaJsonBtn);
        toolbar.add(caricaCsvBtn);
        return toolbar;
    }

    private JToolBar creaBarraStrumentiStrategy(){
        JToolBar toolbar = new JToolBar(JToolBar.VERTICAL);

        JButton ricercaTitoloBtn = new JButton("Ricerca Titolo");
        JButton ricercaAutoreBtn = new JButton("Ricerca Regista");
        JButton filtraGenereBtn = new JButton("Filtra Genere");
        JButton filtraStatoBtn = new JButton("Filtra Stato");
        JButton ordinaTitoloBtn = new JButton("Ordina per Titolo");
        JButton ordinaValutazioneBtn = new JButton("Ordina per Valutazione");
        JButton mostraTuttiBtn = new JButton("Mostra Tutti");

        ricercaTitoloBtn.addActionListener(e -> {
            String chiave = JOptionPane.showInputDialog(this, "Inserisci Titolo");
            if(chiave != null){
                List<Film> risultati = controller.ricercaPerTitolo(chiave);
                modello.aggiorna(risultati);
            }
        });

        ricercaAutoreBtn.addActionListener(e -> {
            String chiave = JOptionPane.showInputDialog(this, "Inserisci Regista");
            if(chiave != null){
                List<Film> risultati = controller.ricercaPerRegista(chiave);
                modello.aggiorna(risultati);
            }
        });

        filtraGenereBtn.addActionListener(e -> {
            String genere = JOptionPane.showInputDialog(this, "Inserisci Genere");
            if(genere != null){
                List<Film> risultati = controller.filtraPerGenere(genere);
                modello.aggiorna(risultati);
            }
        });

        filtraStatoBtn.addActionListener(e -> {
            String stato = JOptionPane.showInputDialog(this, "Inserisci Stato (VISTO, DA_VEDERE, IN_VISIONE)");
            if(stato != null){
                List<Film> risultati = controller.filtraPerStato(stato);
                modello.aggiorna(risultati);
            }
        });

        mostraTuttiBtn.addActionListener(e -> {
            modello.aggiorna(controller.getFilms());
        });

        ordinaTitoloBtn.addActionListener(e -> modello.aggiorna(controller.ordinaPerTitolo()));
        ordinaValutazioneBtn.addActionListener(e -> modello.aggiorna(controller.ordinaPerValutazione()));

        toolbar.add(ricercaTitoloBtn);
        toolbar.add(ricercaAutoreBtn);
        toolbar.addSeparator();
        toolbar.add(filtraGenereBtn);
        toolbar.add(filtraStatoBtn);
        toolbar.addSeparator();
        toolbar.add(ordinaTitoloBtn);
        toolbar.add(ordinaValutazioneBtn);
        toolbar.addSeparator();
        toolbar.add(mostraTuttiBtn);
        return toolbar;

    }
}

