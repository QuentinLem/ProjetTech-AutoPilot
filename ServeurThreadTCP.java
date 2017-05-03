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

      //Envoi confirmation
      jSend = new JSONObject();
      jSend.put("type", "register");
      jSend.put("sender_id", "1");
      jSend.put("ack", "ok");

      out.println(jSend.toString());
      out.flush();




      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
