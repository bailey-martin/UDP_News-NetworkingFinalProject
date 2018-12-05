package Interface;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SocketTest {
    private boolean run = true;
    private ArrayList<String> ip_addresses = new ArrayList(); //stores IP addresses of peers
    private ArrayList<Boolean> can_be_used = new ArrayList(); //true if matching location IP can be sent to; false if already sent to

    public SocketTest() throws UnknownHostException { //beginning of constructor
        //String ip = ""; //initialize string to contain nothing
        try (final DatagramSocket socket = new DatagramSocket()) {
            //gets host IP address and stores as type InetAddress
            InetAddress ip = InetAddress.getLocalHost();
            //Converts from IPAddress to String
            String tempIP = ip.toString();
            //subString that takes the local host name and / out of the IP address
            tempIP = tempIP.substring(tempIP.lastIndexOf("/") + 1);
            //add ip to arrayList, add boolean true to arrayList
            ip_addresses.add(tempIP);
            can_be_used.add(true);
            System.out.println("IP's have been added to the arrayLists.");
            System.out.println("Your IP address is: " + tempIP);
        } catch (SocketException ex) {
            System.out.println("ERROR IN IP PULL HAS OCCURED.");
        }
    } //end of constructor

    public static void main(String[] args) throws IOException { //begin main
        SocketTest s1 = new SocketTest();//designed to pull client IP
        startServer();
        startSender();
    } //end main
    
    public static void startSender() throws UnknownHostException { //beginning of startSender()
//        InetAddress aHost = InetAddress.getLocalHost();
         InetAddress aHost = InetAddress.getByName("192.168.223.203");
         //InetAddress aHost = InetAddress.getByName("10.18.40.37");
         InetAddress bHost = InetAddress.getByName("192.168.223.114");
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
                   // socket.setBroadcast(true);
                } catch (SocketException ex) {
                    ex.printStackTrace();
                }//end of catch

                DatagramPacket packet = new DatagramPacket(data, data.length, aHost, 55555);
                DatagramPacket packet2 = new DatagramPacket(data, data.length, bHost, 55555);
                data = null;
                int i = 0;
                while (i < 5) { //begin of while
                    try { //begin of try()
                        System.out.println("Sending news item: " + new String(packet.getData()));
                        socket.send(packet);
                        socket.send(packet2);
                        Thread.sleep(50);
                        i++;
                        //Now we need to send to other peers who have not yet gotten this message yet. So..let's pull from the arrayList!
                        SocketTest dummy = new SocketTest();
                        dummy.P2PWork();
                        System.out.println("Sending Attempt Number of News Item: " + i);
                    }//end of try
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }//end of catch
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }//end of catch
                }//end of while-loop
                
            }//end of run()
        }).start(); //end of thread
    } //end of startSender()

    public void P2PWork() throws UnknownHostException {
        ///*random number between 0 and array length of ips 
        //  if there and can be used is true in boolean array, 
        //  then call method start peer 2 peer sender with parameter of ip at location of the 
        //  random number generated. 
        //  change boolean location to false.
        //*/
        Boolean conditional = true;
        if (ip_addresses.size() > 1) {
            while (conditional == true) {
                Random rand = new Random();
                int position = rand.nextInt(ip_addresses.size());
                if (can_be_used.get(position).equals(true)) {
                    conditional = false;
                    can_be_used.set(position, false);
                    startP2PSender(ip_addresses.get(position));
                }//end of if-statement
            }//end of true loop
        }//end of if-peventing 1 user
    }//end of P2PWork Class
  
    public static void startServer() { //beginning of startServer()
        (new Thread() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(55555); //listens on port 55555
                } catch (SocketException ex) {
                    ex.printStackTrace();
                }//end of catch
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);//makes a new packet
                DatagramPacket packet2 = new DatagramPacket(new byte[1024], 1024);
                String temp;
                while (true) {
                    try {
                        socket.receive(packet);
                        temp = new String(packet.getData());
                        System.out.println("News Item that was received by the server: " + temp);
                        //Now we need to send to other peers who have not yet gotten this message yet. So..let's pull from the arrayList!
                        //SocketTest dummy = new SocketTest();
                        //dummy.P2PWork();
                    }//end of try-statement 
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }//end of catch
                }//end of while
            }//end of run
        }).start(); //end of thread
    }//end of start server
            
  //P2P Send

    public void startP2PSender(String ip_addr) throws UnknownHostException {//beginning of startP2PSender()
        InetAddress aHost = InetAddress.getByName(ip_addr); //gets the IP of a peer from arrayList and attempts to send to them
        (new Thread() {
            @Override
            public void run() {
                System.out.println("Please enter the news item that you wish to share:\n");
                Scanner scan = new Scanner(System.in);
                String str = scan.nextLine();
                byte data[] = str.getBytes();
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(); //creates a new socket
                    socket.setBroadcast(true);
                } catch (SocketException ex) {
                    ex.printStackTrace();
                    //parent.quit();
                }//end of catch

                DatagramPacket packet = new DatagramPacket(data, data.length, aHost, 55555);
                int i = 0;
                while (i < 10) {
                    try {
                        System.out.println("Sending news item: " + new String(packet.getData()));
                        socket.send(packet);
                        Thread.sleep(50);
                        i++;
                        System.out.println("Sending Attempt Number of News Item: " + i);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        // parent.quit();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        //parent.quit();
                    }//end of catch
                }//end of while
            }//end of run()
        }).start(); //end of thread
    }//end of startSender()
  
}//end of class