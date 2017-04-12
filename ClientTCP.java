package ProjetTech_Autopilot;
import java.net.*;
import java.io.*;
import javax.json.*;
import org.glassfish.json.*;
import java.util.Scanner;

public class ClientTCP {

  final static int port = 7182;

  public static void main(String[] args) {
    Socket socket;
    try {
      InetAddress serveur = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()); //ou: args[0]
      socket = new Socket(serveur, port);

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintStream out = new PrintStream(socket.getOutputStream());

      Scanner inSys = new Scanner(System.in);
      String strClass;
      String strName;



      /*
      Exemple d'objet JSON à envoyer :
      --------------------------------
      {
      "type" : "register",
        "sender_class" : "GPS",
        "sender_name" : "GPS 2"
      }

      Code pour la création et l'envoi de l'objet :
      ---------------------------------------------
      JSONObjetc jo = new JSONObjetc();
      jo.put("type", "REGISTER");
      jo.put("sender_class", "GPS");
      jo.put("sender_name", "gps_1");
      System.out.println(jo.toString());
      out.println(jo.toString());


      --> BACKLOG :
      ClientTCP.java:27: error: cannot find symbol
      JSONObjetc jo = new JSONObjetc();
      ^
        symbol:   class JSONObjetc
        location: class ClientTCP
      ClientTCP.java:27: error: cannot find symbol
            JSONObjetc jo = new JSONObjetc();
                                ^
        symbol:   class JSONObjetc
        location: class ClientTCP
      2 errors

      */

      // Résultat attendu :


      //Rentrée des caractéristiques de l'appareil
      System.out.println("Enter the class of your device, then press \"Enter\"\n");
      strClass = inSys.nextLine();
      System.out.println("Enter the name of your device, then press \"Enter\"\n");
      strName = inSys.nextLine();
      
      
	  
      
      //Création de l'objet JSON
      JsonObject jo= Json.createObjectBuilder().add("type", "REGISTER").add("sender_class", strClass).add("sender_name", strName).build();
      out.println(jo.toString());
      System.out.println(in.readLine());

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
