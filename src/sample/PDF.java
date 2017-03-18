package sample;

import java.io.*;
import java.net.Socket;

/**
 * Created by LouisChristophe on 2017-03-18.
 */
public class PDF {

    final private String IP = "149.56.47.97";
    final private int PORT_JEU = 51007;
    private Socket socketJeu;

    private String userName = "LesRingos";
    private String passWord = "123QWEasdzxc";
    private BufferedReader reader;
    private PrintWriter writer;


    PDF()
    {
        try
        {
            socketJeu = new Socket(IP, PORT_JEU);
            reader = new BufferedReader(new InputStreamReader(socketJeu.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socketJeu.getOutputStream()), true);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }

    public void CommandeHELLO()
    {
        String line;

        try
        {
            writer.println("HELLO " + userName + " " + passWord);
            line = reader.readLine();
            System.out.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void CommandeGOTO(int id)
    {
        String line;
        try
        {
            writer.println("GOTO " + id);
            line = reader.readLine();
            System.out.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void CommandeBUILD()
    {
        //todo
        String line;
        try
        {
            line = reader.readLine();
            System.out.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }    }

    public void CommandeQUIT()
    {
        String line;
        try
        {
            writer.println("QUIT");
            line = reader.readLine();
            System.out.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void CloseServeur()
    {
        try
        {
            socketJeu.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
