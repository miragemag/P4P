import java.awt.Color;
import java.awt.*;
import java.util.* ;
import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Amine
 */
public class P4P extends javax.swing.JFrame {

    /**
     * Creates new form P4P
     */
    private LesJoueurs lstj;
    private Joueur joueur1;
    private Joueur joueur2;
    private int JoueurCourant;
    private Jeu lejeu;
    private Plateau PJeu;
    private LesParties lp;
    private int xa,ya;
    private int xsel, ysel;
    private Case caseCourante;
    private int nbPionsDepot;
    private int nbgj1;
    private int nbgj2;
    private int xd, yd;
    private int pxd, pyd;
    private int compt;
    private boolean selection; 
    private boolean finPartie;
    private int nbgj1; 
    private int nbgj2;
    
    
    public P4P() {
        initComponents();
        this.lstj = new LesJoueurs();
        this.lstj.creationJoueursTest(this.lp);
        this.lp=new LesParties();
        this.joueur1=this.lstj.getJoueur(0);
        this.joueur2=this.lstj.getJoueur(1);
        this.lejeu= new Jeu();
        this.PJeu=lejeu.getPlateau();
        initPanneau();
        affichePanneau();
        this.PJeu.afficheValPlateau();
        this.JoueurCourant=1;
        this.nbgj1=8;
        this.nbgj2=8;
        this.compt=0;
        this.caseCourante=null;
        this.selection=false;
        this.finPartie=false;
        Message.setText("C'est au joueur 1 de jouer");
    }
    private void initPanneau() {
        for(int i=0;i<16;i++){
            JPanel  pan = new JPanel(new GridLayout(2,1));
            JButton BH =new JButton();
            BH.setName(""+i);
            BH.setBackground(new Color(227,198,109));
            BH.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
                traitementActionPerformed(evt);
            }});
            pan.add(BH);
            JPanel panelBas=new JPanel();
            panelBas.setBackground(new Color(227,198,109));
            pan.add(panelBas);
            jCenter.add(pan);   
            }
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);// centrer l'interface
    }
    private void traitementActionPerformed(java.awt.event.ActionEvent evt){
        if (!finPartie){ 
            JButton jb= (JButton) evt.getSource();
            int num= Integer.parseInt(jb.getName());
            int x= num/4;
            int y=num-x*4;
            MessageErreur.setText(""); // JLabel nommé MessageErreur à côté de la zone Message
            // pour afficher des messages d'erreurs pendant le jeu
            if (this.selection==false){ // C'est le début d'un tour de jeu
                if (PJeu.getCase(x, y).estVide())
                this.MessageErreur.setText("Case vide !");
                // Il est interdit de déposer un pion sur une case vide
            }else{ 
                this.compt++;
                selection=true; // indicateur pour indiquer qu'on est au cours d'un tour de jeu (phase d'égrainage)
                this.xsel=x; this.ysel=y; // position initiale cliquée (xsel,ysel) pour le dépôt du pion du joueur
                this.xd=x; this.yd=y; // position de dépôt du galet (ici position initiale)
                this.pxd=-1; this.pyd=-1; // position précédente du dernier dépôt (ici pas de précédent)

                this.PJeu.deposePionCase(JoueurCourant, xsel, ysel); // A COMMENTER
                // on duplique la case sélectionnée dans la case courante
                this.caseCourante= new Case();
                for (int i=0; i<this.PJeu.getCase(xsel, ysel).getNbPions(); i++)
                    this.caseCourante.empilePion(this.PJeu.getCase(xsel, ysel).getValCase(i));
                    this.nbPionsDepot=this.caseCourante.getNbPions();
                    // nombre de pions sur cette case sélectionnée (y compris le pion que l'on vient de déposer)
                    affichePanneau(); // affichage du panneau avec ce pion
                    afficheCaseCourante();
                    //affichage en partie droite de la pile des galets qui seront à déposer (pour plus de facilité pour jouer)
                    this.PJeu.getCase(xsel,ysel).videCase(); // A COMMENTER
                }
        }else{ 
            if (this.nbPionsDepot !=0){ // s'il reste des pions à poser
                this.xa=x; this.ya=y; // (xa, ya) est la position choisie pour déposer (clic sur cette case par le joueur)
                int val=this.caseCourante.getValCase(0); // récupération de la valeur du pion à déposer
                int code;
                code = this.lejeu.jouePion(pxd, pyd, xd, yd, xa, ya,val); // A COMMENTER
                if (code ==0 ){ // le pion a été déposé sur une case correcte (adjacente sans retour arrière)
                this.nbPionsDepot--; // on décrémente le nombre de pions à déposer
                this.caseCourante.defilePion(); // on supprime ce pion de la case initiale
                pxd=xd; pyd=yd; xd=xa; yd=ya; // on remet à jour les positions
                affichePanneau(); afficheCaseCourante(); // on réaffiche le jeu plateau et les galets à déposer
                    if (this.nbPionsDepot ==0){ // s'il n'y a plus de pions
                        traiteFinTour(); // on traite la fin du tour (changement de joueur ou fin de partie)
                    }else this.MessageErreur.setText("Mouvement Impossible");
                }
            }
        }
    }
    private void afficheCaseCourante(){ // case panel droite pile de galets
           if (this.caseCourante != null){ 
               jGalets.removeAll();
                //int nbPions=this.caseCourante.getNbPions();
                int nbPions=this.nbPionsDepot;
                jGalets.setLayout(new GridLayout(nbPions,1));
                for (int k=0; k<nbPions; k++){
                    JButton panPion = new JButton();
                        switch(this.caseCourante.getValCase(k)){
                            case -1 : panPion.setBackground(new Color(227,198,109));break;
                            case 0 : panPion.setBackground(Color.ORANGE);break;
                            case 1 : panPion.setBackground(Color.RED);break;
                            case 2 : panPion.setBackground(new Color(123,40,0));break;
                        }
                    jGalets.add(panPion);
                }
            }else jGalets.removeAll();
            this.pack();
            this.jGalets.validate();
            this.jGalets.repaint();
            this.setSize(800, 600);
            this.setLocation(200, 200);
 }
    public void afficheJoueurs(){
        this.PseudoJ1.setText(this.joueur1.getPseudo());
        this.PseudoJ2.setText(this.joueur2.getPseudo());
        this.PpJoueur1.setIcon(joueur1.getPhoto());
        this.PpJoueur2.setIcon(joueur2.getPhoto());
    }
    private void affichePanneau(){
        for(int i= 0;i<4;i++){
            for(int j=0; j<4; j++){
                int num = i*4+j;
                JPanel pan= (JPanel) this.jCenter.getComponent(num);
                JButton BH = (JButton) pan.getComponent(0);
                int val = this.PJeu.getSommetCase(i,j);
                if(val==-1)
                    BH.setBackground(new Color(227,198,109));
                else if(val==0){
                    BH.setBackground(Color.ORANGE);
                 }
                 else if(val==1){
                    BH.setBackground(Color.RED);
                 }
                 else if(i==2) {
                     BH.setBackground(new Color(101,52,0));
                 }
                /*switch(val){
                    case -1 : BH.setBackground(new Color(227,198,109));break;
                    case 0 : BH.setBackground(Color.ORANGE);break;
                    case 1 : BH.setBackground(Color.RED);break;
                    case 2 : BH.setBackground(new Color(101,52,0));break;
                }*/
                JPanel PB = (JPanel) pan.getComponent(1);
                PB.setBackground(new Color(227,198,109));
                PB.removeAll();
                Case c = this.PJeu.getCase(i,j);
                int nbPions = c.getNbPions();
                PB.setLayout(new GridLayout(1,nbPions));
                for(int x=0;x<nbPions-2;x++){
                    JButton btPion = new JButton();
                    int valeur = c.getValCase(x);
                    switch(valeur){
                        case 0 : btPion.setBackground(Color.ORANGE);break;
                        case 1 : btPion.setBackground(Color.RED);break;
                        case 2 : btPion.setBackground(new Color(101,52,0));break;
                    }
                    PB.add(btPion);
                }
                this.pack();
                this.setSize(800,600);
            }
        }
    }
    private void traiteFinTour(){
        this.selection=false;
        this.PJeu.afficheValPlateau(); // affichage console pour vérifier les valeurs du plateau (de type Plateau)
        if (this.lejeu.gagne(JoueurCourant)){ 
            Message.setText("joueur "+JoueurCourant+ " a gagné");
            Partie part=new Partie(this.joueur1, this.joueur2,this.compt,this.JoueurCourant);
            this.lp.ajoutePartie(part);
            this.compt=0;
            this.finPartie=true;
        }else
            if (this.JoueurCourant==1){     
                this.JoueurCourant=2;
                if (this.nbgj2 == 0){
                    Message.setText("Match nul");
                    this.finPartie=true;
                }
                else Message.setText("C'est au joueur 2 de jouer");
            }else{
                this.JoueurCourant=1;
                if (this.nbgj1 == 0){ 
                    Message.setText("Match nul");
                    this.finPartie=true;
                }
                else Message.setText("C'est au joueur 1 de jouer");
            }
        affichePanneau();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jWest = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelJoueur1 = new javax.swing.JLabel();
        PseudoJ1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        PpJoueur1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelJoueur2 = new javax.swing.JLabel();
        PseudoJ2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        PpJoueur2 = new javax.swing.JButton();
        jCenter = new javax.swing.JPanel();
        jEast = new javax.swing.JPanel();
        jLabelText = new javax.swing.JLabel();
        jGalets = new javax.swing.JButton();
        jSouth = new javax.swing.JPanel();
        jLabelMessage = new javax.swing.JLabel();
        Message = new javax.swing.JTextField();
        MessageErreur = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuJeu = new javax.swing.JMenu();
        jMenuJoueurs = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuStat = new javax.swing.JMenu();
        jMenuItemScore = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jWest.setPreferredSize(new java.awt.Dimension(150, 0));
        jWest.setLayout(new java.awt.GridLayout(4, 1));

        jPanel1.setLayout(new java.awt.GridLayout(3, 1));

        jLabelJoueur1.setText("Joueur 1 :");
        jLabelJoueur1.setToolTipText("");
        jPanel1.add(jLabelJoueur1);
        jPanel1.add(PseudoJ1);

        jPanel3.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3);

        jWest.add(jPanel1);

        PpJoueur1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/joueurDefaut.png"))); // NOI18N
        jWest.add(PpJoueur1);

        jPanel2.setLayout(new java.awt.GridLayout(3, 1));

        jLabelJoueur2.setText("Joueur 2 :");
        jPanel2.add(jLabelJoueur2);
        jPanel2.add(PseudoJ2);

        jPanel4.setBackground(new java.awt.Color(101, 52, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4);

        jWest.add(jPanel2);

        PpJoueur2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/joueurDefaut.png"))); // NOI18N
        jWest.add(PpJoueur2);

        getContentPane().add(jWest, java.awt.BorderLayout.WEST);

        jCenter.setLayout(new java.awt.GridLayout(4, 4, 5, 10));
        getContentPane().add(jCenter, java.awt.BorderLayout.CENTER);

        jEast.setPreferredSize(new java.awt.Dimension(150, 0));
        jEast.setLayout(new java.awt.GridLayout(4, 1));

        jLabelText.setText("Galets à déposer");
        jEast.add(jLabelText);
        jEast.add(jGalets);

        getContentPane().add(jEast, java.awt.BorderLayout.EAST);

        jLabelMessage.setText("Messages :");
        jSouth.add(jLabelMessage);

        Message.setPreferredSize(new java.awt.Dimension(200, 28));
        jSouth.add(Message);
        jSouth.add(MessageErreur);

        getContentPane().add(jSouth, java.awt.BorderLayout.SOUTH);

        jMenuJeu.setText("Jeu");
        jMenuBar1.add(jMenuJeu);

        jMenuJoueurs.setText("Joueurs");
        jMenuJoueurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuJoueursActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Visualiser");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuJoueurs.add(jMenuItem1);

        jMenuBar1.add(jMenuJoueurs);

        jMenuStat.setText("Statistiques");

        jMenuItemScore.setText("Scores");
        jMenuStat.add(jMenuItemScore);

        jMenuBar1.add(jMenuStat);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuJoueursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuJoueursActionPerformed

    }//GEN-LAST:event_jMenuJoueursActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        VisuJoueurDlg diag= new VisuJoueurDlg(this,true,this.lstj);
            diag.setSize(800, 600);
            diag.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(P4P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(P4P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(P4P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(P4P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new P4P().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Message;
    private javax.swing.JLabel MessageErreur;
    private javax.swing.JButton PpJoueur1;
    private javax.swing.JButton PpJoueur2;
    private javax.swing.JLabel PseudoJ1;
    private javax.swing.JLabel PseudoJ2;
    private javax.swing.JPanel jCenter;
    private javax.swing.JPanel jEast;
    private javax.swing.JButton jGalets;
    private javax.swing.JLabel jLabelJoueur1;
    private javax.swing.JLabel jLabelJoueur2;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JLabel jLabelText;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemScore;
    private javax.swing.JMenu jMenuJeu;
    private javax.swing.JMenu jMenuJoueurs;
    private javax.swing.JMenu jMenuStat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jSouth;
    private javax.swing.JPanel jWest;
    // End of variables declaration//GEN-END:variables

}
