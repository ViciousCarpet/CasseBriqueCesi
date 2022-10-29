package com.ksoudier.cassebrique.Model;

import java.awt.*;

public class Brique extends Rectangle{
    protected int vie;
    protected int type;
    public static final int BRIQUEBASIQUE=1;
    public static final int BRIQUEAMELIOREE=3;
    public static final int BRIQUEDURE=10;

    public Brique(int largeur, int positionX, int positionY, int hauteur, int vie, int type) {
        super(largeur, positionX, positionY, hauteur);
        this.vie = vie;
        this.type=type;
        switch (type){
            case BRIQUEBASIQUE -> this.couleur= Color.LIGHT_GRAY;
            case BRIQUEAMELIOREE -> this.couleur=Color.darkGray;
            case BRIQUEDURE -> this.couleur=Color.RED;
        }
    }

    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(this.couleur);
        dessin.drawRect(this.positionX,this.positionY,this.largeur,this.hauteur);
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
