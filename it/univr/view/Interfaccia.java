package it.univr.view;

import it.univr.control.Dati;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe che chiede all'utente quale interfaccia grafica vuole utilizzare per
 * il gioco.
 *
 * @author Valeria Gottelli, Alberto Miglio, Andrea Zenatti
 */
public class Interfaccia extends JFrame implements ActionListener {

    private final JLabel label1, label2;
    private final JButton classica;
    private final JButton moderna;
    private final JButton star;
    private final JButton allinea;
    private final JButton marmo;

    /**
     * Costruttore della classe
     */
    public Interfaccia() {
        label1 = new JLabel("Benvenuto nel gioco della Dama.");
        label2 = new JLabel("Prima di cominciare seleziona l'interfaccia grafica con cui vuoi giocare.");
        classica = new JButton("Dama Classica");
        moderna = new JButton("Dama Moderna");
        star = new JButton("Dama Star Wars");
        marmo = new JButton("Dama di Marmo");
        allinea = new JButton("");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 600);
        this.setVisible(true);

        this.add(label1);
        this.add(label2);
        this.add(classica);
        this.add(moderna);
        this.add(marmo);
        this.add(star);
        this.add(allinea);

        label1.setBounds(10, 0, 300, 30);
        label2.setBounds(10, 15, 600, 30);
        classica.setBounds(170, 50, 200, 30);
        moderna.setBounds(170, 80, 200, 30);
        marmo.setBounds(170, 110, 200, 30);
        star.setBounds(170, 140, 200, 30);
        allinea.setBounds(170, 170, 200, 30);

        classica.addActionListener((ActionListener) this);
        moderna.addActionListener((ActionListener) this);
        star.addActionListener((ActionListener) this);
        marmo.addActionListener((ActionListener) this);
        allinea.setEnabled(false);
        allinea.setVisible(false);
    }

    /**
     * Gestisce il click sui bottoni
     *
     * @param e sorgente del click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Dati dati;
        Damiera damiera;
        Statistiche stat;
        this.setVisible(false);
        if (e.getSource() == classica) {
            dati = new Dati();
            damiera = new Damiera(dati, 0);
            stat = new Statistiche(dati, damiera, 0);
            damiera.setVisible(true);
        }
        if (e.getSource() == moderna) {
            dati = new Dati();
            damiera = new Damiera(dati, 1);
            stat = new Statistiche(dati, damiera, 1);
            damiera.setVisible(true);
        }
        if (e.getSource() == star) {
            dati = new Dati();
            damiera = new Damiera(dati, 2);
            stat = new Statistiche(dati, damiera, 2);
            damiera.setVisible(true);
        }
        if (e.getSource() == marmo) {
            dati = new Dati();
            damiera = new Damiera(dati, 3);
            stat = new Statistiche(dati, damiera, 3);
            damiera.setVisible(true);
        }
        this.dispose();
    }
}
