import java.net.*;
import java.io.*;

public class ServeurThreadTCP extends Thread {

  final static int port = 7182;
  private Socket socket;

  public static void main(String[] args) {
    try {
      ServerSocket socketServeur = new ServerSocket(port);
      System.out.println("Lancement du serveur sur "+ InetAddress.getLocalHost().toString());
      while (true) {
        Socket socketClient = socketServeur.accept();
        ServeurThreadTCP t = new ServeurThreadTCP(socketClient);
        t.start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ServeurThreadTCP(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      String message = "";

      System.out.println("Connexion avec le client : " + socket.getInetAddress());

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintStream out = new PrintStream(socket.getOutputStream());

      message = in.readLine();
      System.out.println(message);
      out.println("{\"type\":\"register\",\"sender_id\":\"1\",\"ack\":{\"resp\":\"ok\"}}");

      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
