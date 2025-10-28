package gestorefilm.view;

import gestorefilm.model.Film;
import gestorefilm.model.StatoVisione;

import javax.swing.*;
import java.awt.*;

public class FilmDialog extends JDialog{
    private JTextField titoloField;
    private JTextField registaField;
    private JTextField annoField;
    private JTextField genereField;
    private JComboBox<Integer> valutazioneBox;
    private JComboBox<String> statoBox;

    private boolean confermato = false;
    private Film film;

    public FilmDialog(JFrame parent){
        this(parent, null);
    }

    public FilmDialog(JFrame parent, Film filmEsistente){
        super(parent, "Film", true);
        setLayout(new BorderLayout());
        this.setSize(400, 300);
        this.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(6,2,5,5));
        titoloField = new JTextField();
        registaField = new JTextField();
        annoField = new JTextField();
        genereField = new JTextField();
        valutazioneBox = new JComboBox<>(new Integer[]{1,2,3,4,5});
        statoBox = new JComboBox<>(new String[]{"VISTO", "DA_VEDERE", "IN_VISIONE"});

        panel.add(new JLabel("Titolo"));
        panel.add(titoloField);
        panel.add(new JLabel("Regista"));
        panel.add(registaField);
        panel.add(new JLabel("Anno"));
        panel.add(annoField);
        panel.add(new JLabel("Genere"));
        panel.add(genereField);
        panel.add(new JLabel("Valutazione"));
        panel.add(valutazioneBox);
        panel.add(new JLabel("Stato"));
        panel.add(statoBox);

        add(panel, BorderLayout.CENTER);

        JButton confermaBtn = new JButton("Conferma");
        JButton annullaBtn = new JButton("Annulla");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confermaBtn);
        buttonPanel.add(annullaBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        confermaBtn.addActionListener(e -> {
            if(validaInput()){
                film = new Film.Builder(
                        titoloField.getText(),
                        registaField.getText(),
                        annoField.getText()
                )
                        .genere(genereField.getText())
                        .valutazione((Integer) valutazioneBox.getSelectedItem())
                        .statoVisione(StatoVisione.valueOf((String) statoBox.getSelectedItem()))
                        .build();
                confermato = true;
                setVisible(false);
            }
        });

        annullaBtn.addActionListener(e -> {
            confermato = false;
            setVisible(false);
        });

        if (filmEsistente != null){
            riempiCampi(filmEsistente);
        }
    }

    private boolean validaInput(){
        if(titoloField.getText().isBlank() || registaField.getText().isBlank() || annoField.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Titolo, regista e anno sono obbligatori.");
            return false;
        }
        return true;
    }

    private void riempiCampi(Film film){
        titoloField.setText(film.getTitolo());
        registaField.setText(film.getRegista());
        annoField.setText(film.getAnnoUscita());
        genereField.setText(film.getGenere());
        valutazioneBox.setSelectedItem(film.getValutazione());
        statoBox.setSelectedItem(film.getStatoVisione());
    }

    public boolean isConfermato(){
        return confermato;
    }

    public Film getFilm(){
        return film;
    }
}
