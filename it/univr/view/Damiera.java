package it.univr.view;

import java.awt.GridLayout;
import it.univr.control.*;
import javax.swing.*;

/**
 * Questa classe genera una Damiera e la popola
 *
 * @author Valeria Gottelli, Alberto Miglio, Andrea Zenatti
 */
public class Damiera extends JFrame {

    private final Dati dati;
    private final Casella[][] caselle = new Casella[8][8];
    private final int versione;

    /**
     * Costruttore della classe
     *
     * @param d dati di gioco
     * @param versione interfaccia grafica scelta
     */
    public Damiera(Dati d, int versione) {

        this.versione = versione;
        this.dati = d;
        setLayout(new GridLayout(8, 8));
        setSize(600, 600);
        creaCaselle();
        popola();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        this.setTitle("Dama");
    }

    /**
     * Metodo usato per popolare la Damiera
     *
     *
     */
    public final void popola() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                add(caselle[i][j]);
            }
        }
    }

    /**
     * Metodo usato per creare le caselle
     */
    public final void creaCaselle() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int a = this.dati.getValue(i, j);
                this.caselle[i][j] = new Casella(a, this, i, j, versione);
            }
        }
    }

    /**
     * Aggiorna l'icona delle caselle
     */
    public void refresh() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int a = this.dati.getValue(i, j);
                this.caselle[i][j].refresh(a);
            }
        }
        if (dati.getTurno() == 0 && dati.getNumClick() == 0) {
            dati.nessunaMossa();
        }
    }

    /**
     * Invoca il metodo di check su dati
     *
     * @param r indice riga
     * @param c indice colonna
     * @return esito della verifica
     */
    public int check(int r, int c) {
        return dati.check(r, c);
    }

    /**
     * Ritorna il turno di gioco
     *
     * @return turno di gioco
     */
    public int turno() {
        return dati.getTurno();
    }

    /**
     * Ritorna i dati di gioco
     *
     * @return dati di gioco
     */
    public Dati getDati() {
        return this.dati;
    }
}
