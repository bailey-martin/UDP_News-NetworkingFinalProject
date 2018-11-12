import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClient {
    public static void main(String[] args){
        String sn = "weberkcudafac";
        int sp = 12000;
        try {
            DatagramSocket s = new DatagramSocket();
            String message = "Everyone gets to do the hokey pokey";
            byte[] buf = message.getBytes();
        InetAddress address = InetAddress.getByName(sn);
        DatagramPacket packet = new DatagramPacket (buf, buf.length, address, sp);
        
        s.send(packet);
        s.receive(packet);
        System.out.println("Received from grandma: "  + new String (buf));
        }//end of try 
        catch (SocketException ex) {
            System.out.println ("Ooops" + ex);
        }//end of catch
        catch (UnknownHostException ex) {
            System.out.println ("Ooops" + ex);
        }//end of catch
        catch (IOException ex) {
            System.out.println ("Ooops" + ex);
        }//end of catch
        
        }//end of main
        
}//end of UDPClient class
