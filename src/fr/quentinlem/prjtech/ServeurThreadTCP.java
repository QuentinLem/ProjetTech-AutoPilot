//package fr.quentinlem.prjtech;

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


  public JSONObject requestHandler(JSONObject jo) {
    String s = jo.getString("type");
    JSONObject jRequest;

    if ( s == "register") {
        jRequest = requestRegister(jo);
    }
    else if ( s == "deregister") {
        jRequest = requestDeregister(jo);
    }
    else if ( s == "list") {
        jRequest = requestList(jo);
    }
    else if ( s == "send") {
        jRequest = requestSend(jo);
    }
    else if ( s == "get") {
        jRequest = requestGet(jo);
    }
    else if ( s == "get_last") {
        jRequest = requestGetLast(jo);
    }
    else {
        jRequest = new JSONObject().put("type", s).put("ack", new JSONObject().put("error", 400));
    }
    return jRequest;
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


      //Appel du bus ?
      Bus b = new Bus();

      //Gestion du type de requête
      jSend = requestHandler(jReceive);


      //Envoi confirmation
      out.println(jSend.toString());
      out.flush();




      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
