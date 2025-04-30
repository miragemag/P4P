/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Amine
 */
public class Jeu {
    private Plateau platJeu;
    
    public Jeu(){
        this.platJeu = new Plateau();
        this.platJeu.initPlateauJeu();
        this.platJeu.afficheValPlateau();
    }
    public Plateau getPlateau(){
        return this.platJeu;
    }
    public boolean gagne(int valj){
        int compt;
        boolean gain = false;
        int i=0;
        while(!gain && i<4){
            compt=0;
            for(int j=0;j<4;j++){
                if(this.platJeu.getCase(i, j).getSommetCase()== valj){
                    compt ++;
                }
            }
            if(compt==4){
                gain = true;
            }else{ i = i+1;}    
        }
        int col=0;
        while(!gain && col<4){
            compt=0;
            for(int lig=0; lig<4; lig++){
                if(this.platJeu.getCase(lig, col).getSommetCase()== valj){
                    compt++;
                }
            }
            if(compt == 4){
                gain = true;
            }else{ col ++;}
        }
        if(!gain){
            compt = 0;
            for(int x =0;x<4;x++){
                if(this.platJeu.getCase(x, x).getSommetCase()==valj){
                    compt++;
                }
            }
            if(compt == 4){
                gain=true;
            }
        }
        if(!gain){
            compt = 0;
            for(int y =0;y<4;y++){
                if(this.platJeu.getCase(y, 3-y).getSommetCase()==valj){
                    compt++;
                }
            }    
            if(compt == 4){
                gain=true;
            }
        }
    return gain;
    }
    
    public boolean adjacent(int xd,int yd,int xa,int ya){
        return (xa==xd-1 && ya==yd)||(xa==xd+1 && ya==yd)||(xa==xd && ya==yd-1)||(xa==xd && ya==yd+1);
    }
    public int jouePion(int pxd,int pyd,int xd,int yd,int xa,int ya,int valp){
        int code = 0;
        if(pxd==xd && pyd==yd){
            System.out.print("Mouvement interdit retourner en arrière !");
            code = 1;
        }else{
            if(!adjacent(xd,yd,xa,ya)){
                System.out.print("Interdit la case n'est pas adjacente");
                code = 2;
            }else{
                this.platJeu.deposePionCase(valp, xa, ya);
            }
        } 
        return code; 
    }
}