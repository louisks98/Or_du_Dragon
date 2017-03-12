package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class Main extends Application {

    public void Cree_Noeud(Group g, ArrayList<String> coord, Vector<Boolean> construsible)
    {
        int x;
        int y;
        for(int i = 0; i < coord.size(); i++)
        {
            x = getCoordonneeX(coord.get(i));
            y = getCoordonneeY(coord.get(i));
            Circle c = new Circle(x, y, 10);
            if(construsible.get(i) == true) // Demander prof pour couleur
            {
                c.setFill(Color.WHITE);
                c.setStroke(Color.BLACK);
                c.setStrokeWidth(1);
            }
            g.getChildren().add(c);
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


    @Override
    public void start(Stage primaryStage) throws Exception{
        LireServeur serveur = new LireServeur();
        Group root = new Group();
        serveur.LireNoeuds_Arcs();
        Cree_Noeud(root ,serveur.GetCoordonnee(), serveur.GetConstruisible());

        primaryStage.setTitle("L'Or du Dragon");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
