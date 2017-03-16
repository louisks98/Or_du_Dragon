package sample;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by 201567153 on 2017-03-16.
 */
public class ServeurPosition implements Runnable{

    final private int  PORT_POSITION = 51006;
    final private String IP = "149.56.47.97";
    private String Packet[];
    @Override
    public void run()
    {
        BufferedReader serveurReader;
        PrintWriter serveurWriter;

        try
        {
            Socket socketPosition = new Socket(IP, PORT_POSITION);
            serveurReader = new BufferedReader(new InputStreamReader(socketPosition.getInputStream()));
            serveurWriter = new PrintWriter(new OutputStreamWriter(socketPosition.getOutputStream()));
            String ligne;

            ligne = serveurReader.readLine();
            serveurWriter.println("");
            serveurWriter.flush();
            //do
           //{
            if (ligne != null)
            {
                Packet = ligne.split(" ");
                //Position.add(Packet);
                //ligne = serveurReader.readLine();

            }
            //}while (ligne != null);
            socketPosition.close();
        }
        catch (java.io.IOException e)
        {
            System.out.println("Impossible de se connecter au serveur");
        }
    }

    public String[] GetPosition() {return Packet;}
}
