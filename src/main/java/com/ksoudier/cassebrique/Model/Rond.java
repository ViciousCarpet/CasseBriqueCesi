package com.ksoudier.cassebrique.Model;

import java.awt.*;

public class Rond extends Sprite{
    protected int diametre;
    protected static final Color couleurParDefaut=Color.black;
    public Rond(int diametre, int positionX, int positionY, Color couleur) {
        super.positionX = positionX;
        super.positionY = positionY;
        super.couleur = couleur;
        this.diametre = diametre;
    }
    public Rond(int diametre, int positionX, int positionY) {
        super.positionX = positionX;
        super.positionY = positionY;
        super.couleur = couleurParDefaut;
        this.diametre = diametre;
    }
    public void dessiner(Graphics2D dessin){
        dessin.setColor(couleur);
        dessin.fillOval(positionX,positionY,diametre,diametre);
    }

    public int getDiametre() {
        return diametre;
    }

    public void setDiametre(int diametre) {
        this.diametre = diametre;
    }
}
