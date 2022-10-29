package com.ksoudier.cassebrique.Model;

import java.awt.*;

public class Rectangle extends Carre{
    protected int hauteur;
    public Rectangle(int largeur, int positionX, int positionY, int hauteur) {
        super(largeur, positionX, positionY);
        this.hauteur = hauteur;
    }
    public Rectangle(int largeur, int positionX, int positionY, Color couleur, int hauteur) {
        super(largeur, positionX, positionY, couleur);
        this.hauteur = hauteur;
    }

    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(Sprite.couleurParDefaut);
        dessin.fillRect(super.positionX,super.positionY,this.largeur,this.hauteur);
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
}
