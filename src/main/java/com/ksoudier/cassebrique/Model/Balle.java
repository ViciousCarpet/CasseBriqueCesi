package com.ksoudier.cassebrique.Model;

import com.ksoudier.cassebrique.CasseBrique;

import java.awt.*;

import static com.ksoudier.cassebrique.CasseBrique.LABARRE;

public class Balle extends Rond{
    protected int vitesseHorizontaleBalle;
    protected int vitesseVerticaleBalle;
    protected static final Color COULEURPARDEFAUT=Color.DARK_GRAY;

    public Balle(int positionX, int positionY, int vitesseHorizontaleBalle, int vitesseVerticaleBalle, int diametre) {
        super(diametre,positionX,positionY);

        this.vitesseHorizontaleBalle=vitesseHorizontaleBalle;
        this.vitesseVerticaleBalle=vitesseVerticaleBalle;
        if (this.vitesseHorizontaleBalle==0){
            this.vitesseHorizontaleBalle=1;
        }
        if (this.vitesseVerticaleBalle==0){
            this.vitesseVerticaleBalle=1;
        }
        this.couleur=COULEURPARDEFAUT;
    }
//
//    public Balle(int positionX, int positionY, int vitesseHorizontaleBalle, int vitesseVerticaleBalle, int diametre, Color couleurBalle) {
//        super(diametre,positionX,positionY,couleurBalle);
//        this.vitesseHorizontaleBalle = vitesseHorizontaleBalle;
//        this.vitesseVerticaleBalle = vitesseVerticaleBalle;
//        if (vitesseHorizontaleBalle==0)
//            this.vitesseVerticaleBalle=1;
//        if (vitesseVerticaleBalle==0)
//            this.vitesseVerticaleBalle=1;
//    }

    public void mouvement(){
        positionX+=vitesseHorizontaleBalle;
        positionY-=vitesseVerticaleBalle;

    }

    public void collision(){
        if(positionX>= CasseBrique.LARGEUR - (diametre/2) || positionX<=0){
            vitesseHorizontaleBalle*=-1;
//            super.couleur= (new Color((float)Math.random(),(float)Math.random(),(float)Math.random())==Color.white?Color.black:Color.BLUE);
            this.vitesseVerticaleBalle = vitesseVerticaleBalle==0?1:vitesseVerticaleBalle;
        }

        if(positionY<=0 || positionY>=CasseBrique.LONGUEUR - (diametre/2)) {
            vitesseVerticaleBalle *= -1;
//            super.couleur= new Color((float)Math.random(),(float)Math.random(),(float)Math.random());
        }
        //Si une balle arrive entre la largeur min et max a la hauteur min alors il renvoie la balle
        if((this.positionX <= LABARRE.getPositionX()+LABARRE.largeur)&&(this.positionX)>=LABARRE.positionX &&
                (this.positionY>= LABARRE.positionY-(this.diametre/2))){
            this.vitesseVerticaleBalle*=-1;
        }
    }



    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getVitesseHorizontaleBalle() {
        return vitesseHorizontaleBalle;
    }

    public void setVitesseHorizontaleBalle(int vitesseHorizontaleBalle) {
        this.vitesseHorizontaleBalle = vitesseHorizontaleBalle;
    }

    public int getVitesseVerticaleBalle() {
        return vitesseVerticaleBalle;
    }

    public void setVitesseVerticaleBalle(int vitesseVerticaleBalle) {
        this.vitesseVerticaleBalle = vitesseVerticaleBalle;
    }
}
