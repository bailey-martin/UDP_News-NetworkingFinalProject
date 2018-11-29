package Interface;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SocketTest {
  private boolean run = true;
  private ArrayList <String> ip_addresses = new ArrayList();
  private ArrayList <Boolean> can_be_used = new ArrayList(); //boolean arraylist; true if matching location IP can be sent to; false if already sent to

public String getClientIP(){
        String ip = "";
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 55555);  //changed from 10002 to 55555
            ip = socket.getLocalAddress().getHostAddress();
            //add ip to arrayList
            ip_addresses.add(ip);
            can_be_used.add(true);
        } catch (SocketException ex) {
            System.out.println ("ERROR IN IP PULL HAS OCCURED.");
        } catch (UnknownHostException ex) {
            System.out.println ("ERROR IN IP PULL HAS OCCURED.");
        }
        return ip;
    }//end of getClientIP()

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
  
  public void P2PWork() throws UnknownHostException{
//                        /*
//                        random number between 0 and array length of ips 
//                        if there and can be used is true in boolean array, 
//                        then call method start peer 2 peer sender with parameter of ip at location of the 
//                        random number generated. 
//                        change boolean location to false.
//                        */
                        Boolean conditional = true;
                        if (ip_addresses.size()>1){
                        while (conditional == true){
                        Random rand=new Random();
                        int position = rand.nextInt(ip_addresses.size());
                        if (can_be_used.get(position).equals(true)){
                            //code
                            conditional = false;
                            can_be_used.set(position, false);
                            startP2PSender(ip_addresses.get(position));
                        }//end of if-statement
                        }//end of true loop
                        }//end of if-peventing 1 user
  }//end of P2PWork Class
  
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
                   SocketTest dummy = new SocketTest();
                   dummy.P2PWork();
                 
//                    
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