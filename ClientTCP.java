import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;

import org.json.*;



public class ClientTCP {

  final static int port = 7182;

  public static void main(String[] args) {
    Socket socket;
    try {
      InetAddress serveur = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()); //ou: args[0]
      socket = new Socket(serveur, port);

      System.out.println("Connecté !\n");

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream());

      //Objets pour la lecture dans le buffer
      Scanner inSys = new Scanner(System.in);
      String strClass;
      String strName;



      /*
      System.out.println();
      */

      //Rentrée des caractéristiques de l'appareil
      System.out.println("Enter the class of your device, then press \"Enter\"\n");
      strClass = inSys.nextLine();
      System.out.println("Enter the name of your device, then press \"Enter\"\n");
      strName = inSys.nextLine();


      //Création de l'objet JSON
      JSONObject object = new JSONObject()
            .put("type", "register")
            .put("sender_class", strClass)
            .put("sender_name", strName);

      System.out.println(object.toString());


      //Envoi du message JSON
      out.println(object.toString());
      out.flush();


      //Attente de la réponse du serveur
      String confirm = "";
      JSONObject jo;

      confirm = in.readLine();
      jo = new JSONObject(confirm);
      System.out.println(jo.toString());


      System.out.println("Connexion terminée.\n");

      socket.close();

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
