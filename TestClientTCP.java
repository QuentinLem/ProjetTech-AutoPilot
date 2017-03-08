import java.net.*;
import java.io.*;

public class TestClientTCP {
  final static int port = 7182;

  public static void main(String[] args) {

    Socket socket;
    DataInputStream userInput;
    PrintStream theOutputStream;

    try {
      InetAddress serveur = InetAddress.getByName(InetAddress.getHostAddress()); //ou: args[0]
      socket = new Socket(serveur, port);

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintStream out = new PrintStream(socket.getOutputStream());

      out.println("TEST");
      System.out.println(in.readLine());

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
