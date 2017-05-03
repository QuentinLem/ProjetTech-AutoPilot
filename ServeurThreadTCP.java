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

      private String message = "";
      JSONObject jReceive;
      JSONObject jSend;


      message = in.readLine();
      System.out.println(message);
      jReceive = new JSONObject(message);
      System.out.println(jReceive.toString());

      //TERMINER ENVOI
      //jSend=new JSONObject("");
      //out.println("{\"type\":\"register\",\"sender_id\":\"1\",\"ack\":{\"resp\":\"ok\"}}");

      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
