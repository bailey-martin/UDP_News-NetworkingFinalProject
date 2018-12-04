/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author it_martin
 */
public class IPServerSender {
    private DatagramSocket socket;
    private InetAddress address;
 
    private byte[] buf;
 
    public IPServerSender() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("weberkcudafac");
    }
 
    public void sendEcho(String msg) throws IOException {
//        buf = msg.getBytes();
//        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 55555);
//        socket.send(packet);

         InetAddress aHost = InetAddress.getByName("weberkcudafac");
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
                int i = 0;
                while (i < 10) { //begin of while
                    try { //begin of try()
                        System.out.println("Sending news item: " + new String(packet.getData()));
                        socket.send(packet);
                        Thread.sleep(50);
                        i++;
                        
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
    }
 
    public void close() {
        socket.close();
    }
    
    public static void main (String [] args) throws SocketException, UnknownHostException, IOException{
        IPServerSender a = new IPServerSender();
        Scanner myScanner = new Scanner (System.in);
        System.out.println("Enter the IP address that you would like to send:");
        String message = myScanner.nextLine();
        a.sendEcho(message);
        System.out.println("Message sent");
    }//end of main
}