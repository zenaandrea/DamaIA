/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univr.model;

/**
 * Classe che gestisce la singola mossa. Viene usata per l'IA
 *
 * @author Valeria Gottelli, Alberto Miglio, Andrea Zenatti
 */
public class Mossa {

    private final int sorgenteRiga, sorgenteColonna;
    private int destinazioneRiga, destinazioneColonna;
    private double peso;

    /**
     * Crea una nuova mossa impostando indice sorgente riga e colonna
     *
     * @param sR indice sorgente riga
     * @param sC indice sorgente colonna
     */
    public Mossa(int sR, int sC) {
        this.sorgenteRiga = sR;
        this.sorgenteColonna = sC;
        this.destinazioneRiga = -1;
        this.destinazioneColonna = -1;
        this.peso = 0;
    }

    /**
     * Crea una nuova mossa impostando gli indici sorgente e destinazione riga e
     * colonna
     *
     * @param sR indice sorgente riga
     * @param sC indice sorgente colonna
     * @param dR indice destinazione riga
     * @param dC indice destinazione colonna
     */
    public Mossa(int sR, int sC, int dR, int dC) {
        this.sorgenteRiga = sR;
        this.sorgenteColonna = sC;
        this.destinazioneRiga = dR;
        this.destinazioneColonna = dC;
        this.peso = 0;
    }

    /**
     * Incrementa il peso della mossa
     *
     * @param p valore da aggiungere al peso
     */
    public void setPeso(double p) {
        this.peso += p;
    }

    /**
     * Imposta l'indice destinazione riga
     *
     * @param d indice da impostare
     */
    public void setDestinazioneRiga(int d) {
        this.destinazioneRiga = d;
    }

    /**
     * Imposta l'indice destinazione colonna
     *
     * @param d indice da impostare
     */
    public void setDestinazioneColonna(int d) {
        this.destinazioneColonna = d;
    }

    /**
     * Ritorna il peso della mossa
     *
     * @return peso
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Ritorna l'indice sorgente riga
     *
     * @return indice sorgente riga
     */
    public int getSorgenteRiga() {
        return sorgenteRiga;
    }

    /**
     * Ritorna l'indice sorgente colonna
     *
     * @return indice sorgente colonna
     */
    public int getSorgenteColonna() {
        return sorgenteColonna;
    }

    /**
     * Ritorna l'indice destinazione riga
     *
     * @return indice destinazione riga
     */
    public int getDestinazioneRiga() {
        return destinazioneRiga;
    }

    /**
     * Ritorna l'indice destinazione colonna
     *
     * @return indice destinazione colonna
     */
    public int getDestinazioneColonna() {
        return destinazioneColonna;
    }
}
