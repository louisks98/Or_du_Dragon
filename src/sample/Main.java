package sample;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.image.Image;
import javafx.scene.text.*;
import javafx.scene.input.MouseEvent;

public class Main extends Application {

    public class Noeud extends Circle
    {
        private int num;

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
    }

    public Vector<Circle> tbCircle = new Vector<>();

    public void Cree_Noeud(Pane g, ArrayList<String> coord, Vector<Boolean> construsible)
    {
        int x;
        int y;
        for(int i = 0; i < coord.size(); i++)
        {
            x = getCoordonneeX(coord.get(i));
            y = getCoordonneeY(coord.get(i));
            Noeud node = new Noeud(x, y, 12, construsible.get(i), i);
//            Circle c = new Circle(x, y, 10);
//            if(construsible.get(i) == true)
//            {
//                c.setFill(Color.WHITE);
//                c.setStroke(Color.BLACK);
//                c.setStrokeWidth(1);
//            }
            node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {texte.setText("X: " + node.getCenterX() + "   Y: " + node.getCenterY());});
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
        final String joueur = "J";
        final String troll = "T";
        final String gobelin = "G";
        final String mountainDew = "M";
        final String doritos = "D";
        final String auberge = "A";
        final String manoir = "N";
        final String chateau = "C";

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
                        ChangerCercle(idCercle, objet);
                    }
                    Thread.sleep(1);
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
        public void ChangerCercle(String id, String obj)
        {
            switch (obj)
            {
                case joueur : tbCircle.get(Integer.parseInt(id)).setFill(Color.AQUA);
                break;
                case troll : tbCircle.get(Integer.parseInt(id)).setFill(Color.DARKGREEN);
                break;
                case gobelin : tbCircle.get(Integer.parseInt(id)).setFill(Color.DARKSALMON);
                break;
                case  mountainDew : tbCircle.get(Integer.parseInt(id)).setFill(Color.DARKSEAGREEN);
                break;
                case doritos : tbCircle.get(Integer.parseInt(id)).setFill(Color.ORANGE);
                break;
                case auberge : tbCircle.get(Integer.parseInt(id)).setFill(Color.AQUA);
                break;
                case manoir : tbCircle.get(Integer.parseInt(id)).setFill(Color.BLANCHEDALMOND);
                break;
                case chateau : tbCircle.get(Integer.parseInt(id)).setFill(Color.CRIMSON);
                break;
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
            }
            else
            {
                tbCircle.get(i).setFill(Color.BLACK);
            }
        }

    }

    Text texte = new Text(1400, 850, "X:  Y:");
    LireServeur serveur = new LireServeur();
    @Override
    public void start(Stage primaryStage) throws Exception{

        ChangerCouleur couleur = new ChangerCouleur();
        Thread t = new Thread(couleur);

        Pane root = new Pane();
        serveur.LireNoeuds_Arcs();
        CreeLiens(root, serveur.GetCoordonnee(), serveur.GetLiaison());
        Cree_Noeud(root ,serveur.GetCoordonnee(), serveur.GetConstruisible());

        texte.setFont(new Font(20));
        root.getChildren().add(texte);
        Image image = new Image("nowhereland.png");
        BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(bg));
        primaryStage.setTitle("L'Or du Dragon");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
        t.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
