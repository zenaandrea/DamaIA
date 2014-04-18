package it.univr.control;

import it.univr.model.Mossa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe che si occupa di gestire i dati della partita.
 *
 * @author Valeria Gottelli, Alberto Miglio, Andrea Zenatti
 */
public class Dati {
    /*
     * Legenda pedine
     * 0 -> Cella bianca
     * 1 -> Cella nera
     * 2 -> Dama bianca in cella nera
     * 3 -> Dama nera in cella nera
     * 4 -> Damone bianco in cella nera
     * 5 -> Damone nero in cella nera
     * 6 -> Dama bianca selezionata
     * 7 -> Dama nera selezionata
     * 8 -> Damone bianco selezionato
     * 9 -> Damone nero selezionato
     *
     * 
     * Legenda turno
     * 0 -> Turno giocatore bianco
     * 1 -> Turno giocatore nero
     *
     * primoClickRiga e primoClickColonna contengono l'indice della tabella in cui è stato fatto il
     * click
     *
     * pedine nere e pedine bianche indicano il numero di pedine ancora presenti del rispettivo giocatore
     */

    private int turno;
    private int pedineNere, pedineBianche;
    private int secondoClickRiga, secondoClickColonna;
    private int numClick;
    private int quanteMangiate;
    private int primoClickRiga, primoClickColonna;
    private int[][] matrice = new int[8][8];
    private boolean mangiato = false;
    private boolean potevaMangiare = false;
    private final ArrayList<Mossa> mosse;
    private int errore = 0;
    private javax.swing.Timer timerDati;
    private final int delay = 1100;
    private boolean sconfittaB = false, sconfittaN = false;
    ActionListener listener;

    /**
     * Costruttore per la partita
     */
    public Dati() {
        pedineNere = 12;
        pedineBianche = 12;
        turno = 0;
        numClick = 0;
        quanteMangiate = 0;
        mosse = new ArrayList<Mossa>();
        inizializza();
        //Timer
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                generaMossa();
            }
        };
        timerDati = new javax.swing.Timer(delay, listener);

    }

    /**
     * Costruttore da utilizzare per simulare le mosse
     *
     * @param m matrice da copiare
     * @param mo mosse da copiare
     */
    public Dati(int[][] m, ArrayList<Mossa> mo) {
        this.matrice = m;
        this.mosse = new ArrayList<Mossa>();
        this.mosse.addAll(mo);
        this.numClick = 0;
        this.turno = 1;
        this.quanteMangiate = 0;
    }

    /**
     * Funzione che inizializza la matrice con le pedine in posizione di
     * partenza.
     */
    public final void inizializza() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i <= 2) {
                    //Popolo con i pedine nere
                    if ((i + j) % 2 == 0) {
                        //Somma pari
                        matrice[i][j] = 3;
                    } else {
                        //Somma dispari
                        matrice[i][j] = 0;
                    }
                } else if (i > 2 && i < 5) {
                    //Vuoto
                    if ((i + j) % 2 == 0) {
                        //Somma pari
                        matrice[i][j] = 1;
                    } else {
                        //Somma dispari
                        matrice[i][j] = 0;
                    }
                } else if (i >= 5) {
                    //Popolo con pedine bianche
                    if ((i + j) % 2 == 0) {
                        //Somma pari
                        matrice[i][j] = 2;
                    } else {
                        //Somma dispari
                        matrice[i][j] = 0;
                    }
                }
            }
        }
    }

    /**
     * Cambia il turno corrente
     */
    public void cambiaTurno() {
        this.turno = 1 - this.turno;
        this.numClick = 0;
        this.potevaMangiare = false;
        quanteMangiate = 0;
        if (turno == 1) {
            //Aspetta delay e lancia genera mossa
            mosse.clear();
            timerDati.start();
        }
    }

    /**
     * Imposta la casella i,j con il valore value
     *
     * @param i indice riga
     * @param j indice colonna
     * @param value valore da assegnare
     */
    public void setValue(int i, int j, int value) {
        this.matrice[i][j] = value;
    }

    /**
     * Salva gli indici riga e colonna del primo click
     *
     * @param r indice riga del primo click
     * @param c indice colonna del secondo click
     */
    public void setClick(int r, int c) {
        this.primoClickRiga = r;
        this.primoClickColonna = c;
    }

    /**
     * Imposta il numero di click
     *
     * @param n numero di click da impostare
     */
    public void setNumClick(int n) {
        this.numClick = n;
    }

    /**
     * Ritorna che cosa contiene la damiera in posizione i,j
     *
     * @param i indice riga
     * @param j indice colonna
     * @return matrice[i][j] cosa contiene la damiera in posizione i,j
     */
    public int getValue(int i, int j) {
        return this.matrice[i][j];
    }

    /**
     * Ritorna il turno corrente
     *
     * @return Turno corrente (0: bianco / 1: nero)
     */
    public int getTurno() {
        return this.turno;
    }

    /**
     * Ritorna il numero di click
     *
     * @return Il numero di click effettuati nel turno corrente (0: primo / 1:
     * secondo)
     */
    public int getNumClick() {
        return this.numClick;
    }

    /**
     * Ritorna il numero di pedine bianche
     *
     * @return numero di pedine bianche
     */
    public int getPedineBianche() {
        return this.pedineBianche;
    }

    /**
     * Ritorna il numero di pedine nere
     *
     * @return numero di pedine nere
     */
    public int getPedineNere() {
        return this.pedineNere;
    }

    /**
     * Ritorna se c'è un errore
     *
     * @return 1 se c'è un errore, 0 se non c'è
     */
    public int getErrore() {
        return this.errore;
    }

    /**
     * Ritorna se il giocatore nero è stato sconfitto
     *
     * @return true se il nero è stato sconfitto, false altrimenti
     */
    public boolean getSconfittaN() {
        return this.sconfittaN;
    }

    /**
     * Ritorna se il giocatore bianco è stato sconfitto
     *
     * @return true se il bianco è stato sconfitto, false altrimenti
     */
    public boolean getSconfittaB() {
        return this.sconfittaB;
    }

    /**
     * Verifica che gli indici riga e colonna del click utente siano corretti
     *
     * @param r indice riga
     * @param c indice colonna
     * @return 0 se la verifica è andata a buon fine, 1 se è andata a buon fine
     * e il turno viene cambiato, -1 se non è andata a buon fine
     */
    public int check(int r, int c) {
        if (verifica(r, c) && numClick == 0) {
            primoClickRiga = r;
            primoClickColonna = c;
            numClick++;
            this.errore = 0;
            matrice[r][c] += 4;
            return 0;
        }
        if (numClick == 1 && (primoClickRiga == r && primoClickColonna == c) && quanteMangiate == 0) {
            matrice[r][c] -= 4;
            numClick = 0;
            return 0;
        }
        if (verifica(r, c) && numClick == 1) {
            secondoClickRiga = r;
            secondoClickColonna = c;
            move(primoClickRiga, primoClickColonna, secondoClickRiga, secondoClickColonna);
            matrice[secondoClickRiga][secondoClickColonna] += 4;
            //Controllo per la doppia mangiata
            if (puoMangiare(secondoClickRiga, secondoClickColonna, 0) && quanteMangiate > 0) {
                primoClickRiga = secondoClickRiga;
                primoClickColonna = secondoClickColonna;
                mangiato = false;
                if (turno == 1) {
                    //Aspetta delay e lancia genera mossa
                    timerDati.start();
                }
                return 0;
            }
            matrice[secondoClickRiga][secondoClickColonna] -= 4;
            numClick--;
            cambiaTurno();
            return 1;
        }
        this.errore = 1;
        return -1;
    }

    /**
     * Verifica se la pedina in posizione r,c può muoversi
     *
     * @param r indice riga
     * @param c indice colonna
     * @return true se può muoversi, false altrimenti
     */
    private boolean puoMuovere(int r, int c) {
        if (this.matrice[r][c] == 2) {
            //Pedina bianca
            if (c == 0) {
                //Margine sx
                return (this.matrice[r - 1][c + 1] == 1);
            }
            if (c == 7) {
                //Margine dx
                return (this.matrice[r - 1][c - 1] == 1);
            }
            if (c > 0 && c < 7) {
                //Centrale
                return ((this.matrice[r - 1][c + 1] == 1) || (this.matrice[r - 1][c - 1] == 1));
            }
        } else if (this.matrice[r][c] == 3) {
            //Pedina nera
            if (c == 0) {
                //Margine sx
                return (this.matrice[r + 1][c + 1] == 1);
            }
            if (c == 7) {
                //Margine dx
                return (this.matrice[r + 1][c - 1] == 1);
            }
            if (c > 0 && c < 7) {
                //Centrale
                return ((this.matrice[r + 1][c + 1] == 1) || (this.matrice[r + 1][c - 1] == 1));
            }
        } else {
            //Damone
            if (c == 0 && r == 0) {
                //Angolo alto sx
                return (this.matrice[r + 1][c + 1] == 1);
            }
            if (c == 7 && r == 7) {
                //Angolo basso dx
                return (this.matrice[r - 1][c - 1] == 1);
            }
            if (r == 0) {
                //Alto
                return ((this.matrice[r + 1][c - 1] == 1) || (this.matrice[r + 1][c + 1] == 1));
            }
            if (r == 7) {
                //Basso
                return ((this.matrice[r - 1][c - 1] == 1) || (this.matrice[r - 1][c + 1] == 1));
            }
            if (c == 0) {
                //Margine sx
                return ((this.matrice[r - 1][c + 1] == 1) || (this.matrice[r + 1][c + 1] == 1));
            }
            if (c == 7) {
                //Margine dx
                return ((this.matrice[r - 1][c - 1] == 1) || (this.matrice[r + 1][c - 1] == 1));
            } else {
                //Centrale
                return ((this.matrice[r - 1][c - 1] == 1) || (this.matrice[r - 1][c + 1] == 1)
                        || (this.matrice[r + 1][c - 1] == 1) || (this.matrice[r + 1][c + 1] == 1));
            }
        }
        return false;
    }

    /**
     * Funzione che controlla se le coordinate del secondo click individuano una
     * cella adiacente a quelle del primo click
     *
     * @param r indice riga
     * @param c indice colonna
     * @return true se le coordinate sono corrette, false altrimenti
     */
    private boolean adiacente(int r, int c) {
        if (this.matrice[r][c] == 1) {
            if (this.matrice[primoClickRiga][primoClickColonna] == 6) {
                //Pedina bianca
                return (r == primoClickRiga - 1 && (c == primoClickColonna + 1 || c == primoClickColonna - 1));
            }
            if (this.matrice[primoClickRiga][primoClickColonna] == 7) {
                //Pedina nera
                return (r == primoClickRiga + 1 && (c == primoClickColonna + 1 || c == primoClickColonna - 1));
            }
            if (this.matrice[primoClickRiga][primoClickColonna] == 8 + turno) {
                //Damone
                return ((r == primoClickRiga + 1 || r == primoClickRiga - 1)
                        && (c == primoClickColonna + 1 || c == primoClickColonna - 1));
            }
        }
        return false;
    }

    /**
     * Funzione che controlla se le coordinate del click individuano una pedina
     * che può mangiare
     *
     * @param r indice riga
     * @param c indice colonna
     * @param nClick numero del click (primo o secondo)
     * @return true se la verifica va a buon fine, false altrimenti
     */
    private boolean puoMangiare(int r, int c, int nClick) {
        /* numClick = 0 -> r e c contengono le coordinate del primo click
         * numClick = 1 -> r e c contengono le coordinate del secondo click
         */
        if (nClick == 0) {
            //Primo click, devo controllare se la pedina selezionata può mangiare
            if (adiacenteVuota(r, c, this.turno)) {
                this.potevaMangiare = true;
                return true;
            }
        }
        if (nClick == 1) {
            //Secondo click, devo controllare se la cella selezionata corrisponde alla destinazione giusta per mangiare
            if (Math.abs(primoClickRiga - r) <= 2 && Math.abs(primoClickColonna - c) <= 2) {
                int mediaRiga = (primoClickRiga + r) / 2;
                int mediaColonna = (primoClickColonna + c) / 2;
                if (this.matrice[primoClickRiga][primoClickColonna] == 6) { //turno+2
                    //Pedina bianca
                    if (this.matrice[mediaRiga][mediaColonna] == 3 && primoClickRiga == r + 2) {
                        mangiato = true;
                        return true;
                    }
                }
                if (this.matrice[primoClickRiga][primoClickColonna] == 7) { //turno+2
                    //Pedina nera
                    if (this.matrice[mediaRiga][mediaColonna] == 2 && primoClickRiga == r - 2) {
                        mangiato = true;
                        return true;
                    }
                }
                if (this.matrice[primoClickRiga][primoClickColonna] == (turno + 8)) { //turno+ 4
                    //Damone
                    if ((this.matrice[mediaRiga][mediaColonna] == (5 - turno))
                            || (this.matrice[mediaRiga][mediaColonna] == (3 - turno))) {
                        mangiato = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Funzione che si occupa di spostare una pedina da sorgente a destinazione
     * e di decrementare le pedine in caso di mangiata
     *
     * @param sorgenteRiga indice riga della sorgente
     * @param sorgenteColonna indice colonna della sorgente
     * @param destinazioneRiga indice riga della destinazione
     * @param destinazioneColonna indice colonna della destinazione
     *
     */
    private void move(int sorgenteRiga, int sorgenteColonna, int destinazioneRiga, int destinazioneColonna) {
        if (destinazioneRiga == 0 && this.matrice[sorgenteRiga][sorgenteColonna] == 6) {
            this.matrice[destinazioneRiga][destinazioneColonna] = 8;
            quanteMangiate = -1;
        } else if (destinazioneRiga == 7 && this.matrice[sorgenteRiga][sorgenteColonna] == 7) {
            this.matrice[destinazioneRiga][destinazioneColonna] = 9;
            quanteMangiate = -1;
        } else {
            this.matrice[destinazioneRiga][destinazioneColonna] = this.matrice[sorgenteRiga][sorgenteColonna];
        }
        this.matrice[sorgenteRiga][sorgenteColonna] = 1;
        this.matrice[destinazioneRiga][destinazioneColonna] -= 4;
        if (mangiato) {
            quanteMangiate++;
            this.matrice[(destinazioneRiga + sorgenteRiga) / 2][(destinazioneColonna + sorgenteColonna) / 2] = 1;
            if (turno == 1) {
                pedineBianche--;
                if (pedineBianche == 0) {
                    sconfittaB = true;
                }
            } else {
                pedineNere--;
                if (pedineNere == 0) {
                    sconfittaN = true;
                }
            }
        }
        this.mangiato = false;
        this.errore = 0;
    }

    /**
     * Si entra in questa funzione quando c'è una pedina da mangiare. La
     * funzione si occupa di calcolare se è stato fatto un click nella cella
     * vuota, così da poter effettuare la mangiata
     *
     * @param r indice riga
     * @param c indice colonna
     * @param turno turno
     *
     * @return true se la verifica è andata a buon fine, false altrimenti
     *
     */
    private boolean adiacenteVuota(int r, int c, int turno) {
        if (this.matrice[r][c] == 2 || this.matrice[r][c] == 6) {
            //Turno BIANCO: pedina nera da mangiare
            if (r > 1) {
                if (c < 2 && this.matrice[r - 1][c + 1] == 3) {
                    //Margine sx
                    return (this.matrice[r - 2][c + 2] == 1);
                }
                if (c > 5 && this.matrice[r - 1][c - 1] == 3) {
                    //Margine dx
                    return (this.matrice[r - 2][c - 2] == 1);
                }
                if (c >= 2 && c <= 5) {
                    //Centrale sx o dx
                    return ((this.matrice[r - 1][c - 1] == 3) && this.matrice[r - 2][c - 2] == 1)
                            || ((this.matrice[r - 2][c + 2] == 1) && (this.matrice[r - 1][c + 1] == 3));
                }
            }
            return false;
        }
        if (this.matrice[r][c] == 3 || this.matrice[r][c] == 7) {
            if (r < 6) {
                //Turno NERO: pedina bianca da mangiare
                if (c < 2 && this.matrice[r + 1][c + 1] == 2) {
                    //Margine sx
                    return (this.matrice[r + 2][c + 2] == 1);
                }
                if (c > 5 && this.matrice[r + 1][c - 1] == 2) {
                    //Margine dx
                    return (this.matrice[r + 2][c - 2] == 1);
                }
                if (c >= 2 && c <= 5) {
                    //Centrale sx o dx
                    return ((this.matrice[r + 1][c + 1] == 2) && this.matrice[r + 2][c + 2] == 1)
                            || ((this.matrice[r + 1][c - 1] == 2) && (this.matrice[r + 2][c - 2] == 1));
                }
            }
            return false;
        }
        //Damone
        if (c <= 1 && r <= 1) {
            //Angolo alto sx
            return (this.matrice[r + 2][c + 2] == 1 && (this.matrice[r + 1][c + 1] == 3 - turno || this.matrice[r + 1][c + 1] == 5 - turno));
        }
        if (c >= 6 && r >= 6) {
            //Angolo basso dx
            return (this.matrice[r - 2][c - 2] == 1 && (this.matrice[r - 1][c - 1] == 3 - turno || this.matrice[r - 1][c - 1] == 5 - turno));
        }
        if (r <= 1 && c >= 6) {
            //Angolo alto dx
            return (this.matrice[r + 2][c - 2] == 1 && (this.matrice[r + 1][c - 1] == 3 - turno || this.matrice[r + 1][c - 1] == 5 - turno));
        }
        if (r >= 6 && c <= 1) {
            //Angolo basso sx
            return ((this.matrice[r - 2][c + 2] == 1) && (this.matrice[r - 1][c + 1] == 3 - turno || this.matrice[r - 1][c + 1] == 5 - turno));
        }
        if (r <= 1) {
            //Alto sx o dx
            return (((this.matrice[r + 2][c + 2] == 1)
                    && ((this.matrice[r + 1][c + 1] == 3 - turno || this.matrice[r + 1][c + 1] == 5 - turno)))
                    || ((this.matrice[r + 2][c - 2] == 1)
                    && ((this.matrice[r + 1][c - 1] == 3 - turno || this.matrice[r + 1][c - 1] == 5 - turno))));
        }
        if (r >= 6) {
            //Basso sx o dx
            return (((this.matrice[r - 2][c + 2] == 1)
                    && ((this.matrice[r - 1][c + 1] == 3 - turno || this.matrice[r - 1][c + 1] == 5 - turno)))
                    || ((this.matrice[r - 2][c - 2] == 1)
                    && ((this.matrice[r - 1][c - 1] == 3 - turno || this.matrice[r - 1][c - 1] == 5 - turno))));
        }
        if (c <= 1) {
            //Margine sx su o giù
            return (((this.matrice[r - 2][c + 2] == 1)
                    && ((this.matrice[r - 1][c + 1] == 3 - turno || this.matrice[r - 1][c + 1] == 5 - turno)))
                    || ((this.matrice[r + 2][c + 2] == 1)
                    && ((this.matrice[r + 1][c + 1] == 3 - turno || this.matrice[r + 1][c + 1] == 5 - turno))));

        } else if (c >= 6) {
            //Margine dx su o giù
            return (((this.matrice[r + 2][c - 2] == 1)
                    && ((this.matrice[r + 1][c - 1] == 3 - turno || this.matrice[r + 1][c - 1] == 5 - turno)))
                    || ((this.matrice[r - 2][c - 2] == 1)
                    && ((this.matrice[r - 1][c - 1] == 3 - turno || this.matrice[r - 1][c - 1] == 5 - turno))));
        } else {
            //Centrale sx su o giu o dx su o giù
            return (((this.matrice[r + 2][c + 2] == 1)
                    && ((this.matrice[r + 1][c + 1] == 3 - turno || this.matrice[r + 1][c + 1] == 5 - turno)))
                    || ((this.matrice[r + 2][c - 2] == 1)
                    && ((this.matrice[r + 1][c - 1] == 3 - turno || this.matrice[r + 1][c - 1] == 5 - turno)))
                    || ((this.matrice[r - 2][c + 2] == 1)
                    && ((this.matrice[r - 1][c + 1] == 3 - turno || this.matrice[r - 1][c + 1] == 5 - turno)))
                    || ((this.matrice[r - 2][c - 2] == 1)
                    && ((this.matrice[r - 1][c - 1] == 3 - turno || this.matrice[r - 1][c - 1] == 5 - turno))));
        }
    }

    /**
     * In base al numero di click, va a controllare se è stata scelta una pedina
     * corretta. Se il click è il primo si va a verificare che sia stata scelta
     * una pedina che può mangiare o, eventualmente, che può muovere. Se il
     * click è il secondo si verifica che sia stata scelta la destinazione
     * giusta.
     *
     * @param r indice riga
     * @param c indice colonna
     *
     * @return true se la verifica va a buon fine, false altrimenti
     *
     */
    public boolean verifica(int r, int c) {
        if (numClick == 0) {
            //Primo click
            if (this.matrice[r][c] == 2 + turno || this.matrice[r][c] == 4 + turno) {
                boolean trovato = false;
                for (int i = 0; i < 8; i += 1) {
                    for (int j = 0; j < 8; j += 1) {
                        if (this.matrice[i][j] == 2 + turno || this.matrice[i][j] == 4 + turno) {
                            if (puoMangiare(i, j, numClick)) {
                                if (i == r && j == c) {
                                    return true;
                                }
                                trovato = true;
                            }
                        }
                    }
                }
                if (trovato) {
                    return false;
                }
                return puoMuovere(r, c);
            }
        }
        if (numClick == 1) {
            //Secondo click
            if (this.matrice[r][c] == 1) {
                if (potevaMangiare) {
                    return puoMangiare(r, c, numClick);
                } else {
                    return adiacente(r, c);
                }
            }
        }
        return false;
    }

    /**
     * La funzione si occupa di far generare una mossa all'IA.
     *
     */
    private void generaMossa() {
        timerDati.stop();
        ArrayList<Mossa> temp = new ArrayList<Mossa>();
        numClick = 0;
        temp.addAll(calcolaMosse(1, mosse));
        mosse.clear();
        mosse.addAll(temp);
        temp.clear();
        int[][] nuova = new int[8][8];
        copiaMatrice(matrice, nuova);
        Dati appoggio = new Dati(nuova, this.mosse);
        int indice = appoggio.simula();
        if (indice == -1) {
            //Sconfitta
            System.out.println("Non ho più mosse!");
            mosse.clear();
            this.sconfittaN = true;
            return;
        }

        System.out.println("Muovo da ["+mosse.get(indice).getSorgenteRiga()+","+mosse.get(indice).getSorgenteColonna()
                                +"] a ["+mosse.get(indice).getDestinazioneRiga()+","+mosse.get(indice).getDestinazioneColonna()+"]");
        matrice[mosse.get(indice).getSorgenteRiga()][mosse.get(indice).getSorgenteColonna()] += 4;
        move(mosse.get(indice).getSorgenteRiga(),
                mosse.get(indice).getSorgenteColonna(),
                mosse.get(indice).getDestinazioneRiga(),
                mosse.get(indice).getDestinazioneColonna());
        if (puoMangiare(mosse.get(indice).getDestinazioneRiga(), mosse.get(indice).getDestinazioneColonna(), 0) && quanteMangiate > 0) {
            int tempR, tempC;
            tempR = mosse.get(indice).getDestinazioneRiga();
            tempC = mosse.get(indice).getDestinazioneColonna();
            mosse.clear();
            mosse.add(new Mossa(tempR, tempC));
            mosse.get(0).setPeso(Double.MAX_VALUE);
            //Aspetta delay e lancia genera mossa
            timerDati.start();
        } else {
            mosse.clear();
            cambiaTurno();
        }
    }

    /**
     * Controlla se il giocatore bianco ha ancora mosse a disposizione o meno.
     */
    public void nessunaMossa() {
        boolean test = false;
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < 8; j += 1) {
                if (this.matrice[i][j] == 2 || this.matrice[i][j] == 4) {
                    if (puoMangiare(i, j, 0) || puoMuovere(i, j)) {
                        test = true;
                    }
                }
            }
        }
        if (test == false) {
            this.sconfittaB = true;
        }
        potevaMangiare = false;
    }

    /**
     * Si occupa di simulare, per ogni mossa disponibile all'IA, tutte le mosse
     * disponibili all'utente e guarda quella che porterebbe più vantaggio alla
     * prima.
     *
     * @return indice della mossa da eseguire, -1 se non ci sono mosse
     * disponibili
     */
    private int simula() {
        int jack[][] = new int[8][8];
        copiaMatrice(matrice, jack);
        ArrayList<Mossa> contromosse = new ArrayList<Mossa>();
        ArrayList<Mossa> temp = new ArrayList<Mossa>();
        ArrayList<Integer> migliori = new ArrayList<Integer>();
        int count = 0;
        double max = -1000;
        this.turno = 1;
        for (Mossa m : mosse) {
            potevaMangiare = false;
            matrice[m.getSorgenteRiga()][m.getSorgenteColonna()] += 4;
            move(m.getSorgenteRiga(), m.getSorgenteColonna(), m.getDestinazioneRiga(), m.getDestinazioneColonna());
            turno = 0;
            temp.addAll(calcolaMosse(0, contromosse));
            contromosse.clear();
            contromosse.addAll(temp);
            temp.clear();
            if (!contromosse.isEmpty()) {
                for (Mossa c : contromosse) {
                    max = c.getPeso() > max ? c.getPeso() : max;
                }
                if (max > 0.5) {
                    m.setPeso(-max);
                }
            } else {
                m.setPeso(Double.MAX_VALUE);
            }
            copiaMatrice(jack, matrice);
            contromosse.clear();
            max = -1000;
            turno = 1;
        }
        max = -1000;
        for (Mossa m : mosse) {
            max = m.getPeso() > max ? m.getPeso() : max;
        }
        count = 0;
        if (mosse.size() == 1) {
            migliori.add(count);
        } else {
            for (Mossa m : mosse) {
                if (m.getPeso() >= max) {
                    migliori.add(count);
                }
                count++;
            }
        }
        Random r = new Random();
        int scelta;
        if (!migliori.isEmpty()) {
            scelta = migliori.get(r.nextInt(migliori.size()));
            return scelta;
        } else {
            //Sconfitta
            return -1;
        }
    }

    /**
     * In base al turno, calcola le possibili mosse del giocatore
     *
     * @param turno turno (0 bianco, 1 nero)
     * @param old ArrayList di mosse
     * @return ArrayList di mosse disponibili
     */
    private ArrayList<Mossa> calcolaMosse(int turno, ArrayList<Mossa> old) {
        int count = 0;
        double peso;
        if (old.isEmpty()) {
            //Scansione della matrice per vedere le possibili pedine da muovere 
            for (int i = 0; i < 8; i += 1) {
                for (int j = 0; j < 8; j += 1) {
                    if (matrice[i][j] == 2 + turno || matrice[i][j] == 4 + turno) {
                        if (verifica(i, j)) {
                            old.add(new Mossa(i, j));
                            //verifica bontà mossa
                            if (puoMangiare(i, j, 0)) {
                                //se può mangiare ha peso più alto rispetto a se può solo muovere
                                peso = turno == 0 ? 1.3 : 1.0;
                                //peso = 1.0;
                                old.get(count).setPeso(peso);
                                if (i == (turno + 2 + 2 * turno) && matrice[i][j] == 2 + turno) {
                                    old.get(count).setPeso(0.5);
                                }
                            } else {
                                old.get(count).setPeso(0.2);
                                if (i == (1 + 5 * turno) && matrice[i][j] == 2 + turno) {
                                    old.get(count).setPeso(0.5);
                                }
                            }
                            count++;
                        }
                    }
                }
            }
        }
        numClick = 1;
        ArrayList<Mossa> temp2 = new ArrayList<Mossa>();
        count = 0;
        for (Mossa m : old) {
            primoClickRiga = m.getSorgenteRiga();
            primoClickColonna = m.getSorgenteColonna();
            matrice[primoClickRiga][primoClickColonna] += 4;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (verifica(i, j)) {
                        temp2.add(new Mossa(m.getSorgenteRiga(),
                                m.getSorgenteColonna(),
                                i, j));
                        temp2.get(count).setPeso(m.getPeso());
                        count++;
                    }
                }
            }
            matrice[primoClickRiga][primoClickColonna] -= 4;
        }
        old.clear();
        old.addAll(temp2);
        temp2.clear();
        numClick = 0;
        return old;
    }

    /**
     * Copia i dati contenuti nella matrice sorgente in matrice destinazione
     *
     * @param sorgente matrice sorgente
     * @param destinazione matrice destinazione
     */
    private void copiaMatrice(int[][] sorgente, int[][] destinazione) {
        for (int i = 0; i < sorgente.length; i++) {
            System.arraycopy(sorgente[i], 0, destinazione[i], 0, sorgente.length);
        }
    }
}
