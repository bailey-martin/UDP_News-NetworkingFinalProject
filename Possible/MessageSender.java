package herexChatProg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JOptionPane;

import login.MainScreen;

public class MessageSender extends Thread {
private int Port;
private String recIP;
private final static BufferedReader in = new BufferedReader(new   InputStreamReader(System.in));

private MainScreen Screen;

private DatagramSocket ds = null;
private DatagramPacket dp = null;

public MessageSender(MainScreen m, String ip, int port) throws Exception {
    recIP = ip;
    Port = port;
    Screen = m;
    System.out.println("chat program: IP address: " + recIP + " port " + Port);
    start();

}

public void run() {
    try {
        // open DatagramSocket to receive
        ds = new DatagramSocket(Port);
        // loop forever reading datagrams from the DatagramSocket
        while (true) {
            byte[] buffer = new byte[65000]; // max char length
            dp = new DatagramPacket(buffer, buffer.length);
            ds.receive(dp);
            String s = new String(dp.getData(), 0, dp.getLength());

            Screen.writeText(s);
            // System.out.println("UDP datagram length " + s.length() + "
            // from IP " + dp.getAddress() + " received: " + s);
        }
    } catch (SocketException se) {
        System.err.println("chat error (Socket Closed = good): " + Se.getMessage());
        JOptionPane.showMessageDialog(null, "Please check your connection or try to log on again");
        } catch (IOException se) {
        System.err.println("chat error: " + se.getMessage());
    }
}

public void Stop() {
    if (ds != null) {
        ds.close();
        ds = null;
    }
}



public boolean sendMessage(String message) throws IOException {
    try {
        System.out.println("Sending to " + recIP + " socket " + Port + " data: " + message);
        byte[] data = message.getBytes();
        DatagramSocket theSocket = new DatagramSocket();
        DatagramPacket theOutput = new DatagramPacket(data, data.length, InetAddress.getByName(recIP), Port);
        theSocket.send(theOutput);
        Screen.writeText(message);
        return true;
    } catch (IOException e) {
        return false;
    }
}
}