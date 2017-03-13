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

    public void Cree_Noeud(Pane g, ArrayList<String> coord, Vector<Boolean> construsible)
    {
        int x;
        int y;
        for(int i = 0; i < coord.size(); i++)
        {
            x = getCoordonneeX(coord.get(i));
            y = getCoordonneeY(coord.get(i));
            Circle c = new Circle(x, y, 10);
            if(construsible.get(i) == true)
            {
                c.setFill(Color.WHITE);
                c.setStroke(Color.BLACK);
                c.setStrokeWidth(1);
            }
            c.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {texte.setText("X: " + c.getCenterX() + "   Y: " + c.getCenterY());});

            g.getChildren().add(c);
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

        }
    }

    Text texte = new Text(1400, 850, "X:  Y:");
    @Override
    public void start(Stage primaryStage) throws Exception{

        LireServeur serveur = new LireServeur();
        Pane root = new Pane();
        serveur.LireNoeuds_Arcs();
        CreeLiens(root, serveur.GetCoordonnee(), serveur.GetLiaison());
        Cree_Noeud(root ,serveur.GetCoordonnee(), serveur.GetConstruisible());
        serveur.LirePosition();

        texte.setFont(new Font(20));
        root.getChildren().add(texte);
        Image image = new Image("file:nowhereland.png");
        BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(bg));
        primaryStage.setTitle("L'Or du Dragon");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
