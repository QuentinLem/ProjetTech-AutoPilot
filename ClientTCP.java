package javax.json;
import java.net.*;
import java.io.*;
import javax.json.*;
import org.glassfish.json.*;

public class ClientTCP {

  final static int port = 7182;

  public static void main(String[] args) {
    Socket socket;
    try {
      InetAddress serveur = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()); //ou: args[0]
      socket = new Socket(serveur, port);

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintStream out = new PrintStream(socket.getOutputStream());



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

      JsonObject jo= Json.createObjectBuilder().add("type", "REGISTER").add("sender_class", "GPS").add("sender_name", "gps_1").build();
      out.println(jo.toString());
      System.out.println(in.readLine());

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
