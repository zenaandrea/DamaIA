package it.univr.view;

import it.univr.control.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe che si occupa di gestire una finestra contentente le statistiche del
 * gioco
 *
 * @author Valeria Gottelli, Alberto Miglio, Andrea Zenatti
 */
public class Statistiche extends JFrame {

    private final JLabel pedineBianco, pedineNero, numBianco, numNero;
    private final JLabel illegale;
    private final JLabel turno;
    private final JMenuBar barra;
    private final JMenu menu;
    private final JMenuItem ritirati, chiudi, nuova;
    private final Container c;
    private final Dati dati;
    private final JLabel sfondo;
    private final String[] path = {"/Dama_Classica/", "/Dama_Moderna/", "/Dama_StarWars/", "/Dama_Marmo/"};
    private final ImageIcon sfondi[];
    private final Damiera damiera;
    private final javax.swing.Timer timer;
    private boolean sconfitta;

    /**
     * Costruttore delle statistiche
     *
     * @param d dati associati alla sessione di gioco
     * @param damiera damiera di gioco
     * @param versione intero che indica l'interfaccia grafica scelta
     */
    public Statistiche(Dati d, Damiera damiera, int versione) {

        sfondi = new ImageIcon[]{new ImageIcon(getClass().getResource(path[versione] + "SfondoBianco.jpg")),
            new ImageIcon(getClass().getResource(path[versione] + "SfondoNero.jpg"))};
        //TIMER 
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                refreshStatistiche();
            }
        };
        int delay = 10;
        timer = new javax.swing.Timer(delay, listener);
        timer.start();
        /* 
         * Impostazioni finestra
         */
        this.setSize(500, 400);
        this.setBounds(650, 0, 400, 500);
        this.setTitle("Statistiche di gioco");
        c = this.getContentPane();
        this.damiera = damiera;
        this.dati = d;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        
        /*
         * Settaggio elementi
         */
        sconfitta=false;
        pedineBianco = new JLabel("Pedine rimanenti giocatore Bianco: ");
        pedineNero = new JLabel("Pedine rimanenti giocatore Nero: ");
        numBianco = new JLabel("12");
        numNero = new JLabel("12");
        turno = new JLabel("Turno del giocatore Bianco");
        illegale = new JLabel("");
        sfondo = new JLabel();

        illegale.setForeground(Color.RED);
        sfondo.setIcon(sfondi[0]);
        /*
         * Posizionamento elementi
         */
        c.add(pedineBianco);
        c.add(pedineNero);
        c.add(numBianco);
        c.add(numNero);
        c.add(turno);
        c.add(illegale);
        c.add(sfondo);

        pedineBianco.setBounds(0, 20, 250, 30);
        numBianco.setBounds(250, 20, 20, 30);
        pedineNero.setBounds(0, 50, 250, 30);
        numNero.setBounds(250, 50, 20, 30);
        turno.setBounds(0, 80, 200, 30);
        illegale.setBounds(210, 80, 150, 30);
        sfondo.setBounds(0, 0, 228, 400);
        /*
         * Gestione Menù
         */
        barra = new JMenuBar();
        menu = new JMenu("Partita");
        nuova = new JMenuItem("Nuova partita");
        ritirati = new JMenuItem("Ritirati");
        chiudi = new JMenuItem("Esci");

        barra.add(menu);
        menu.add(nuova);
        menu.add(ritirati);
        menu.add(chiudi);

        this.setJMenuBar(barra);
        this.setVisible(true);

        nuova.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se l'utente clicca su esci chiedo conferma
                nuovaPartita();
            }
        });

        ritirati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se l'utente clicca su ritirati chiedo conferma
                int risposta = JOptionPane.showConfirmDialog(null, "Sei sicuro di volerti ritirare?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (risposta == JOptionPane.YES_OPTION) {
                    //Se è sicuro di ritirarsi allora proseguo
                    JOptionPane.showMessageDialog(null, "Ti sei ritirato, il giocatore Nero ha vinto!");
                    System.exit(0);
                }
            }
        });

        chiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se l'utente clicca su esci chiedo conferma
                int risposta = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (risposta == JOptionPane.YES_OPTION) {
                    //Se è sicuro di uscire allora proseguo
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Aggiorna le statistiche e invoca una refresh sulla damiera
     */
    void refreshStatistiche() {
        setTurno(this.dati.getTurno());
        setNumero(this.dati.getTurno());
        setErrore(this.dati.getErrore());
        vittoria();
        this.damiera.refresh();
    }

    /**
     * Funzione utilizzata per cambiare il valore della label turno.
     *
     * @param t intero che indica il turno corrente di gioco (0 bianco, 1 nero)
     */
    public void setTurno(int t) {
        if (t == 0) {
            turno.setText("Turno del giocatore Bianco");
            sfondo.setIcon(sfondi[0]);
        } else {
            turno.setText("Turno del giocatore Nero");
            sfondo.setIcon(sfondi[1]);
        }
    }

    /**
     * Funzione utilizzata per cambiare il numero di pedine ancora disponibili
     * al giocatore t
     *
     * @param t intero che indica il turno di gioco (0 bianco, 1 nero) giocatore
     * t
     */
    public void setNumero(int t/*, int numero*/) {
        if (t == 0) {
            numBianco.setText("" + this.dati.getPedineBianche());
        } else {
            numNero.setText("" + this.dati.getPedineNere());
        }
    }

    /**
     * Setta, in base al parametro, se c'è un errore o meno
     *
     * @param x se è uguale ad 1 indica che c'è un errore, altrimenti no
     */
    public void setErrore(int x) {
        //Se 1 metti errore, se 0 non lo metti
        if (x == 1) {
            illegale.setText("Mossa non consentita.");
        } else {
            illegale.setText("");
        }
    }

    /**
     * Setta come errore la stringa passata in input
     *
     * @param s stringa con cui settare l'errore
     */
    public void setErrore(String s) {
        illegale.setText(s);
    }

    /**
     * Gestisce l'eventuale vittoria di un giocatore
     */
    private void vittoria() {
        //gestioneVittoria
        if (this.dati.getPedineBianche() == 0 || this.dati.getSconfittaB()) {
            sconfitta=true;
            JOptionPane.showMessageDialog(null, "Vince il giocatore Nero!");
            this.timer.stop();
            nuovaPartita();
        } else if (this.dati.getPedineNere() == 0 || this.dati.getSconfittaN()) {
            sconfitta=true;
            JOptionPane.showMessageDialog(null, "Vince il giocatore Bianco!");
            this.timer.stop();
            nuovaPartita();
        }
    }

    /**
     * Chiede all'utente, quando la partita corrente è finita, se vuole fare una
     * nuova partita
     */
    private void nuovaPartita() {
        int risposta = JOptionPane.showConfirmDialog(null, "Vuoi iniziare una nuova partita?", "Nuova partita!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (risposta == JOptionPane.YES_OPTION) {
            //se è sicuro di uscire allora proseguo
            damiera.dispose();
            Interfaccia interfaccia = new Interfaccia();
            this.dispose();
        } else {
            if(sconfitta)
                System.exit(0);
        }
    }
}
