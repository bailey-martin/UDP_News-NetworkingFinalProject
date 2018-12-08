//CSC 360 Networking Project--a P2P UDP-Based News Application      SocketTest_Old.java
//University of Mount Union
//Dr.Weber
//Team Members: Bailey Martin, Matt McMinn, Amanda Hegidus
//Date Published: December 7, 2018
//Project Description: https://silver.mountunion.edu/cs/csc/CSC360/Fall2018/index.htm#project
//GitHub Project Link: https://github.com/bailey-martin/UDP_News-NetworkingFinalProject
package Interface;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        InetAddress aHost = InetAddress.getByName("192.168.209.240"); //First peer IP
        //InetAddress bHost = InetAddress.getByName("10.18.40.48");
        InetAddress bHost = InetAddress.getByName("172.20.1.177"); //Second peer IP
        InetAddress cHost = InetAddress.getByName("10.18.40.55"); //Third peer IP
        Scanner scan = new Scanner(System.in);
            (new Thread() {
            @Override
            public void run() {
                String stopLimit = "";
                while (!("-".equals(stopLimit))) {
                    System.out.println("Please enter the news item that you wish to share, Enter '-' to escape input feed:\n"); //tells the user the program is ready to transmit and tells them how to terminate the program
                    String str = scan.nextLine();
                    outerloop:
                    if(str.equals("-")){ //if the String equals '-', then the stop limit is set to that symbol
                        System.exit(0); //exit the system
                        break outerloop; //breaks the loop
                    }
                    else{
                       stopLimit = str; //Sets the stop limit to a string
                    }
                    byte data[] = str.getBytes();
                    DatagramSocket socket = null;
                    try {
                        socket = new DatagramSocket();
                    } catch (SocketException ex) {
                        ex.printStackTrace();
                    }//end of catch
                        DatagramPacket packet = new DatagramPacket(data, data.length, aHost, 55555); //creates packet for host A
                        DatagramPacket packet1 = new DatagramPacket(data, data.length, bHost, 55555); //creates packet for host B
                        DatagramPacket packet2 = new DatagramPacket(data, data.length, cHost, 55555); //creates packet for host C
                        int q = 0;
                        while (q < 1) { //begin of while
                            try { //begin of try()
                                System.out.println("Sending news item: " + new String(packet.getData()));
                                socket.send(packet); //sends first packet from the first peer
                                socket.send(packet1); //sends second packet from the second peer
                                socket.send(packet2); //sends the third packet from the third peer
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
                        socket.receive(packet); //the packet is received
                        //temp = new String(packet.getData()); fixes overwrite issue
                        temp = new String(packet.getData(), packet.getOffset(), packet.getLength()); //detects the length of a packet and makes that data the only data on that line
                        System.out.println("News Item that was received by the server: " + temp); //prints out what the server has received
                    }//end of try-statement
                    catch (IOException ex) {
                        System.out.println ("NAK"); //creates a NAK when a message is not received but is registered to have been sent
                    }//end of catch
                }//end of while
            }//end of run
        }).start(); //end of thread
    }//end of start server
}//end of class