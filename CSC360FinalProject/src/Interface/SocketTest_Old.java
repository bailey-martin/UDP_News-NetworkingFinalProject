package Interface;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketTest_Old {

    private static ArrayList<String> ip_addresses = new ArrayList(); //stores IP addresses of peers
    private static ArrayList<Boolean> can_be_used = new ArrayList(); //true if matching location IP can be sent to; false if already sent to

    public SocketTest_Old() throws UnknownHostException, IOException { //beginning of constructor
        try (final DatagramSocket socket = new DatagramSocket()) {
            //gets host IP address and stores as type InetAddress
            InetAddress ip = InetAddress.getLocalHost();
            //Converts from IPAddress to String
            String tempIP = ip.toString();
            //subString that takes the local host name and / out of the IP address
            tempIP = tempIP.substring(tempIP.lastIndexOf("/") + 1);
            //add ip to arrayList, add boolean true to arrayList

            ip_addresses.add(tempIP); //adds to arrayList
            can_be_used.add(true); //adds to secondary arrayList
            System.out.println("IP's have been added to the arrayLists.");  //alerts user they have been added
            System.out.println("Your IP address is: " + tempIP);    //displays peer's IP address
        }//end of try()
        catch (SocketException ex) {
                System.out.println("NAK-Error has occured.");
        }//end of catch()

    } //end of constructor

    public static void main(String[] args) throws IOException { //begin main
        SocketTest_Old s1 = new SocketTest_Old();//designed to pull client IP
        startServer();
        startSender();
    } //end main

    public static void startSender() throws UnknownHostException { //beginning of startSender()
        InetAddress aHost = InetAddress.getByName("192.168.209.240");
        InetAddress bHost = InetAddress.getByName("10.18.40.48");
        InetAddress cHost = InetAddress.getByName("10.18.40.55");                           
        Scanner scan = new Scanner(System.in);
            (new Thread() {
            @Override
            public void run() {
                String stopLimit = "";
                while (!("-".equals(stopLimit))) {
                    System.out.println("Please enter the news item that you wish to share, Enter '-' to escape input feed:\n");
                    String str = scan.nextLine();
                    outerloop:
                    if(str.equals("-")){
                        stopLimit = "-";
                        System.exit(0);
                        break outerloop;
                    }
                    else{
                       stopLimit = str; 
                    }
                    byte data[] = str.getBytes();
                    DatagramSocket socket = null;
                    try {
                        socket = new DatagramSocket();
                    } catch (SocketException ex) {
                        ex.printStackTrace();
                    }//end of catch
                        DatagramPacket packet = new DatagramPacket(data, data.length, aHost, 55555);
                        DatagramPacket packet1 = new DatagramPacket(data, data.length, bHost, 55555);
                        DatagramPacket packet2 = new DatagramPacket(data, data.length, cHost, 55555);
                        data = null;     
                        int q = 0;
                        while (q < 1) { //begin of while
                            try { //begin of try()
                                System.out.println("Sending news item: " + new String(packet.getData()));
                                socket.send(packet);
                                socket.send(packet1);
                                socket.send(packet2);
                                Thread.sleep(50);
                                q++;
                                System.out.println("Sending Attempt Number of News Item: " + q);
                            }//end of try
                            catch (IOException | InterruptedException ex) {
                                System.out.println ("NAK-sending end");
                            }//end of catch()
                        }//end of while-loop
                }//end of big for-loop
    }//end of run()      
        }).start(); //end of thread
    } //end of startSender()
    
    public static void startServer() { //beginning of startServer()
        (new Thread() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(55555); //listens on port 55555
                }//end of try()
                catch (SocketException ex) {
                    ex.printStackTrace();
                }//end of catch()
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);//makes a new packet
                String temp;
                while (true) {
                    try {
                        socket.receive(packet);
                        //temp = new String(packet.getData()); fixes overwrite issue
                        temp = new String(packet.getData(), packet.getOffset(), packet.getLength());
                        System.out.println("News Item that was received by the server: " + temp);
                    }//end of try-statement
                    catch (IOException ex) {
                        System.out.println ("NAK");
                    }//end of catch
                }//end of while
            }//end of run
        }).start(); //end of thread
    }//end of start server
}//end of class