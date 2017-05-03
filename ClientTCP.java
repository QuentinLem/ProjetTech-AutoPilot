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
      Exemple d'objet JSON à envoyer :
      --------------------------------
      {
      "type" : "register",
      "sender_class" : "GPS",
      "sender_name" : "GPS_2"
      }

      */


      //Rentrée des caractéristiques de l'appareil
      System.out.println("Enter the class of your device, then press \"Enter\"\n");
      strClass = inSys.nextLine();
      System.out.println("Enter the name of your device, then press \"Enter\"\n");
      strName = inSys.nextLine();


      //Création de l'objet JSON
      JSONObject object = new JSONObject();
      object.put("type", "register");
      object.put("sender_class", strClass);
      object.put("sender_name", strName);

      System.out.println(object.toString());


      //Envoi du message JSON
      out.println(object.toString());


      //Attente de la réponse du serveur
      System.out.println(in.readLine());



    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
