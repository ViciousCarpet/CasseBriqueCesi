package com.ksoudier.cassebrique.Model;

import java.awt.*;

import static com.ksoudier.cassebrique.CasseBrique.*;

public class Bonus extends Rond {
    protected int type;
    public static final int PLUSIEURSBALLES=1;
    public static final Color COULEURPLUSIEURSBALLES=Color.green;
    public static final int AGRANDIRBARRE=2;
    private static final Color COULEURAGRANDIRBARRE=Color.cyan;
    public static final int MODEIMPOSSIBLE=3;
    private static final Color COULEURMODEIMPOSSIBLE= Color.red;

    public Bonus(int diametre, int positionX, int positionY, int type) {
        super(diametre, positionX, positionY);
        this.type= type;
        switch (type){
            case PLUSIEURSBALLES -> this.couleur=COULEURPLUSIEURSBALLES;
            case AGRANDIRBARRE -> this.couleur=COULEURAGRANDIRBARRE;
            case MODEIMPOSSIBLE -> this.couleur=COULEURMODEIMPOSSIBLE;
        }
    }
    //Collision haut de la balle, bas du bonus
    public boolean collisionHautBas(Balle uneBalle){
        if(uneBalle.getPositionX()>= this.getPositionX()&&
                (uneBalle.getPositionX()+uneBalle.getDiametre())<=(this.getPositionX()+this.getDiametre())) {
            //Cas ou le haut de la balle touche le bas du bonus
            if (uneBalle.getPositionY() <= this.getPositionY() + this.getDiametre() &&
                    (uneBalle.getPositionY() + uneBalle.getDiametre() >= (this.getPositionY() + this.getDiametre()))) {
//                effectuerBonus(unBonus);
                return true;
            }
        }
        return false;
    }
    //Collision bas de la balle, haut du bonus
    public boolean collisionBasHaut(Balle uneBalle){
        if (uneBalle.getPositionX() >= this.getPositionX() &&
                (uneBalle.getPositionX() + uneBalle.getDiametre()) <= (this.getPositionX() + this.getDiametre())) {
            if (uneBalle.getPositionY() >= this.getPositionY() &&
                    (uneBalle.getPositionY() + uneBalle.getDiametre()) <= (this.getPositionY() + this.getDiametre())) {
                return true;
            }
        }
        return false;
    }
    //Collision Gauche de la balle, Droite du bonus
    public boolean collisionGaucheDroite(Balle uneBalle) {
        if(uneBalle.getPositionX()<=this.getPositionX()+this.getDiametre()&&
                uneBalle.getPositionX()>= this.getPositionX()){
            if(uneBalle.getPositionY()>= this.getPositionY() &&
                    (uneBalle.getPositionY()+ uneBalle.getDiametre())<=(this.getPositionY()+this.getDiametre())) {
                return true;
            }
        }
        return false;
    }
    //Collision Droite de la balle, Gauche du bonus
    public boolean collisionDroiteGauche(Balle uneBalle) {
        if((uneBalle.getPositionX()+uneBalle.getDiametre())<=this.getPositionX()&&
                (uneBalle.getPositionX()+uneBalle.getDiametre())>= this.getPositionX()){
            if(uneBalle.getPositionY()>= this.getPositionY()
                    && (uneBalle.getPositionY()+uneBalle.getDiametre()<= this.getPositionY()+ this.getDiametre())){
                return true;
            }
        }
        return false;
    }
    public void effectuerBonus(){
        if (this.getType() == Bonus.AGRANDIRBARRE) {
            LABARRE.setLargeur(LABARRE.getLargeur() + 20);
            this.setPositionX(3200);
        } else if (this.getType() == Bonus.PLUSIEURSBALLES) {
            int positionX = LARGEUR / 2;
            int positionY = LONGUEUR - 50;
            int tailleBalle = 10;
            ballesAAjouter.add(new Balle(positionX, positionY, (int) (Math.random() * 4 - 2), (int) (Math.random() * 4 - 2), tailleBalle));
            bonusARetirer.add(this);
        }
        else if(this.getType()== Bonus.MODEIMPOSSIBLE){
            FPS=1000;
            LABARRE.setLargeur(20);
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
