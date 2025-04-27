/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Amine
 */
public class Plateau {
    private Case tab[][];
    
    public Plateau(){
        this.tab = new Case [4][4];
        initPlateau();
    }
    private void initPlateau(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                this.tab[i][j]= new Case();
            }
        }
    }
    public boolean caseValide(int x,int y){
        return x>=0 && x<=3 && y>=0 && y<=3;
    }
    public Case getCase(int x, int y){
        if(caseValide(x,y)){
            return tab[x][y];
        }else return null;
    }
    public int getSommetCase(int x, int y){
        if(caseValide(x,y)){
            return getCase(x,y).getSommetCase();
        }else return -1;
    }
    public void deposePionCase(int val, int x, int y){
        if (caseValide(x,y)){
            this.tab[x][y].empilePion(val);
        }
    }
    public void initPlateauJeu(){
        this.tab[0][0].empilePion(0); // Coin supérieur gauche
        this.tab[0][0].empilePion(0);
        this.tab[0][3].empilePion(0); // Coin supérieur droit
        this.tab[0][3].empilePion(0);
        this.tab[3][0].empilePion(0); // Coin inférieur gauche
        this.tab[3][0].empilePion(0);
        this.tab[3][3].empilePion(0); // Coin inférieur droit
        this.tab[3][3].empilePion(0);
    }
    public void afficheValPlateau(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int sommet = getSommetCase(i, j);
                if (sommet == -1) {
                    System.out.print("Vide "); // Affiche "Vide" pour les cases vides
                } else {
                    System.out.print(sommet + " "); // Affiche la valeur du sommet (ici 0 pour les pions neutres)
                }
            }
            System.out.println();
            
        }
    }
}
