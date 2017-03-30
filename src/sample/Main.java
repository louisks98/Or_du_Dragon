package sample;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.image.Image;
import javafx.scene.text.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

public class Main extends Application {

    final String joueur = "J";
    final String troll = "T";
    final String gobelin = "G";
    final String mountainDew = "M";
    final String doritos = "D";
    final String auberge = "A";
    final String manoir = "N";
    final String chateau = "C";
    final String piece = "P";
    public static int numJoueur;
    public Vector<Noeud> tbCircle = new Vector<>();

    public class Noeud extends Circle
    {
        private int num;
        private boolean estMan = false;
        private boolean estAub = false;
        private boolean estCha = false;

        Noeud(int x, int y, int grosseur ,boolean constructible, int num)
        {
            super(x, y, grosseur);
            this.num = num;
            if (constructible)
            {
                this.setFill(Color.WHITE);
                this.setStroke(Color.BLACK);
            }
            else
            {
                this.setStroke(Color.BLACK);
            }
            this.setStrokeWidth(3);
        }

        public int getNum()
        {
            return num;
        }

        private void setEstMan(boolean b) {estMan = b;}

        private void setEstAub(boolean estAub) {
            this.estAub = estAub;
        }

        private void setEstCha(boolean estCha) {
            this.estCha = estCha;
        }

        public boolean isEstAub() {
            return estAub;
        }

        public boolean isEstCha() {
            return estCha;
        }

        public boolean isEstMan() {
            return estMan;
        }

        public void ChangerCercle(String obj)
        {
            tbCircle.get(numJoueur).setStroke(Color.BLUE);
            switch (obj)
            {
                case joueur : this.setFill(Color.RED);
                break;
                case troll : this.setFill(Color.DARKGREEN);
                break;
                case gobelin : this.setFill(Color.DARKSEAGREEN);
                break;
                case  mountainDew : this.setFill(Color.YELLOWGREEN);
                break;
                case doritos : this.setFill(Color.DARKORANGE);
                break;
                case auberge : this.setFill(Color.AQUA);
                this.setEstAub(true);
                this.setEstCha(false);
                this.setEstMan(false);
                break;
                case manoir : this.setFill(Color.PURPLE);
                    this.setEstAub(false);
                    this.setEstCha(false);
                    this.setEstMan(true);
                break;
                case chateau : this.setFill(Color.CRIMSON);
                    this.setEstAub(false);
                    this.setEstCha(true);
                    this.setEstMan(false);
                break;
                case piece : this.setFill(Color.GOLD);
                break;
            }
        }
    }

    public class Bouton extends Rectangle
    {
        public Bouton(int x1 ,int y1, int x2, int y2)
        {
            super(x1, y1, x2, y2);
            this.setStroke(Color.BLACK);
            this.setFill(Color.WHITE);
            this.setStrokeWidth(2);
        }
    }

    public void Cree_Noeud(Pane g, ArrayList<String> coord, Vector<Boolean> construsible)
    {
        int x;
        int y;
        for(int i = 0; i < coord.size(); i++)
        {
            x = getCoordonneeX(coord.get(i));
            y = getCoordonneeY(coord.get(i));
            Noeud node = new Noeud(x, y, 12, construsible.get(i), i);
            node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                texte.setText("X: " + node.getCenterX() + "   Y: " + node.getCenterY());
                Protocole.CommandeGOTO(node);
                texteCapital.setText(Protocole.getFond() + " Pieces");
                texteDoritos.setText(Protocole.getDoritos() + " Doritos");
                textemountainDew.setText(Protocole.getMountainDew() + " MountainDew");
            });
            tbCircle.add(node);
            g.getChildren().add(node);
        }
    }

    public void CreeLiens(Pane g, ArrayList<String> coord, Vector<String> Liaison)
    {
        String line;
        String node;
        String otherNodes;
        int x1;
        int x2;
        int y1;
        int y2;

        for(int i = 0; i < Liaison.size(); i++)
        {
            int pos;
            boolean terminer = false;
            line = Liaison.get(i);
            node = line.substring(0, line.indexOf(" ")).trim();
            x1 = getCoordonneeX(coord.get(Integer.parseInt(node)));
            y1 = getCoordonneeY(coord.get(Integer.parseInt(node)));
            otherNodes = line.substring(line.indexOf(" ") + 1, line.length());
            do
            {
                pos = otherNodes.indexOf(" ");
                if (pos != -1)
                {
                    node = otherNodes.substring(0, pos).trim();
                    x2 = getCoordonneeX(coord.get(Integer.parseInt(node)));
                    y2 = getCoordonneeY(coord.get(Integer.parseInt(node)));
                    otherNodes = otherNodes.substring(otherNodes.indexOf(" ") + 1, otherNodes.length());
                }
                else
                {
                    node = otherNodes;
                    x2 = getCoordonneeX(coord.get(Integer.parseInt(node)));
                    y2 = getCoordonneeY(coord.get(Integer.parseInt(node)));
                    terminer = true;
                }
                Line ln = new Line(x1, y1, x2, y2);
                ln.setStrokeWidth(3);
                g.getChildren().add(ln);

            }while(!terminer);
        }
    }

    public int getCoordonneeX(String s)
    {
        String sX;
        int x;
        int pos;

        pos = s.indexOf(" ");
        sX = s.substring(0, pos);
        sX = sX.trim();
        x = Integer.parseInt(sX);
        return x;
    }

    public int getCoordonneeY(String s)
    {
        String sY;
        int y;
        int pos;

        pos = s.indexOf(" ");
        sY = s.substring(pos + 1, s.length());
        sY = sY.trim();
        y  = Integer.parseInt(sY);
        return y;
    }

    class ChangerCouleur implements Runnable
    {
        @Override
        public void run()
        {
            String[] packet;
            String idCercle;
            String objet;
            String[] info;
            try
            {
                while(true)
                {
                    serveur.LirePosition();

                    info = serveur.GetPosition();
                    ResetColor();
                    for(int i = 0; i < info.length; i++)
                    {
                        packet = info[i].split(":");
                        idCercle = packet[0];
                        objet = packet[1];
                        tbCircle.get(Integer.parseInt(idCercle)).ChangerCercle(objet);
                    }
                    Thread.sleep(1);
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }
    public void ResetColor()
    {
        for (int i = 0; i < tbCircle.size(); i++)
        {
            if(serveur.GetConstruisible().get(i))
            {
                tbCircle.get(i).setFill(Color.WHITE);
                tbCircle.get(i).setStroke(Color.BLACK);
            }
            else
            {
                tbCircle.get(i).setFill(Color.BLACK);
                tbCircle.get(i).setStroke(Color.BLACK);
            }
        }

    }

    Text texte = new Text(1400, 850, "X:  Y:");
    PDF Protocole = new PDF();
    Text texteCapital = new Text(250,40,"0 Pieces");
    Text texteDoritos = new Text(250, 60, "0 Doritos");
    Text textemountainDew = new Text(250, 80, "0 MountainDew");
    LireServeur serveur = new LireServeur();
    @Override
    public void start(Stage primaryStage) throws Exception{

        ChangerCouleur couleur = new ChangerCouleur();
        Thread t = new Thread(couleur);

        // initialisation des bouton
        Bouton btnConnexion = new Bouton(10, 10, 100, 50);
        Bouton btnQuit = new Bouton(120, 10, 100, 50);
        Bouton btnBuild = new Bouton(10, 70, 100, 50);

        // Ajout des mouse event sur les boutons
        btnConnexion.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Protocole.CommandeHELLO();
            Protocole.CommandeHELLOQuest();
        });
        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Protocole.CommandeQUIT();
        });
        btnBuild.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {Protocole.CommandeBUILD();});

        Text texteBtnConnexion = new Text(20 , 40, "Connexion");
        Text texteBtnQuit = new Text(140, 40, "Quitter");
        Text texteBtnBuild = new Text(20, 100,"Construire");

        Pane root = new Pane();
        serveur.LireNoeuds_Arcs();
        CreeLiens(root, serveur.GetCoordonnee(), serveur.GetLiaison());
        Cree_Noeud(root ,serveur.GetCoordonnee(), serveur.GetConstruisible());

        texte.setFont(new Font(20));
        texteCapital.setFont(new Font(20));
        texteDoritos.setFont(new Font(20));
        textemountainDew.setFont(new Font(20));

        //root.getChildren().add(texte);
        Image image = new Image("nowhereland.png");
        BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(bg));
        root.getChildren().addAll(btnConnexion, texteBtnConnexion, btnQuit, texteBtnQuit, btnBuild, texteBtnBuild, texteCapital, texteDoritos, textemountainDew, texte);
        primaryStage.setTitle("L'Or du Dragon");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
        t.setDaemon(true);
        t.start();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(Protocole.isConnecter())
                {
                    serveur.CloseServeur();
                    Protocole.CloseServeur();
                    System.out.println("Serveur(s) déconnecté(s)");
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
