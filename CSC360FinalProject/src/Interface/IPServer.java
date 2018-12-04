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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author it_martin
 */
public class IPServer extends Thread {
 
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
 
    public IPServer() throws SocketException {
        socket = new DatagramSocket(55555);
        System.out.println ("Listening on port 5555 for data.......");
    }
 
    public void run() {
        running = true;
 
        while (running) {
            DatagramPacket packet 
              = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(IPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received 
              = new String(packet.getData(), 0, packet.getLength());
             
            if (received.equals("end")) {
                running = false;
                continue;
            }
            try {
                socket.send(packet);
            } catch (IOException ex) {
                Logger.getLogger(IPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        socket.close();
    }
    public static void main (String [] args) throws SocketException{
        IPServer a = new IPServer();
    }//end of main
}