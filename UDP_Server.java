import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDP_Server implements Runnable{
    private final int clientPortNumber;
    
    public UDP_Server (int clientPortNumber){
        this.clientPortNumber = clientPortNumber;
    }
    
    public void run(){
        try (DatagramSocket serverSocket = new DatagramSocket(55554)){
            for (int i = 0; i < 3; i++){
                String message = "Message number " + 1;
                DatagramPacket datagramPacket = new DatagramPacket(
                                                                   message.getBytes(), message.length(), InetAddress.getLocalHost(), clientPortNumber);
                serverSocket.send(datagramPacket);
            }//end of for-loop
        }//end of try
        catch (SocketException e){
            e.printStackTrace();
        }//end of catch
        catch (UnknownHostException e){
            e.printStackTrace();
        }//end of catch
        catch (IOException e){
            e.printStackTrace();
        }//end of catch
    }//end of run ()
}//end of UDP_Server class
