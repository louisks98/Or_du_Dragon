package sample;

/**
 * Created by Georges on 2017-03-18.
 * Permet de créé une questions avec ces réponces.
 */
public class Question
{
    private static String codeQuestion;
    private static String enonce;
    private static String reponces[];
    private static boolean estBonne[] = {false,false,false,false};
    private static int numBonneReponce;

    public static int getNumBonneReponce()
    {
        return numBonneReponce;
    }

    public static String getCodeQuestion()
    {
        return codeQuestion;
    }

    public Question(String numQuestion, String enonceQuestion, int bonneReponce, String reponcesQuestion[])
    {
        codeQuestion = numQuestion;
        enonce = enonceQuestion;
        reponces = reponcesQuestion;
        estBonne[bonneReponce] = true;
        numBonneReponce = bonneReponce;
    }

    public static String getEnonce()
    {
        return enonce;
    }

    public static String getReponces(int index)
    {
        if(index <= 3 && index >= 0)
        {
            return reponces[index];
        }
        else
        {
            System.err.println("Lindex d'une question est 0,1,2,3");
            return "Lindex d'une question est 0,1,2,3";
        }
    }

    public static boolean EstBonne(int index)
    {
        if(index <= 3 && index >= 0)
        {
            return estBonne[index];
        }
        else
        {
            System.err.println("Lindex d'une question est 0,1,2,3");
            return false;
        }
    }

    public static String getQuestionToServeur()
    {
        return (String)(":" + getEnonce() + ":" +
                getReponces(0) + ":" +
                getReponces(1) + ":" +
                getReponces(2) + ":" +
                getReponces(3) + ":" +
                getNumBonneReponce());
    }
}
