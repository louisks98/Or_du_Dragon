package sample;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by LouisChristophe on 2017-03-18.
 */
public class PDF implements  Runnable{


    final private String IP = "149.56.47.97";
    final private int PORT_JEU = 51007;
    final private int PORT_ENIGME = 51008;
    private Socket socketJeu;
    Socket socketQuestion;
    boolean connecter = false;

    private String userName = "LesRingos";
    private String passWord = "123QWEasdzxc";
    private BufferedReader reader;
    private PrintWriter writer;
    private FonctionSQL Fsql = new FonctionSQL();


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

    @Override
    public void run()
    {
        try
        {
            String line;
            socketQuestion = new Socket(IP, PORT_ENIGME);
           BufferedReader readerQuestion = new BufferedReader(new InputStreamReader(socketQuestion.getInputStream()));
           PrintWriter writerQuestion = new PrintWriter(new OutputStreamWriter(socketQuestion.getOutputStream()), true);
           Question quest;

            writerQuestion.println("HELLO " + userName + " " + passWord);
            line = readerQuestion.readLine();
            if(line.equals("AUB"))
            {
               quest =  Fsql.GetQuestionSelonImmeuble("F");
               writerQuestion.println(quest.getQuestionToServeur());
            }
            if(line.equals("MAN"))
            {
                quest =  Fsql.GetQuestionSelonImmeuble("M");
                writerQuestion.println(quest.getQuestionToServeur());
            }
            if(line.equals("CHA"))
            {
                quest =  Fsql.GetQuestionSelonImmeuble("D");
                writerQuestion.println(quest.getQuestionToServeur());
            }

        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    public void CommandeHELLO()
    {
        String line;

        try
        {
            if (socketJeu.isClosed())
            {
                socketJeu = new Socket(IP, PORT_JEU);
            }
            writer.println("HELLO " + userName + " " + passWord);
            line = reader.readLine();
            if(line.contains("OK"))
            {
                Fsql.Open();
                Fsql.Init_BD();
                connecter = true;
            }
            System.out.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void CommandeGOTO(Main.Noeud node)
    {
        String line;
        try
        {
            writer.println("GOTO " + node.getNum());
            line = reader.readLine();

                if(line.equals("D"))
                {
                    Fsql.IncrementerDoritos();
                }
                if(line.equals("M"))
                {
                    Fsql.IncrementMountainDew();
                }
                if(line.equals("P"))
                {
                    Fsql.AjouterCapital(1);
                }

            System.out.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //return line;
    }

    public void CommandeBUILD()
    {
        //todo
        String line;
        try
        {
            writer.println("Build");
            line = reader.readLine();
            if (line.equals("AUB"))
            {
                Fsql.AcheterAuberge();
            }
            if(line.equals("MAN"))
            {
                Fsql.AcheterManoir();
            }
            if(line.equals("CHA"))
            {
                if(Fsql.GetAuberge() >= 1 && Fsql.GetManoir() >= 1)
                {
                    this.CloseServeur();
                }
                Fsql.AcheterChateau();
            }

            System.out.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void CommandeQUIT()
    {
        String line;
        try
        {
            writer.println("QUIT");
            line = reader.readLine();
            if(line.contains("OK")) {Fsql.Close();}
            System.out.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public int getFond()
    {
        if (connecter)
        {
            return Fsql.GetCapital();
        }
        return 0;
    }
    public int getDoritos()
    {
        if(connecter)
        {
            return Fsql.GetDoritos();
        }
        return 0;
    }
    public int getMountainDew()
    {
        if(connecter)
        {
            return Fsql.GetMountainDew();
        }
        return 0;
    }

    public void CloseServeur()
    {
        try
        {
            socketJeu.close();
            socketQuestion.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
