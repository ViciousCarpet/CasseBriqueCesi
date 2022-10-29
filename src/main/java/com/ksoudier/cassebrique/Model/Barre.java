package com.ksoudier.cassebrique.Model;

import com.ksoudier.cassebrique.CasseBrique;

import java.awt.*;

public class Barre  extends Rectangle{
    public Barre(int largeur, int positionX, int positionY, Color couleur, int hauteur) {
        super(largeur, positionX, positionY, couleur, hauteur);
    }
    public Barre(int largeur, int positionX, int positionY, int hauteur) {
        super(largeur, positionX, positionY, hauteur);
    }
    public void deplacementDroite(){
        if(positionX< (CasseBrique.LARGEUR- this.largeur)-1){
            super.positionX+=5;
        }
    }
    public void deplacementGauche(){
        if(positionX>0){
            super.positionX-=5;
        }
    }
}
