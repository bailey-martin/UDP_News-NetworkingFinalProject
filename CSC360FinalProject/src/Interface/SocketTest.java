//CSC 360 Networking Project--a P2P UDP-Based News Application     SocketTest.java
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketTest {

    private static ArrayList<String> ip_addresses = new ArrayList(); //stores IP addresses of peers
    private static ArrayList<Boolean> can_be_used = new ArrayList(); //true if matching location IP can be sent to; false if already sent to
    private static InetAddress aHost;

    public SocketTest() throws UnknownHostException, IOException { //beginning of constructor
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

            //send IP to weberkcudafac
            InetAddress IP_Server = InetAddress.getByName("weberkcudafac"); //IPServer
            byte data[] = tempIP.getBytes();
            DatagramPacket IPpacket = new DatagramPacket(data, data.length, IP_Server, 55555); //creates a new Datagram and prepares it to be sent over port 55555
            socket.send(IPpacket);
        }//end of try()
        catch (SocketException ex) {
                System.out.println("NAK-Error has occured.");
        }//end of catch()

    } //end of constructor

    public static void main(String[] args) throws IOException { //begin main
        SocketTest s1 = new SocketTest();//designed to pull client IP
        startServer();
        startSender();
    } //end main

    public static void startSender() throws UnknownHostException { //beginning of startSender()
        //InetAddress aHost;
        //InetAddress aHost = InetAddress.getByName("10.18.40.55"); //First host IP
        //InetAddress aHost = InetAddress.getByName("10.18.40.48"); //Second host IP
//        for (int i = 0; i < ip_addresses.size(); i++){
//            System.out.println("WE ARE USING: " + ip_addresses.get(i));
//            InetAddress aHost = InetAddress.getByName(ip_addresses.get(i));
                                
        Scanner scan = new Scanner(System.in);
            (new Thread() {
            @Override
            public void run() {
                String stopLimit = ""; //Creates a stop limit "value"
                while (!("-".equals(stopLimit))) {
                    System.out.println("Please enter the news item that you wish to share, Enter '-' to escape input feed:\n"); //Displays message in Output window to prompt user to enter news item
                    String str = scan.nextLine(); //scans next line for a string value
                    outerloop:
                    if(str.equals("-")){  //if the String equals '-', then the stop limit is set to that symbol
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
                    try {
                            aHost = InetAddress.getByName(ip_addresses.get((1)));
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(SocketTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        DatagramPacket packet = new DatagramPacket(data, data.length, aHost, 55555);
                        int q = 0;
                        while (q < 1) { //begin of while
                            try { //begin of try()
                                System.out.println("Sending news item: " + new String(packet.getData()));
                                socket.send(packet); //sends the news item
                                Thread.sleep(50);
                                q++;
                                System.out.println("Sending Attempt Number of News Item: " + q); //registers how many attempts have been made to send a packet
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
                        socket.receive(packet); //the socket has received the packet
                        //temp = new String(packet.getData()); fixes overwrite issue
                        temp = new String(packet.getData(), packet.getOffset(), packet.getLength());
                        System.out.println("News Item that was received by the server: " + temp); //prints out what the server has received

                        //Break the list of IPs up into individual IP string addresses that can be added to the arrayList ip_addresses
                        String [] splitIPs = temp.split("/"); //Sets the split symbol to '/'
                        for (String s:splitIPs){
                            if (s.contains("1")){ //if the IP split contains '1', enter the for loop and be split in the correct location
                                for (int i = 0; i < ip_addresses.size(); i++){
                                    if (ip_addresses.indexOf(s)==-1){
                                        ip_addresses.add(s);
                                        boolean myBoolean = new Boolean (true);
                                        can_be_used.add(myBoolean);
                                        System.out.println ("ADDING THE IP: " + s + " into the arrayList"); //tells the user the IP is being added to the ArrayList
                                        break; //break the split process
                                    }//end of if-statement
                                }//end of for-loop
                            }//end of if-statement to see if the message contains a valid IP number
                        }//end of for-each loop

                        for (int q = 0; q < ip_addresses.size(); q++){
                            System.out.println ("ARRAYLIST IPS  " + ip_addresses.get(q)); //displays all the IPs in the ArrayList
                            System.out.println ("Size == " + ip_addresses.size()); //prints out the IP addresses in the ArrayList in order
                        }//end of for-loop

                    }//end of try-statement
                    catch (IOException ex) {
                        System.out.println ("NAK"); //creates a NAK when a message is not received but is registered to have been sent
                    }//end of catch
                }//end of while
            }//end of run
        }).start(); //end of thread
    }//end of start server


}//end of class
