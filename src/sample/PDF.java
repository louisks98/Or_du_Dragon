package sample;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LouisChristophe on 2017-03-18.
 */
public class PDF {


    final private String IP = "149.56.47.97";
    final private int PORT_JEU = 51007;
    final private int PORT_ENIGME = 51008;
    private Socket socketJeu;
    private Socket socketQuestion;
    private boolean connecter = false;
    private Main.Noeud oldNode;

    private String userName = "LesRingos";
    private String passWord = "123QWEasdzxc";
    private BufferedReader reader;

    private PrintWriter writer;
    private PrintWriter WriteReponse;
    private FonctionSQL Fsql = new FonctionSQL();
    private String[] questionSeparer;
    private int rep;

    PDF()
    {
        try
        {
            socketJeu = new Socket(IP, PORT_JEU);
            reader = new BufferedReader(new InputStreamReader(socketJeu.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socketJeu.getOutputStream()), true);
            WriteReponse = new PrintWriter(new OutputStreamWriter(socketJeu.getOutputStream()), true);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }

    public boolean isConnecter() {return connecter;}

    public class HELLOQuestion implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                System.out.println("thread pdf is running");
                socketQuestion = new Socket(IP, PORT_ENIGME);
                String line;
                BufferedReader readerQuestion = new BufferedReader(new InputStreamReader(socketQuestion.getInputStream()));
                PrintWriter writerQuestion = new PrintWriter(new OutputStreamWriter(socketQuestion.getOutputStream()), true);

                Question quest;


                if(!socketQuestion.isClosed())
                {
                    writerQuestion.println("HELLO " + userName + " " + passWord);
                    line = readerQuestion.readLine();
                    System.out.println("line read");
                    while(true)
                    {
                        //System.out.println(line);
                        if (line != null)
                        {
                            System.out.println("in if != null");
                            if(line.equals("AUB"))
                            {
                                quest =  Fsql.GetQuestionSelonImmeuble("F");
                                System.out.println(quest.getQuestionToServeur());
                                writerQuestion.println(quest.getQuestionToServeur());
                            }
                            if(line.equals("MAN"))
                            {
                                quest =  Fsql.GetQuestionSelonImmeuble("M");
                                System.out.println(quest.getQuestionToServeur());
                                writerQuestion.println(quest.getQuestionToServeur());
                            }
                            if(line.equals("CHA"))
                            {
                                quest =  Fsql.GetQuestionSelonImmeuble("D");
                                System.out.println(quest.getQuestionToServeur());
                                writerQuestion.println(quest.getQuestionToServeur());
                            }
                            System.out.println(line);
                        }
                        line = readerQuestion.readLine();
                    }
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
    }

    public void CommandeHELLOQuest()
    {
        HELLOQuestion commande = new HELLOQuestion();
        Thread thq = new Thread(commande);
        thq.setDaemon(true);
        thq.start();
    }

    public class HELLO implements Runnable
    {
        @Override
        public void run()
        {
            String line;
            try
            {
                if (socketJeu.isClosed())
                {
                    socketJeu = new Socket(IP, PORT_JEU);
                    reader = new BufferedReader(new InputStreamReader(socketJeu.getInputStream()));
                    writer = new PrintWriter(new OutputStreamWriter(socketJeu.getOutputStream()), true);
                    WriteReponse = new PrintWriter(new OutputStreamWriter(socketJeu.getOutputStream()), true);
                }

                if(!socketJeu.isClosed())
                {
                    writer.println("HELLO " + userName + " " + passWord);
                    line = reader.readLine();
                    if(line.contains("OK"))
                    {
                        Fsql.Open();
                        Fsql.Init_BD();
                        connecter = true;
                        //return line.substring(line.indexOf("O") + 2, line.length()).trim();
                    }
                    System.out.println(line);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //return null;
        }
    }

    public void CommandeHELLO()
    {
        HELLO commande = new HELLO();
        Thread th = new Thread(commande);
        th.start();
    }

    public class GOTO implements Runnable
    {
        Main.Noeud node;
        public GOTO(Main.Noeud node)
        {
            this.node = node;
        }

        @Override
        public void run()
        {
            String line;
            try
            {
                if(!socketJeu.isClosed())
                {
                    writer.println("GOTO " + node.getNum());
                    line = reader.readLine();

                    if(line.equals("OK"))
                    {
                        Main.numJoueur = node.getNum();
                    }
                    if(line.equals("D"))
                    {
                        Fsql.IncrementerDoritos();
                        Main.numJoueur = node.getNum();
                    }
                    if(line.equals("M"))
                    {
                        Fsql.IncrementMountainDew();
                        Main.numJoueur = node.getNum();
                    }
                    if(line.equals("P"))
                    {
                        Fsql.AjouterCapital(1);
                        Main.numJoueur = node.getNum();
                    }
                    if(line.equals("T"))
                    {
                        if(Fsql.GetCapital() < 5 && Fsql.GetDoritos() < 1)
                        {
                            CloseServeur();
                        }
                        else
                        {
                            Fsql.AcheterSortieDePrisonTroll();
                            Main.numJoueur = node.getNum();
                        }
                    }
                    if(line.equals("G"))
                    {
                        if(Fsql.GetCapital() < 5 && Fsql.GetMountainDew() < 1)
                        {
                            CloseServeur();
                        }
                        else
                        {
                            Fsql.AcheterSortieDePrisonGoblin();
                            Main.numJoueur = node.getNum();
                        }
                    }
                    if(line.contains("mauvaise reponse"))
                    {
                        if(node.isEstAub()) {Fsql.AcheterDroitDePassageAuberge();}
                        if (node.isEstCha()) {Fsql.AcheterDroitDePassageChateau();}
                        if (node.isEstMan()) {Fsql.AcheterDroitDePassageManoir();}
                    }

                    if(line.contains("ENIG"))
                    {
                        Main.numJoueur = node.getNum();
                        String question = line.substring(4, line.length());
                        System.out.println(line);
                        questionSeparer = question.split(":");
                        Platform.runLater(() -> afficherQuestion());
                    }
                    System.out.println(line);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void CommandeGOTO(Main.Noeud node)
    {
        GOTO commande = new GOTO(node);
        Thread t = new Thread(commande);
        t.start();
    }

    public class BULID implements Runnable
    {
        @Override
        public void run()
        {
            String line;
            try
            {
                if(!socketJeu.isClosed())
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
                            CloseServeur();
                        }
                        Fsql.AcheterChateau();
                    }

                    System.out.println(line);
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void CommandeBUILD()
    {
        BULID commande = new BULID();
        Thread tb = new Thread(commande);
        tb.start();

    }

    public class QUIT implements Runnable
    {
        @Override
        public void run()
        {
            String line;
            try
            {
                if (!socketJeu.isClosed())
                {
                    writer.println("QUIT");
                    line = reader.readLine();
                    if(line.contains("OK"))
                    {
                        Fsql.Close();
                        socketJeu.close();
                    }
                    System.out.println(line);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void CommandeQUIT()
    {
        QUIT commande = new QUIT();
        Thread tq = new Thread(commande);
        tq.start();
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
            if (!socketJeu.isClosed())
            {
                socketJeu.close();
                Fsql.Close();
                System.out.println("Socketjeu fermer");
            }
            if(!socketQuestion.isClosed())
            {
                socketQuestion.close();
                System.out.println("SocketQuestion fermer");
            }



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void afficherQuestion()
    {
        System.out.println("thread afficherquestion is running");
        List<String> choix = new ArrayList<>();
        choix.add(questionSeparer[2]);
        choix.add(questionSeparer[3]);
        choix.add(questionSeparer[4]);
        choix.add(questionSeparer[5]);

        ChoiceDialog<String> dialog = new ChoiceDialog<>(questionSeparer[2], choix);
        dialog.setTitle("Question");
        dialog.setHeaderText("Énigme posé par" + questionSeparer[0]);
        dialog.setContentText(questionSeparer[1]);
        Optional<String> result = dialog.showAndWait();
        rep = -1;
        if(result.isPresent())
        {
            rep = choix.indexOf(result.get());
        }
        System.out.println("Envoi de la reponse");
        System.out.println(rep);
        WriteReponse.println("CHOICE " + rep);
    }

}
