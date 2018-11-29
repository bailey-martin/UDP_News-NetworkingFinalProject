package Interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Random;
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
            System.out.println("Please enter the news item that you wish to share:\n");
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
                    
                    //Now we need to send to other peers who have not yet gotten this message yet. So..let's pull from the arrayList!
                    boolean condition=true;
                    while(condition==true){
                        /*
                        random number between 0 and array length of ips 
                        if there and can be used is true in boolean array, 
                        then call method start peer 2 peer sender with parameter of ip at location of the 
                        random number generated. 
                        change boolean location to false.
                        */
                        
                        Random rand=new Random();
                        int position = rand.nextInt(Launching_Frame.counter);
                    }
                    
                    //System.out.println("Message received ..."+ temp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    //parent.quit();
                }//end of catch
                 }//end of while
            }//end of run
    }).start();
 }//end of start server
  
  
  
  
  //P2P Send
  public static void startP2PSender(String ip_addr) throws UnknownHostException{
    InetAddress aHost = InetAddress.getByName(ip_addr);
    (new Thread() {
        @Override
        public void run() {
            System.out.println("Please enter the news item that you wish to share:\n");
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
}//end of class