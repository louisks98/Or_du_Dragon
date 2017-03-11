package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by LouisChristophe on 2017-03-11.
 */
public class LireServeur {

    final private String IP = "149.56.47.97";
    final private int  PORT_CARTE = 51005;
    final private int  PORT_POSITION = 51006;

    private ArrayList<String> Coordonnee = new ArrayList<>();
    private Vector<Boolean> Construisible = new Vector<>();
    private Vector<String> Liaison = new Vector<>();

    public void LireNoeuds_Arcs()
    {
        try
        {
            Socket soc_Carte = new Socket(IP, PORT_CARTE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(soc_Carte.getInputStream()));
            //BufferedWriter writer = new BufferedWriter(new FileWriter("Contenu_Serveur"));
            String line;
            String newline;
            int Compteur = 0;


            do
            {
                line = reader.readLine();
                if (!line.equals(""))
                {
                    int pos = line.indexOf(Integer.toString(Compteur));
                    newline = line.substring(pos + Integer.toString(Compteur).length() + 1, line.length());

                    if(newline.length() - 1 == '1')
                    {
                        Construisible.add(true);
                    }
                    else
                    {
                        Construisible.add(false);
                    }
                    String coor = newline.substring(0, newline.length() - 2);
                    Coordonnee.add(coor);
                    Compteur++;
                }
            }while(!line.equals(""));

            do
            {
              line = reader.readLine();
              if(line != null)
              {
                Liaison.add(line);
              }

            }while(line != null);

            soc_Carte.close();

        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }

    }









}