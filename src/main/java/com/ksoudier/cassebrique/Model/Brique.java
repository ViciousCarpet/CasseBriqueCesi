package com.ksoudier.cassebrique.Model;

import java.awt.*;

import static com.ksoudier.cassebrique.CasseBrique.briquesARetirer;

public class Brique extends Rectangle{
    protected int vie;
    protected int type;
    public static final int BRIQUEBASIQUE=1;
    public static final int BRIQUEAMELIOREE=3;
    public static final int BRIQUEDURE=5;
    protected int alpha=255;

    public Brique(int largeur, int positionX, int positionY, int hauteur, int vie, int type) {
        super(largeur, positionX, positionY, hauteur);
        this.vie = vie;
        this.type=type;
        switch (type){
            case BRIQUEBASIQUE -> this.couleur= new Color(194, 255, 177, alpha);
            case BRIQUEAMELIOREE -> this.couleur=new Color(241, 255, 81, alpha);
            case BRIQUEDURE -> this.couleur=new Color(255, 37, 37, alpha);
        }
    }
    public boolean collisionHautBas(Balle uneBalle){
        //Cas de la balle qui touche le bas d'une brique
        if(uneBalle.getPositionX()>= this.getPositionX()&&
                (uneBalle.getPositionX()+uneBalle.getDiametre())<=(this.getPositionX()+ this.getLargeur())){
            //Cas ou le haut de la balle touche le bas de la brique
            if(uneBalle.getPositionY()<= this.getPositionY()+ this.getHauteur()&&
                    (uneBalle.getPositionY()+uneBalle.getDiametre()>= (this.getPositionY()+ this.getHauteur()))
                    && this.getVie()>0){
                this.setVie();
                uneBalle.setVitesseVerticaleBalle(uneBalle.getVitesseVerticaleBalle()*-1);
                return true;
            }

        }
        return false;
    }
    public boolean collisionBasHaut(Balle uneBalle) {
        if(uneBalle.getPositionX()>= this.getPositionX()&&
                (uneBalle.getPositionX()+uneBalle.getDiametre())<=(this.getPositionX()+ this.getLargeur())){
            if(uneBalle.getPositionY()<= this.getPositionY()&&
                    ((uneBalle.getPositionY()+uneBalle.getDiametre())>= (this.getPositionY()))){
                this.setVie();
                uneBalle.setVitesseVerticaleBalle(uneBalle.getVitesseVerticaleBalle()*-1);
                return true;
            }
        }
        return false;
    }
    public boolean collisionGaucheDroite(Balle uneBalle) {
        if(uneBalle.getPositionX()<=this.getPositionX()+this.getLargeur()&&
                uneBalle.getPositionX()>= this.getPositionX()){
            if(uneBalle.getPositionY()>= this.getPositionY() &&
                    (uneBalle.getPositionY()+ uneBalle.getDiametre())<=(this.getPositionY()+this.getHauteur())
                    && this.getVie()>0){
                this.setVie();
                uneBalle.setVitesseHorizontaleBalle(uneBalle.getVitesseHorizontaleBalle()*-1);
                return true;
            }
        }
        return false;
    }
    public boolean collisionDroiteGauche(Balle uneBalle) {
        if((uneBalle.getPositionX()+uneBalle.getDiametre())<=this.getPositionX()&&
                (uneBalle.getPositionX()+uneBalle.getDiametre())>= this.getPositionX()){
            if(uneBalle.getPositionY()>= this.getPositionY() && this.getVie()>0
                    && (uneBalle.getPositionY()+uneBalle.getDiametre()<= this.getPositionY()+ this.getHauteur())){
                this.setVie();
                uneBalle.setVitesseHorizontaleBalle(uneBalle.getVitesseHorizontaleBalle()*-1);
                return true;
            }
        }
        return false;
    }
    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(this.couleur);
        dessin.fillRect(this.positionX,this.positionY,this.largeur,this.hauteur);
        dessin.setColor(Color.BLACK);
        dessin.drawRect(this.positionX,this.positionY,this.largeur,this.hauteur);
    }

    public int getVie() {
        return vie;
    }

    public void setVie() {
        int transparence= (int)(this.couleur.getAlpha())-((int)(255/(this.vie+3)+1));
        if(transparence<0){
            transparence=0;
        }
        this.couleur= new Color(this.couleur.getRed(),this.couleur.getGreen(),this.couleur.getBlue(),
                transparence);
        this.vie-=1;
        if(this.getVie()==0){
            briquesARetirer.add(this);
        }
    }
    public void setColor(){
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
