package com.ksoudier.cassebrique;

import com.ksoudier.cassebrique.Model.Balle;
import com.ksoudier.cassebrique.Model.Barre;
import com.ksoudier.cassebrique.Model.Bonus;
import com.ksoudier.cassebrique.Model.Brique;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class CasseBrique extends Canvas{
    public boolean statusPartie=true;//false arrété true jeu en cours
    public boolean recommencer=false;//false si non true si souhait de refaire une partie
    public final static int LARGEUR =500;
    public final static int LONGUEUR =600;
    public static int FPS= 300;
    public static Barre LABARRE = new Barre(75,(LARGEUR/2-40),LONGUEUR-20,10);
    public static ArrayList<Balle> lesBalles= new ArrayList<>();
    public static ArrayList<Bonus> lesBonus = new ArrayList<>();
    public static ArrayList<Brique> lesBriques = new ArrayList<>();
    public static ArrayList<Balle> ballesAAjouter= new ArrayList<>();
    public static ArrayList<Balle> ballesARetirer= new ArrayList<>();
    public static ArrayList<Brique> briquesARetirer= new ArrayList<>();
    public static ArrayList<Bonus> bonusARetirer= new ArrayList<>();
    public CasseBrique() throws InterruptedException {
        recommencer=false;
        JFrame fenetre = new JFrame("Casse brique");
        //On récupère le panneau de la fenetre principale
        JPanel panneau = (JPanel) fenetre.getContentPane();
        //On définie la hauteur / largeur de l'écran
        panneau.setPreferredSize(new Dimension(LARGEUR, LONGUEUR));
        setBounds(0, 0, LARGEUR, LONGUEUR);
        //On ajoute cette classe (qui hérite de Canvas) comme composant du panneau principal
        panneau.add(this);

        fenetre.pack();
        fenetre.setResizable(false);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.requestFocus();
        fenetre.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==37){
                    LABARRE.deplacementGauche();
                }
                if(e.getKeyCode()==39){
                    LABARRE.deplacementDroite();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //On indique que le raffraichissement de l'ecran doit être fait manuellement.
        createBufferStrategy(2);
        setIgnoreRepaint(true);
        this.setFocusable(false);
        demarrer();
        fenetre.dispose();
        return;
    }

    public void demarrer() throws InterruptedException {
        int positionX= LARGEUR /2;
        int positionY= LONGUEUR -50;
        int tailleBalle;
        int tailleBonus;
        for(int i=0; i<1;i++){
            tailleBalle=(int)(10);
            positionX= LARGEUR/2;
            positionY=LONGUEUR-200;
            lesBalles.add(new Balle(positionX,positionY,(int)(Math.random()*4-2),(int)(Math.random()*4-2),tailleBalle));
        }

        for(int i=0; i<10;i++){
            tailleBonus=(20);
            positionX= (int)(Math.random()*(LARGEUR -tailleBonus));
            positionY=(int)(Math.random()*((LONGUEUR -tailleBonus)));
            int type= (int)(Math.random()*3+1);
            lesBonus.add(new Bonus(tailleBonus,positionX,positionY,type));
        }
        int BpositionX=LARGEUR-20;
        int BpositionY=0;
        ArrayList<Integer> lesTypesPossibles= new ArrayList<Integer>();
        lesTypesPossibles.add(Brique.BRIQUEBASIQUE);
        lesTypesPossibles.add(Brique.BRIQUEAMELIOREE);
        lesTypesPossibles.add(Brique.BRIQUEDURE);
        for(int i=0;i<10;i++){
            BpositionX=LARGEUR-20;
            BpositionY+=20;
            for(int j=0;j<9;j++){
                BpositionX-=50;
                int leType=lesTypesPossibles.get((int)((Math.random() * 3 )));
                lesBriques.add(new Brique(50,BpositionX,BpositionY,20, leType,leType));
            }
        }

        while(statusPartie) {
            Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();
            dessin.setColor(Color.white);
            dessin.fillRect(0,0, LARGEUR, LONGUEUR);
            this.PerteDeBalle();
            for (Balle uneBalle:lesBalles) {
                uneBalle.dessiner(dessin);
                uneBalle.mouvement();
                uneBalle.collision();
                this.collisionBonus(uneBalle,lesBonus); // ajouter nouveau tableau de balles pour eviter un bug ici
            }
            ajouterBalle();
            for (Brique uneBrique: lesBriques){
                uneBrique.dessiner(dessin);
            }

            for (Bonus unBonus:lesBonus) {
                unBonus.dessiner(dessin);
            }
            LABARRE.dessiner(dessin);
            this.collisionBalleBrique();

            dessin.dispose();
            getBufferStrategy().show();
            Thread.sleep(1000 / FPS);
            if(!statusPartie){
                break;
            }
            if(recommencer){
                FPS= 300;
                LABARRE = new Barre(75,(LARGEUR/2-40),LONGUEUR-20,10);
                return;
            }
        }
    }
    public void collisionBalleBrique(){
        for (Balle uneBalle:lesBalles) {
            for (Brique uneBrique:lesBriques) {
                //Cas ou le bas de la balle le haut du bonus
                if(uneBrique.collisionHautBas(uneBalle)){
                    break;
                }
                //Cas ou le bas de la balle touche le haut d'une brique
                if(uneBrique.collisionBasHaut(uneBalle)){
                    break;
                }

                //Cas ou la Gauche de la balle touche le flanc droit de la brique
                if(uneBrique.collisionGaucheDroite(uneBalle)){
                    break;
                }

                //Cas ou la droite de la balle touche un flanc de la brique
                if(uneBrique.collisionDroiteGauche(uneBalle)){
                    break;
                }
            }
        }
        enleverBrique();
        if(lesBriques.isEmpty()){
            GameOver("BRAVO VOUS AVEZ GAGNÉ");
            if(!statusPartie){
                System.exit(200);
            }
            else{
                this.recommencerUnePartie();
            }
        }
    }
    public void collisionBonus(Balle uneBalle, ArrayList<Bonus> lesBonus){
        for (Bonus unBonus:lesBonus) {
            //Cas ou le bas de la balle le haut du bonus
            if(unBonus.collisionBasHaut(uneBalle)){
                unBonus.effectuerBonus();
            }
            //Collision haut de la balle, bas du bonus
           if(unBonus.collisionHautBas(uneBalle)){
               unBonus.effectuerBonus();
           }
            //Collision gauche de la balle, droite du bonus
           if(unBonus.collisionGaucheDroite(uneBalle)){
               unBonus.effectuerBonus();
           }
            //Collision droite de la balle, Gauche du bonus
            if(unBonus.collisionDroiteGauche(uneBalle)){
                unBonus.effectuerBonus();
            }
        }
        enleverBonus();
    }

    public void ajouterBalle(){
        for (Balle uneBalle:ballesAAjouter){
            lesBalles.add(uneBalle);
        }
        ballesAAjouter.clear();
    }
    public void enleverBalle(){
        for (Balle uneBalle : ballesARetirer) {
            this.lesBalles.remove(uneBalle);
        }
        ballesARetirer.clear();
        if(lesBalles.isEmpty()) {
            GameOver("GAME OVER");
            this.recommencerUnePartie();
        }
    }
    public boolean recommencerUnePartie(){
        lesBonus.clear();
        lesBalles.clear();
        lesBriques.clear();
        if(!statusPartie){
            System.exit(200);
            return false;
        }
        else{
            return true;
        }
    }
    public void enleverBrique(){
        for (Brique uneBrique:briquesARetirer) {
            lesBriques.remove(uneBrique);
        }
        briquesARetirer.clear();
    }
    public void enleverBonus(){
        for (Bonus unBonus: bonusARetirer) {
            lesBonus.remove(unBonus);
        }
        bonusARetirer.clear();
    }
    public void PerteDeBalle(){
        for (Balle uneBalle: lesBalles) {
            if((uneBalle.getPositionY()+ uneBalle.getDiametre())>= LONGUEUR){
                this.ballesARetirer.add(uneBalle);
            }
        }
        enleverBalle();
    }
    public void GameOver(String message){
        if(!recommencer){
            JFrame fenetre = new JFrame(message);
            if(JOptionPane.showConfirmDialog(null,message+"\nSouhaitez vous refaire une partie ?",
                    "GAME OVER", JOptionPane.YES_NO_OPTION)==0) {
                statusPartie=true;
                recommencer=true;
            }
            else{
                statusPartie=false;
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        while(true){
            new CasseBrique();
        }
    }

}