import java.awt.*;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

/**
 *
 * @author Amine
 */
public class ScoresDlg extends javax.swing.JDialog {
    private LesJoueurs lj;
    private LesParties lp;
    private PanneauImage panImage;
    /**
     * Creates new form ScoresDlg
     */
    public ScoresDlg(java.awt.Frame parent, boolean modal, LesJoueurs lj, LesParties lp){
        super(parent, modal);
        initComponents();
        this.lj = lj;
        this.lp = lp;
        Joueur jd= new Joueur(); // expert
        jd.setPhoto(new ImageIcon(getClass().getResource("/Img/joueurDefaut.png"))); // on récupère l'image par défaut(joueurDefaut.png)
        BProfil.setIcon(jd.getPhoto());
        initNomJoueurs(); // pour remplir JList des pseudos
    }
    private void initNomJoueurs(){ // rempli la JList(NomJoueurs) avec les pseudos de tout les joueurs
        DefaultListModel ljs = new DefaultListModel(); // usage d'un modèle pour remplir la JList
        ListeJoueurs.setModel(ljs);
        for(int i=0; i<this.lj.getNbJoueurs(); i++){
            ljs.addElement(this.lj.getJoueur(i).getPseudo());
        }
    }
    
    @Override
    public void paint (Graphics g) {
        super.paint(g);
    // Ne rien dessiner tant qu'aucun joueur n'est sélectionné
        if (ListeJoueurs.getSelectedIndex() != -1) {
            dessineResPartJoueur(ListeJoueurs.getSelectedIndex());
        }else {
        // Sinon, on peut soit ne rien faire, soit afficher un message ou une image par défaut
        ImageIcon icon = new ImageIcon(getClass().getResource("/Img/joueurDefaut.png"));
        Image img = icon.getImage();
        panImage.setImage(img);
        PpJ.revalidate();  // mise à jour du layout
        PpJ.repaint();     // redessine le panel

        // panImage est déjà utilisée pour ça
        }
    }
        
    public void DessinHisto(){
        Graphics g = PanHisto.getGraphics(); // Graphics contient les méthodes de dessin et PanGraph JPanel de dessin de l'histo
        g.clearRect(0, 0, PanHisto.getWidth(), PanHisto.getHeight()); // efface la zone de dessin
        // Initialisation des compteurs
        int nbPGagnées = 0; // compte le nombre de parties gagnées par le joueur 1
        int nbPPerdu = 0; // compte le nombre de parties perdu
        int nbPNulles = 0; // compte le nombre de parties nulles
        for(int i=0; i<lp.getNbPartie(); i++){ // boucle pour remplir les compteurs à partir de toutes les parties enregistrées
            int res = lp.getPartie(i).getRes(); // récupère le résultat de la partie : 1 = gagné, 2 = perdu, 0 = match nul
            switch(res){
                case 1 : nbPGagnées++;break;
                case 2 : nbPPerdu++;break;
                case 0 : nbPNulles++;break;
            }
        }
        int[] valeurs = {nbPGagnées,nbPNulles,nbPPerdu}; // tableau des valeaurs à afficher
        String[] labels = {"GAGNEE", "NUL", "PERDU",}; // message(legende) pour chaque barre
        Color[] couleurs = {Color.GREEN, Color.RED, Color.ORANGE}; // couleur des barres
        // Calculs pour le bonne affichage de l'histogramme
        int largRect = PanHisto.getWidth() / 4; // largeur d'une barre (un quart du Panel(PanGraph))
        int x = 50; // pour le décalage horizontal de départ
        int hauteurMax = PanHisto.getHeight() - 100; // hauteur maximale utilisable pour les barres
        int total = lp.getNbPartie(); // nombre total de parties 
        for(int i=0; i<3; i++){ // Dessin des 3 barres
            int h = 10;
            if(total > 0){
                h = (valeurs[i] * hauteurMax) / total;  // calcul de la hauteur proportionnelle de la barre
            }
            else{
                h = 10; // si aucune partie jouée, hauteur par défaut
            }
            if(valeurs[i] == 0){
                h = 10; // si aucune partie jouée, hauteur par défaut
            }
            g.setColor(couleurs[i]); // choix de la couleur pour chaque barre
            g.fillRect(x + i * largRect, PanHisto.getHeight() - h - 50, largRect - 20, h); // dessine le rectangle(barre)
            g.setColor(Color.BLACK); // texte en noir
            g.drawString(labels[i] + " : " + valeurs[i], x + i * largRect, PanHisto.getHeight() - 30); // légende sous la barre
        }
    }
    private void dessineResPartJoueur(int indice){ // qui trace l'histogramme des résultats de toutes les parties d'un joueur
        Graphics g = PanHisto.getGraphics(); // Graphics contient les méthodes de dessin et PanGraph JPanel de dessin de l'histo
        g.clearRect(0, 0, PanHisto.getWidth(), PanHisto.getHeight()); // efface la zone de dessin
        Joueur j = lj.getJoueur(indice); // récupère le joueur correspondant à l'index sélectionné
        int gagnées = 0; 
        int perdues = 0; 
        int nulles = 0;
        for(int i=0; i<lp.getNbPartie(); i++){ // parcours toutes les parties pour calculer les statistiques du joueur
            Partie p = lp.getPartie(i); // récupère la partie à l'indice i
            int res = p.getRes(); // résultat de la partie
            if(j.equals(p.getJ1())){ // si le joueur 1 est dans cette partie
                switch(res){
                case 1 : gagnées++;break;
                case 2 : perdues++;break;
                case 0 : nulles++;break;
                }
            } 
        }
        int total = gagnées + perdues + nulles; // nombre total de parties jouées
        if(total != 0){
            int largRect = PanHisto.getWidth() / 4; // largeur d'une barre (un quart du Panel(PanGraph))
            int x = 50; // pour le décalage horizontal de départ
            int hauteurMax = PanHisto.getHeight() - 100; // hauteur maximale utilisable pour les barres
            int[] valeurs = {gagnées, nulles, perdues}; // tableau des valeaurs à afficher
            String[] labels = {"Gagnées", "Nulles", "Perdues"}; // message(legende) pour chaque barre
            Color[] couleurs = {Color.GREEN, Color.ORANGE, Color.RED}; // couleur des barres
            for(int i = 0; i < 3; i++){ // dessin des 3 barres
                int h = 10;
                if(valeurs[i] == 0){ // si pas de victoire ou perte ou nul
                    h = 10; // hauteur par défaut de 10 pixels
                }  
                else{
                    h = (valeurs[i] * hauteurMax / total); // calcul de la hauteur proportionnelle de la barre
                }
                g.setColor(couleurs[i]); // choix de la couleur pour chaque barre
                g.fillRect(x + i * largRect, PanHisto.getHeight() - h - 50, largRect - 20, h); // dessine le rectangle(barre)
                g.setColor(Color.BLACK); // texte en noir
                g.drawString(labels[i] + " : " + valeurs[i], x + i * largRect, PanHisto.getHeight() - 30); // légende sous la barre
            }
        }
    }
    
    private void dessinePourcentageVictoire(int index){ // qui trace le pourcentage de victoire d'un joueur par rapport à ses parties jouées 
        Graphics g = PanHisto.getGraphics(); // Graphics contient les méthodes de dessin et PanGraph JPanel de dessin de l'histo
        g.clearRect(0, 0, PanHisto.getWidth(), PanHisto.getHeight()); // efface la zone de dessin
        Joueur j = lj.getJoueur(index); // récupère le joueur sélectionné
        int gagnées = 0; // nombre de victoires
        int total = 0; // nombre total de parties jouées par ce joueur
        for(int i=0; i<lp.getNbPartie(); i++) { // on parcourt toutes les parties enregistrées du joueur
            Partie p = lp.getPartie(i); // récupère la partie d'indice i
            int res = p.getRes(); // récupère le résultat de cette partie
            if(j.equals(p.getJ1())){ // si le joueur est le joueur 1
                total++; // il a joué cette partie donc on augmente son nombres de parties jouées
                if(res == 1){ // et s'il a gagné on augmente son nombre de victoire
                    gagnées++; 
                }
            }
        }
        // initialisation des variables pour le dessin
        int pourcentage = 0;
        int hauteur = 0;
        if(total != 0){
            pourcentage = (int) ((gagnées * 100.0) / total); // calcul du pourcentage
            int hauteurMax = PanHisto.getHeight() - 100; // hauteur maximale utilisable pour la barre
            hauteur = (pourcentage * hauteurMax) / 100; // calcul de la hauteur proportionnelle de la barre
            if(pourcentage == 0){ // si aucune victoire
                hauteur = 10; // petite barre visible de 10 pixels
            }
            int largRect = PanHisto.getWidth() / 3; // largeur de la barre (un tiers du Panel(PanGraph))
            int x = (PanHisto.getWidth() - largRect) / 2; // pour le centrage horizontal
            g.setColor(Color.CYAN); // on fixe la couleur de la barre par du cyan
            g.fillRect(x, PanHisto.getHeight() - hauteur - 50, largRect, hauteur); // dessine le rectangle(barre)
            g.setColor(Color.BLACK); // texte en noir
            g.drawString("Victoire: " + pourcentage + "%", x + 10, PanHisto.getHeight() - 30); // affiche le pourcentage(légende)
        }
    }
    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JTitre = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListeJoueurs = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        PpJ = new javax.swing.JPanel();
        BProfil = new javax.swing.JButton();
        PanHisto = new javax.swing.JPanel();
        JSouth = new javax.swing.JPanel();
        jValider = new javax.swing.JButton();
        MessageErreur = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Statistiques des Scores");
        JTitre.add(jLabel1);

        getContentPane().add(JTitre, java.awt.BorderLayout.NORTH);

        ListeJoueurs.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ListeJoueurs.setPreferredSize(new java.awt.Dimension(200, 90));
        jScrollPane1.setViewportView(ListeJoueurs);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.WEST);

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        PpJ.setLayout(new java.awt.GridLayout());
        PpJ.add(BProfil);

        jPanel1.add(PpJ);

        javax.swing.GroupLayout PanHistoLayout = new javax.swing.GroupLayout(PanHisto);
        PanHisto.setLayout(PanHistoLayout);
        PanHistoLayout.setHorizontalGroup(
            PanHistoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        PanHistoLayout.setVerticalGroup(
            PanHistoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        jPanel1.add(PanHisto);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jValider.setText("Valider");
        JSouth.add(jValider);
        JSouth.add(MessageErreur);

        getContentPane().add(JSouth, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ScoresDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScoresDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScoresDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScoresDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        LesJoueurs lj = new LesJoueurs();
        LesParties lp = new LesParties();
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ScoresDlg dialog = new ScoresDlg(new javax.swing.JFrame(), true, lj, lp);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BProfil;
    private javax.swing.JPanel JSouth;
    private javax.swing.JPanel JTitre;
    private javax.swing.JList<String> ListeJoueurs;
    private javax.swing.JLabel MessageErreur;
    private javax.swing.JPanel PanHisto;
    private javax.swing.JPanel PpJ;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jValider;
    // End of variables declaration//GEN-END:variables
}