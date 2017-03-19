package sample;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import oracle.jdbc.internal.OracleTypes;

import javax.xml.transform.Result;
import java.sql.*;

/**
 * Created by 201567153 on 2017-03-16.
 */
public class FonctionSQL
{
    private final static String URL = "jdbc:oracle:thin:@mercure.clg.qc.ca:1521:orcl";
    private final static String USER = "lesringos";
    private final static String PASS = "ORACLE1";

    private static Connection conn = null;

    public static Question GetQuestionSelonImmeuble(String difficulte)
    {
        CallableStatement CLB_PigerQuestionSelonImmeuble = null;
        ResultSet RS_Reponses = null;
        String NumQuestion = "";
        String EnonceQuestion = "";
        String Reponces[] = new String[4];
        int bonneReponce = -1;
        int i = 0;
        try
        {
            //Requete sql.
            CLB_PigerQuestionSelonImmeuble = conn.prepareCall("{call JEU.PigerQuestionSelonImmeuble(?,?,?,?)}");
            CLB_PigerQuestionSelonImmeuble.setString(1, difficulte);
            CLB_PigerQuestionSelonImmeuble.registerOutParameter(2, OracleTypes.VARCHAR);
            CLB_PigerQuestionSelonImmeuble.registerOutParameter(3, OracleTypes.VARCHAR);
            CLB_PigerQuestionSelonImmeuble.registerOutParameter(4, OracleTypes.CURSOR);
            CLB_PigerQuestionSelonImmeuble.execute();

            //Prend les valeurs de retours
            NumQuestion = CLB_PigerQuestionSelonImmeuble.getString(2);
            EnonceQuestion = CLB_PigerQuestionSelonImmeuble.getString(3);
            RS_Reponses = (ResultSet)CLB_PigerQuestionSelonImmeuble.getObject(4);

            while(RS_Reponses.next())
            {
                String Bonne = RS_Reponses.getString(1);
                if(Bonne.equals("O"))
                {
                    bonneReponce = i;
                }
                Reponces[i] = RS_Reponses.getString(2);
                i++;
            }

            //Fermeture du Result set et du Calable statment.
            CLB_PigerQuestionSelonImmeuble.close();
            RS_Reponses.close();

        }
        //Gestion des erreurs
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }

        return new Question(NumQuestion,EnonceQuestion,bonneReponce,Reponces);
    }

    public static void Open()
    {
        try
        {
            //#1 Charger le driver jdbc pour Oracle
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Driver charge !");

            //#2 Établir la connexion à la base de données Oracle
            conn = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connexion ouverte !");
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Diver manquant!");
        }
    }

    public static void Close()
    {
        try
        {
            conn.close();
            System.out.println("Connexion fermee !");
        }
        catch (SQLException Se)
        {
            System.out.println("Impossible de fermer la connexion !");
            System.out.println(Se.getMessage());
        }
    }

    public static void IncrementerDoritos()
    {
        CallableStatement CLB_InvcrementerDoritos = null;
        try
        {
            CLB_InvcrementerDoritos = conn.prepareCall("{call JEU.IncrementeDoritos}");
            CLB_InvcrementerDoritos.execute();
            CLB_InvcrementerDoritos.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void IncrementMountainDew()
    {
        CallableStatement CLB_IncrementMountainDew = null;
        try
        {
            CLB_IncrementMountainDew = conn.prepareCall("{call JEU.IncrementMountainDew}");
            CLB_IncrementMountainDew.execute();
            CLB_IncrementMountainDew.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void Init_BD()
    {
        CallableStatement CLB_Init_BD = null;

        try
        {
            CLB_Init_BD = conn.prepareCall("{call JEU.Init_BD}");
            CLB_Init_BD.execute();
            CLB_Init_BD.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AjouterCapital(int capital)
    {
        CallableStatement CLB_Init_BD = null;

        try
        {
            CLB_Init_BD = conn.prepareCall("{call JEU.IncrementeCapital(?)}");
            CLB_Init_BD.setInt(1, capital);
            CLB_Init_BD.execute();
            CLB_Init_BD.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AcheterChateau()
    {
        CallableStatement CLB_AcheterChateau = null;

        try
        {
            CLB_AcheterChateau = conn.prepareCall("{call JEU.AcheterChateau}");
            CLB_AcheterChateau.execute();
            CLB_AcheterChateau.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AcheterManoir()
    {
        CallableStatement CLB_AcheterManoir = null;
        try
        {
            CLB_AcheterManoir = conn.prepareCall("{call JEU.AcheterManoir}");
            CLB_AcheterManoir.execute();
            CLB_AcheterManoir.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AcheterAuberge()
    {
        CallableStatement CLB_AcheterAuberge = null;

        try
        {
            CLB_AcheterAuberge = conn.prepareCall("{call JEU.AcheteAuberge}");
            CLB_AcheterAuberge.execute();
            CLB_AcheterAuberge.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AcheterDroitDePassageAuberge()
    {
        CallableStatement CLB_AcheterDroitDePassageAuberge = null;
        try
        {
            CLB_AcheterDroitDePassageAuberge = conn.prepareCall("{call JEU.AcheterDroitDePassageAuberge}");
            CLB_AcheterDroitDePassageAuberge.execute();
            CLB_AcheterDroitDePassageAuberge.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AcheterDroitDePassageChateau()
    {
        CallableStatement AcheterDroitDePassageChateau = null;

        try
        {
            AcheterDroitDePassageChateau = conn.prepareCall("{call JEU.AcheterDroitDePassageChateau}");
            AcheterDroitDePassageChateau.execute();
            AcheterDroitDePassageChateau.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AcheterDroitDePassageManoir()
    {
        CallableStatement CLB_AcheterDroitDePassageManoir = null;

        try
        {
            CLB_AcheterDroitDePassageManoir = conn.prepareCall("{call JEU.AcheterDroitDePassagerManoir}");
            CLB_AcheterDroitDePassageManoir.execute();
            CLB_AcheterDroitDePassageManoir.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AcheterSortieDePrisonGoblin()
    {
        CallableStatement CLB_AcheterSortieDePrisonGoblin = null;

        try
        {
            CLB_AcheterSortieDePrisonGoblin = conn.prepareCall("{call JEU.AcheterSortieDePrisonGoblin}");
            CLB_AcheterSortieDePrisonGoblin.execute();
            CLB_AcheterSortieDePrisonGoblin.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static void AcheterSortieDePrisonTroll()
    {
        CallableStatement CLB_AcheterSortieDePrisonGoblin = null;

        try
        {
            CLB_AcheterSortieDePrisonGoblin = conn.prepareCall("{call JEU.AcheterSortieDePrisonTroll}");
            CLB_AcheterSortieDePrisonGoblin.execute();
            CLB_AcheterSortieDePrisonGoblin.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
    }

    public static int GetCapital()
    {
        int Resultats = 0;
        try
        {
            String sql = "select Capital from Joueur";
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            res.next();
            Resultats = res.getInt(1);
            res.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
        return Resultats ;
    }

    public static int GetAuberge()
    {
        int Resultats = 0;
        try
        {
            String sql = "select Auberge from Joueur";
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            res.next();
            Resultats = res.getInt(1);
            res.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
        return Resultats ;
    }

    public static int GetChateau()
    {
        int Resultats = 0;
        try
        {
            String sql = "select Chateau from Joueur";
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            res.next();
            Resultats = res.getInt(1);
            res.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
        return Resultats ;
    }

    public static int GetManoir()
    {
        int Resultats = 0;
        try
        {
            String sql = "select Manoir from Joueur";
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            res.next();
            Resultats = res.getInt(1);
            res.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
        return Resultats ;
    }

    public static int GetMountainDew()
    {
        int Resultats = 0;
        try
        {
            String sql = "select MountainDew from Joueur";
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            res.next();
            Resultats = res.getInt(1);
            res.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
        return Resultats ;
    }

    public static int GetDoritos()
    {
        int Resultats = 0;
        try
        {
            String sql = "select Doritos from Joueur";
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            res.next();
            Resultats = res.getInt(1);
            res.close();
        }
        catch (SQLException Se)
        {
            System.out.println(Se.getMessage());
        }
        return Resultats ;
    }

    static void InfoJoueur()
    {
        System.out.println("Info de LesRingos:");
        System.out.println("Capital: " + GetCapital());
        System.out.println("Auberge: " + GetAuberge());
        System.out.println("Manoir: " + GetManoir());
        System.out.println("Chateau: " + GetChateau());
        System.out.println("MountaiDew: " + GetMountainDew());
        System.out.println("Dorito: " + GetDoritos());
    }

    public static void main(String[] args)
    {
        FonctionSQL FSQL_Test = new FonctionSQL();
        //Ouvrir la conenction.
        FSQL_Test.Open();

        //Remettre toutes les valeur du joueur a 0 et les Questions a pas répondues.
        FSQL_Test.Init_BD();

        System.out.println("Le joueur au debut:");
        FSQL_Test.InfoJoueur();
        System.out.println();

        System.out.println("Rammasse 5000 piece et 1 doritos et 1 mountainDew:");
        FSQL_Test.IncrementerDoritos();
        FSQL_Test.IncrementMountainDew();
        FSQL_Test.AjouterCapital(5000);
        FSQL_Test.InfoJoueur();
        System.out.println();

        System.out.println("Achète un chateaau un manoir et une auberge:");
        FSQL_Test.AcheterAuberge();
        FSQL_Test.AcheterChateau();
        FSQL_Test.AcheterManoir();
        FSQL_Test.InfoJoueur();
        System.out.println();

        System.out.println("Payes les droits de passage pour un manoir un auberge et un chateau.");
        FSQL_Test.AcheterDroitDePassageAuberge();
        FSQL_Test.AcheterDroitDePassageChateau();
        FSQL_Test.AcheterDroitDePassageManoir();
        FSQL_Test.InfoJoueur();
        System.out.println();

        System.out.println("Rammasse 1 doritos et 1 mountainDew:");
        FSQL_Test.IncrementerDoritos();
        FSQL_Test.IncrementMountainDew();
        FSQL_Test.InfoJoueur();
        System.out.println();

        System.out.println("Payes les gobelins et 2 fois les trolls:");
        FSQL_Test.AcheterSortieDePrisonGoblin();
        FSQL_Test.AcheterSortieDePrisonTroll();
        FSQL_Test.AcheterSortieDePrisonTroll();
        FSQL_Test.InfoJoueur();
        System.out.println();

        System.out.println("On tire une Questions");
        Question q = GetQuestionSelonImmeuble("D");
        System.out.println(q.getCodeQuestion()  + " " + q.getEnonce());
        for(int i = 0; i <= 3; i++)
        {
            System.out.println(q.EstBonne(i) + " " + q.getReponces(i));
        }
        System.out.println();

        FSQL_Test.Close();
    }
}