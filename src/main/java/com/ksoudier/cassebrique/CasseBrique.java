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
    public final static int LARGEUR =500;
    public final static int LONGUEUR =600;
    public static Barre LABARRE = new Barre(40,(LARGEUR/2-20),LONGUEUR-20,10);
    public static ArrayList<Balle> lesBalles= new ArrayList<>();
    public static ArrayList<Bonus> lesBonus = new ArrayList<>();
    public static ArrayList<Brique> lesBriques = new ArrayList<>();
    public static ArrayList<Balle> ballesAAjouter= new ArrayList<>();
    public static ArrayList<Balle> ballesARetirer= new ArrayList<>();
    public CasseBrique() throws InterruptedException {
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
    }

    public void demarrer() throws InterruptedException {
        int positionX= LARGEUR /2;
        int positionY= LONGUEUR -50;
        int tailleBalle;
        int tailleBonus;
        for(int i=0; i<1;i++){
            tailleBalle=(int)(10);
            positionX= LARGEUR/2;
            positionY=400;
            lesBalles.add(new Balle(positionX,positionY,(int)(Math.random()*4-2),(int)(Math.random()*4-2),tailleBalle));
        }

        for(int i=0; i<20;i++){
            tailleBonus=(20);
            positionX= (int)(Math.random()*(LARGEUR -tailleBonus));
            positionY=(int)(Math.random()*((LONGUEUR -tailleBonus)+ (LONGUEUR+100)));
            int type= (int)(Math.random()*2+1);
            lesBonus.add(new Bonus(tailleBonus,positionX,positionY,type));
        }
        int BpositionX=LARGEUR-20;
        int BpositionY=0;
        for(int i=0;i<10;i++){
            BpositionX=LARGEUR-20;
            BpositionY+=20;
            for(int j=0;j<9;j++){
                BpositionX-=50;
                lesBriques.add(new Brique(50,BpositionX,BpositionY,20,Brique.BRIQUEBASIQUE,Brique.BRIQUEBASIQUE));
            }
        }

        while(true) {
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
            for (Balle uneBalle:ballesAAjouter){
                lesBalles.add(uneBalle);
            }
            ballesAAjouter.clear();
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
            Thread.sleep(1000 / 120);
        }
    }
    public void collisionBalleBrique(){
        for (Balle uneBalle:lesBalles) {
            for (Brique uneBrique:lesBriques) {
                //Cas de la balle qui touche le bas d'une brique
                if(uneBalle.getPositionX()>= uneBrique.getPositionX()&&
                (uneBalle.getPositionX()+uneBalle.getDiametre())<=(uneBrique.getPositionX()+ uneBrique.getLargeur())){
                    //Cas ou le haut de la balle touche le bas de la brique
                    if(uneBalle.getPositionY()<= uneBrique.getPositionY()+ uneBrique.getHauteur()&&
                    (uneBalle.getPositionY()+uneBalle.getDiametre()>= (uneBrique.getPositionY()+ uneBrique.getHauteur()))
                    && uneBrique.getVie()>0){
                        uneBrique.setVie(uneBrique.getVie()-1);
                        uneBalle.setVitesseVerticaleBalle(uneBalle.getVitesseVerticaleBalle()*-1);
                        if(uneBrique.getVie()==0){
                            uneBrique.setPositionX(3200);
                            uneBrique.setCouleur(Color.white);
                        }
                    }

                }
                //Cas ou la Gauche de la balle touche le flanc droit de la brique
                if(uneBalle.getPositionX()<=uneBrique.getPositionX()+uneBrique.getLargeur()&&
                uneBalle.getPositionX()>= uneBrique.getPositionX()){
                    if(uneBalle.getPositionY()>= uneBrique.getPositionY() &&
                    (uneBalle.getPositionY()+ uneBalle.getDiametre())<=(uneBrique.getPositionY()+uneBrique.getHauteur())
                    && uneBrique.getVie()>0){
                        uneBrique.setVie(uneBrique.getVie()-1);
                        uneBalle.setVitesseHorizontaleBalle(uneBalle.getVitesseHorizontaleBalle()*-1);
                        if(uneBrique.getVie()==0){
                            uneBrique.setPositionX(3200);
                            uneBrique.setCouleur(Color.white);
                        }
                    }
                }

                //Cas ou la droite de la balle touche un flanc de la brique
                if((uneBalle.getPositionX()+uneBalle.getDiametre())<=uneBrique.getPositionX()&&
                (uneBalle.getPositionX()+uneBalle.getDiametre())>= uneBrique.getPositionX()){
                    if(uneBalle.getPositionY()>= uneBrique.getPositionY() && uneBrique.getVie()>0
                    && (uneBalle.getPositionY()+uneBalle.getDiametre()<= uneBrique.getPositionY()+ uneBrique.getHauteur())){
                        uneBrique.setVie(uneBrique.getVie()-1);
                        uneBalle.setVitesseHorizontaleBalle(uneBalle.getVitesseHorizontaleBalle()*-1);
                        if(uneBrique.getVie()==0){
                            uneBrique.setPositionX(3200);
                            uneBrique.setCouleur(Color.white);
                        }
                    }
                }
            }
        }
    }
    public void collisionBonus(Balle uneBalle, ArrayList<Bonus> lesBonus){
        for (Bonus unBonus:lesBonus) {
            //si une balle sur le plan horizontal touche un bonus sur le plan horizontal
            if (uneBalle.getPositionX() >= unBonus.getPositionX() &&
                    (uneBalle.getPositionX() + uneBalle.getDiametre()) <= (unBonus.getPositionX() + unBonus.getDiametre())) {
                if (uneBalle.getPositionY() >= unBonus.getPositionY() &&
                        (uneBalle.getPositionY() + uneBalle.getDiametre()) <= (unBonus.getPositionY() + unBonus.getDiametre())) {
                    if (unBonus.getType() == Bonus.AGRANDIRBARRE) {
                        LABARRE.setLargeur(LABARRE.getLargeur() + 20);
                        unBonus.setPositionX(3200);
                    } else if (unBonus.getType() == Bonus.PLUSIEURSBALLES) {
                        int positionX = LARGEUR / 2;
                        int positionY = LONGUEUR - 50;
                        int tailleBalle = 10;
                        ballesAAjouter.add(new Balle(positionX, positionY, (int) (Math.random() * 4 - 2), (int) (Math.random() * 4 - 2), tailleBalle));
                        unBonus.setPositionX(3200);
                    }
                }
            }
        }
    }
    public void PerteDeBalle(){
        for (Balle uneBalle: lesBalles) {
            System.out.println(uneBalle.getPositionY()+uneBalle.getDiametre());
            if((uneBalle.getPositionY()+ uneBalle.getDiametre())>= LONGUEUR){
                this.ballesARetirer.add(uneBalle);
            }
        }
        for (Balle uneBalle : ballesARetirer) {
            this.lesBalles.remove(uneBalle);
        }
        ballesARetirer.clear();
        if(lesBalles.isEmpty())
            if (GameOver()){
                System.exit(200);
            }
    }
    public boolean GameOver(){
        JFrame fenetre = new JFrame("Game Over");
        JOptionPane.showMessageDialog(fenetre,"GAME OVER");
        fenetre.requestFocus();
        return true;
    }
    public static void main(String[] args) throws InterruptedException {
        new CasseBrique();
    }

}