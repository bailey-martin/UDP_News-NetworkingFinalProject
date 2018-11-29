import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;

public class SocketTest {
  private boolean run = true;

  public static void main(String[] args) throws IOException {
    startServer();
    startSender();
  }

  public static void startSender() throws UnknownHostException{
    InetAddress aHost = InetAddress.getLocalHost();
    (new Thread() {
        @Override
        public void run() {
            Scanner scan = new Scanner(System.in);
            String str = scan.nextLine();
            byte data[] = str.getBytes();
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setBroadcast(true);
            } catch (SocketException ex) {
                ex.printStackTrace();
                //parent.quit();
            }//end of catch

            DatagramPacket packet = new DatagramPacket(data,data.length,aHost,55555);
            int i=0;
            while (i<10) {
                try {
                    System.out.println("Sending news item: "+new String(packet.getData()));
                    socket.send(packet);
                    Thread.sleep(50);
                    i++;
                    System.out.println("Sending Attempt Number of News Item: " + i);
                } catch (IOException ex) {
                    ex.printStackTrace();
                   // parent.quit();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                   // parent.quit();
                }
            }
        }}).start();
    }//end of startSender()


  public static void startServer() {
    (new Thread() {
        @Override
        public void run() {
                //byte data[] = new byte[0];
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(55555); //listens on port 55555
                    //socket.setBroadcast(true);;
                } catch (SocketException ex) {
                    ex.printStackTrace();
                    //parent.quit();
                }//end of catch
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024); //makes a new packet
                //System.out.println("this is what has been received111"+packet.getData());
                String temp;
                while (true) {
                try {
                    socket.receive(packet);
                    temp=new String(packet.getData());
                    System.out.println("News Item that was received by the server: "+temp);
                    //System.out.println("Message received ..."+ temp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    //parent.quit();
                }//end of catch
                 }//end of while
            }//end of run
    }).start();
 }//end of start server
}//end of class