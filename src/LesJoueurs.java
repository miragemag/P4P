import java.util.ArrayList;
import javax.swing.ImageIcon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Amine
 */
public class LesJoueurs {
    private ArrayList<Joueur>lstj;
    
    public LesJoueurs(){
        this.lstj=new ArrayList<>();
    }
    public Joueur getJoueur(int i){
        return lstj.get(i);
    }
    public int getIndiceJoueur(Joueur j){
        return this.lstj.indexOf(j);
    }
    public int getNbJoueurs(){
        return this.lstj.size();
    }
    public void ajouterJoueur(Joueur j){
        this.lstj.add(j);
    }
    public Joueur rechJoueur(String p){
        for(int i=0;i<this.lstj.size();i++){
            Joueur j= this.lstj.get(i);
            if(j.getPseudo().equals(p)){
                return j;
            }
        }
        return null;
    }
    public Joueur rechercher(String pseudo) {
    for (Joueur j : lstj) {
        if (j.getPseudo().equalsIgnoreCase(pseudo)) {
            return j;
        }
    }
    return null;
}
    public LesJoueurs getJoueurs(int niv){
        LesJoueurs lj=new LesJoueurs ();
        for(int i=0;i<this.lstj.size();i++){
            if(this.lstj.get(i).getNiveau()==niv){
                lj.ajouterJoueur(this.lstj.get(i));
            }
        }
        return lj;
    }
    public void supprimeJoueur(Joueur j){
        int ind = getIndiceJoueur(j);
        if(ind != -1){
            this.lstj.remove(ind);
        }
    }
public void creationJoueursTest(LesParties lp){ // creation de 5 joueurs et des parties
 Joueur j ;
 Joueur jd= new Joueur("Darwin"); // expert
 jd.setPhoto(new ImageIcon(getClass().getResource("/Img/Darwin.png")));
 Joueur jf= new Joueur("Flower"); // débutant
 jf.setPhoto(new ImageIcon(getClass().getResource("/Img/flower.jpg")));
 Joueur jj= new Joueur("Jake"); //intermédiaire
 jj.setPhoto(new ImageIcon(getClass().getResource("/Img/Jake.png")));
 Joueur jh= new Joueur("Hollow Knight");//intermediaire
 jh.setPhoto(new ImageIcon(getClass().getResource("/Img/HollowKnight.jpg")));
 Joueur jc= new Joueur("Cuphead");
 jc.setPhoto(new ImageIcon(getClass().getResource("/Img/cuphead.png")));
 Partie p;
 jd.ajoutPartieJoue(jf, 1);jf.ajoutPartieJoue(jd, 0);
 p = new Partie(jd,jf,10,1);
 lp.ajoutePartie(p);
 jd.ajoutPartieJoue(jf, 1);jf.ajoutPartieJoue(jd, 0);
 p = new Partie(jd,jf,12,1);
 lp.ajoutePartie(p);
 jd.ajoutPartieJoue(jf, 1);jf.ajoutPartieJoue(jd, 0);
 jd.ajoutPartieJoue(jf, 1); jf.ajoutPartieJoue(jd, 0);

 jd.ajoutPartieJoue(jj, 1);jj.ajoutPartieJoue(jd, 0);
 p = new Partie(jd,jj,14,1);
 lp.ajoutePartie(p);

 jd.ajoutPartieJoue(jj, 1);jj.ajoutPartieJoue(jd, 0);
 p = new Partie(jd,jj,8,1);
 lp.ajoutePartie(p);

 jf.ajoutPartieJoue(jj, 1); jj.ajoutPartieJoue(jf, 0);
 p = new Partie(jf,jj,18,1);
 lp.ajoutePartie(p);

 jd.ajoutPartieJoue(jh, 1);jh.ajoutPartieJoue(jd, 0);
 p = new Partie(jd,jh,12,1);
 lp.ajoutePartie(p);

 jd.ajoutPartieJoue(jc, 1);jc.ajoutPartieJoue(jd, 0);
 p = new Partie(jd,jc,8,1);
 lp.ajoutePartie(p);

 jf.ajoutPartieJoue(jj, 1);jj.ajoutPartieJoue(jf,0);
 p = new Partie(jf,jj,8,1);
 lp.ajoutePartie(p);
 jj.ajoutPartieJoue(jh, 1);jh.ajoutPartieJoue(jj,0);
 p = new Partie(jj,jh,8,1);
 lp.ajoutePartie(p);
 jj.ajoutPartieJoue(jh, 1);jh.ajoutPartieJoue(jj,0);
 p = new Partie(jj,jh,8,1);
 lp.ajoutePartie(p);
 jj.ajoutPartieJoue(jh, 1);jh.ajoutPartieJoue(jj,0);
 p = new Partie(jj,jh,8,1);
 lp.ajoutePartie(p);

 this.ajouterJoueur(jd);
 this.ajouterJoueur(jc);
 this.ajouterJoueur(jf);
 this.ajouterJoueur(jj);
 this.ajouterJoueur(jh);
 }
    @Override
    public String toString(){
        String s="";
        for(int i = 0;i<this.lstj.size();i++){
            s+="\nJoueur : "+(i+1)+"\n";
            s+=this.lstj.get(i).toString();
        }
        return s;
    }
}
