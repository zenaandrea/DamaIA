package it.univr.view;

import java.awt.event.*;
import javax.swing.*;

/**
 * Classe che definisce una casella.
 *
 * @author Valeria Gottelli, Alberto Miglio, Andrea Zenatti
 */
public class Casella extends JButton {

    private Damiera damiera;
    private int indice;
    private int riga, colonna;
    private final String[] path;
    private final ImageIcon icon[];

    /**
     * Costruttore della casella
     *
     * @param i indice della casella, indica che tipo di casella è: bianca,
     * nera, nera con pedine/damoni e di quali colori
     * @param d damiera di gioco
     * @param r indice riga della casella
     * @param c indice colonna della casella
     * @param versione interfaccia grafica scelta
     */
    public Casella(int i, Damiera d, int r, int c, int versione) {
        this.path = new String[]{"/Dama_Classica/", "/Dama_Moderna/", "/Dama_StarWars/", "/Dama_Marmo/"};
        this.riga = r;
        this.colonna = c;
        this.damiera = d;
        icon = new ImageIcon[]{
            new ImageIcon(getClass().getResource(path[versione] + "CasellaBianca.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "CasellaNera.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "DamaBianca_CasellaNera.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "DamaNera_CasellaNera.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "DamoneBianco_CasellaNera.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "DamoneNero_CasellaNera.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "DamaBianca_Selezionata.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "DamaNera_Selezionata.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "DamoneBianco_Selezionato.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "DamoneNero_Selezionato.jpg"))};
        this.setIcon(icon[i]);
        this.setSize(75, 75);
        indice = i;
        if (indice == 0) {
            this.setEnabled(false);
            this.setDisabledIcon(icon[0]);
        }

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (damiera.turno() == 0) {
                    damiera.refresh();
                    int check = damiera.check(riga, colonna);
                    damiera.refresh();
                }
                damiera.refresh();
            }
        });
    }

    /**
     * Cambia l'indice della casella
     *
     * @param value nuovo valore da assegnare a indice
     */
    public void setIndex(int value) {
        this.indice = value;
    }

    /**
     * Cambia l'icona della casella in base all'indice
     *
     * @param value valore dell'icona
     */
    public void refresh(int value) {
        this.indice = value;
        this.setIcon(icon[value]);
    }
}
