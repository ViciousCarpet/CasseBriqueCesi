package com.ksoudier.cassebrique.Model;

import java.awt.*;

public class Bonus extends Rond {
    protected int type;
    public static final int PLUSIEURSBALLES=1;
    public static final Color COULEURPLUSIEURSBALLES=Color.green;
    public static final int AGRANDIRBARRE=2;
    private static final Color COULEURAGRANDIRBARRE=Color.cyan;

    public Bonus(int diametre, int positionX, int positionY, int type) {
        super(diametre, positionX, positionY);
        this.type= type;
        switch (type){
            case PLUSIEURSBALLES -> this.couleur=COULEURPLUSIEURSBALLES;
            case AGRANDIRBARRE -> this.couleur=COULEURAGRANDIRBARRE;
        }
    }

    @Override
    public void dessiner(Graphics2D dessin) {
        super.dessiner(dessin);
        dessin.setColor(couleur);
        dessin.fillOval(positionX,positionY,(diametre/2),(diametre/2));
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
