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
 
    public String sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, address, 55555);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
          packet.getData(), 0, packet.getLength());
        return received;
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