import java.net.*;
import java.io.*;

import org.json.*;



public class ServeurThreadTCP extends Thread {

    //données membres
    final static int port = 7182;
    private Socket socket;


  public static void main(String[] args) {
    try {
	//création de la socket du serveur
      ServerSocket socketServeur = new ServerSocket(port);
      System.out.println("Lancement du serveur sur "+ InetAddress.getLocalHost().toString());

      //boucle d'échange serveur/client
      while (true) {
        Socket socketClient = socketServeur.accept();
        ServeurThreadTCP t = new ServeurThreadTCP(socketClient);
        t.start();
      }

      //gestion d'erreurs
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

    //création d'une socket pour la nouvelle connexion avec le client
  public ServeurThreadTCP(Socket socket) {
    this.socket = socket;
  }


  public void run() {
    try {

      //création des flux entrants et sortants de la socket
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream());

      System.out.println("Connexion avec le client : " + socket.getInetAddress());

      String message = "";
      JSONObject jReceive;
      JSONObject jSend;

      //Récupération du message client
      message = in.readLine();
      jReceive = new JSONObject(message);
      System.out.println(jReceive.toString());

      /*
      //Gestion du type de requête

      String req = new String(jReceive.getString("type"))
      if ( req == "register") {
          jSend = register(jReceive);
      }
      else if ( req == "deregister") {
          jSend = deregister(jReceive);
      }
      else if ( req == "list") {
          jSend = list(jReceive);
      }
      else if ( req == "send") {
          jSend = send(jReceive);
      }
      else if ( req == "get") {
          jSend = get(jReceive);
      }
      else if ( req == "get_last") {
          jSend = get_last(jReceive);
      }
      else {
          jSend.put("type", req).put("ack", new JSONObject().put("error", 400));
      }
      */


      //Envoi confirmation

      out.println(jSend.toString());
      out.flush();




      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
