package com.ksoudier.cassebrique.Model;

import java.awt.*;

public abstract class Sprite {
    protected int positionX;
    protected int positionY;
    public Color couleur;
    protected static final Color couleurParDefaut= Color.black;
    public void dessiner(Graphics2D dessin){

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

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }
}
