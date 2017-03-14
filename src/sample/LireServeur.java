package sample;

import java.io.*;
import java.net.InetAddress;
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

    final String joueur = "J";
    final String troll = "T";
    final String gobelin = "G";
    final String montainDew = "M";
    final String doritos = "D";
    final String auberge = "A";
    final String manoir = "N";
    final String chateau = "C";

    private ArrayList<String> Coordonnee = new ArrayList<>();
    private Vector<Boolean> Construisible = new Vector<>();
    private Vector<String> Liaison = new Vector<>();
    private Vector<String[]> Position = new Vector<>();

    public ArrayList<String> GetCoordonnee()
    {
        return Coordonnee;
    }

    public Vector<Boolean> GetConstruisible()
    {
        return Construisible;
    }

    public Vector<String> GetLiaison()
    {
        return Liaison;
    }

    public Vector<String[]> GetPosition() {return Position;}


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

                    if(newline.charAt(newline.length() - 1) == '1')
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

    public void LirePosition()
    {
        BufferedReader serveurReader;
        BufferedReader reader;
        PrintWriter serveurWriter;

        /*if (args.length > 0)
        {
            for (int i = 0;i < args.length; i++)
            {
                VerifierParametre(args[i],joueur,troll,gobelin,montainDew,doritos,auberge,manoir,chateau);
            }
        }*/
        try
        {
            //InetAddress addr;
            Socket socketPosition = new Socket(IP, PORT_POSITION);
            //addr = socketPosition.getInetAddress();
            serveurReader = new BufferedReader(new InputStreamReader(socketPosition.getInputStream()));
            serveurWriter = new PrintWriter(new OutputStreamWriter(socketPosition.getOutputStream()));
            String ligne;
            String Packet[];
            /*Terminator leTerminator = new Terminator();
            Thread t = new Thread(leTerminator);
            t.start();*/

                ligne = serveurReader.readLine();
                serveurWriter.println("");
                serveurWriter.flush();
                //boolean estTrouver = false;
                if (ligne != null)
                {
                    Packet = ligne.split(" ");
                    Position.add(Packet);
                }

        }
        catch (java.io.IOException e)
        {
            System.out.println("Impossible de se connecter au serveur");
        }
    }

    public static void VerifierParametre(String param, String j, String t, String g, String m, String d, String a, String n, String c) {
        if (!param.equals(j) &&
                !param.equals(t) &&
                !param.equals(g) &&
                !param.equals(m) &&
                !param.equals(d) &&
                !param.equals(a) &&
                !param.equals(n) &&
                !param.equals(c)) {
            System.err.println("Un ou plusieurs parametres sont invalides.");
            System.exit(1);
        }
    }
}
