package com.ksoudier.cassebrique.Model;

import java.awt.*;

public class Carre extends Sprite{
    protected int largeur;

    public Carre(int largeur, int positionX,int positionY, Color couleur) {
        super.positionX=positionX;
        super.positionY=positionY;
        super.couleur=couleur;
        this.largeur = largeur;
    }
    public Carre(int largeur, int positionX,int positionY) {
        super.positionX=positionX;
        super.positionY=positionY;
        this.largeur = largeur;
    }
    public void dessiner(Graphics2D dessin){
        dessin.setColor(Sprite.couleurParDefaut);
        dessin.drawRect(super.positionX,super.positionY,this.largeur,this.largeur);
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }
}
