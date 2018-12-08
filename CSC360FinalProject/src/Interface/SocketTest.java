package Interface;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketTest {

    private static ArrayList<String> ip_addresses = new ArrayList(); //stores IP addresses of peers
    private ArrayList<Boolean> can_be_used = new ArrayList(); //true if matching location IP can be sent to; false if already sent to

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
            DatagramPacket IPpacket = new DatagramPacket(data, data.length, IP_Server, 55555);
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
        //InetAddress aHost = InetAddress.getByName("10.18.40.55");
        //InetAddress aHost = InetAddress.getByName("10.18.40.48");
//        for (int i = 0; i < ip_addresses.size(); i++){
//            System.out.println("WE ARE USING: " + ip_addresses.get(i));
//            InetAddress aHost = InetAddress.getByName(ip_addresses.get(i));
//            sendOps(aHost);
        //}//end of for-loop
                                
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
                    for (int z = 0; z < ip_addresses.size(); z++){
                        InetAddress aHost = null;
                        try {
                            aHost = InetAddress.getByName(ip_addresses.get(z));
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(SocketTest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    DatagramPacket packet = new DatagramPacket(data, data.length, aHost, 55555);
                    data = null;
                    int q = 0;
                    while (q < 1) { //begin of while
                        try { //begin of try()
                            System.out.println("Sending news item: " + new String(packet.getData()));
                            socket.send(packet);
                            Thread.sleep(50);
                            q++;
                            System.out.println("Sending Attempt Number of News Item: " + q);
                        }//end of try
                        catch (IOException | InterruptedException ex) {
                            System.out.println ("NAK-sending end");
                        }//end of catch()
                    }//end of while-loop
                }//end of while-loop
            }//end of run()
            }//I just added
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

                        //break it up
                        String [] splitIPs = temp.split("/");
                        for (String s:splitIPs){
                            if (s.contains("1")){
                                for (int i = 0; i < ip_addresses.size(); i++){
                                    if (ip_addresses.indexOf(s)==-1){
                                        ip_addresses.add(s);
                                        System.out.println ("ADDING THE IP: " + s + " into the arrayList");
                                        break;
                                    }//end of if-statement
                                }//end of for-loop
                            }//end of if-statement to see if the message contains a valid IP number
                        }//end of for-each loop

                        for (int q = 0; q < ip_addresses.size(); q++){
                            System.out.println ("ARRAYLIST IPS  " + ip_addresses.get(q));
                            System.out.println ("Size == " + ip_addresses.size());
                        }//end of for-loop

                    }//end of try-statement
                    catch (IOException ex) {
                        System.out.println ("NAK");
                    }//end of catch
                }//end of while
            }//end of run
        }).start(); //end of thread
    }//end of start server


}//end of class
