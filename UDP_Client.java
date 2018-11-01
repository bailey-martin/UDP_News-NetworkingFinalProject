import java.net.DatagramSocket;
import java.net.SocketException;

public class UDP_Client implements Runnable{
    private final int clientPort;
    
    public UDP_Client (int clientPort){
        this.clientPort = clientPort;
    }
    
    public void run(){
        try(DatagramSocket clientSocket = new DatagramSocket(clientPort)){
            byte[] buffer = new byte [65507];
            clientSocket.setSoTimeOut(3000);
            while (true){
                DatagramPacket datagramPacket = new DatagramPacket (buffer, 0, buffer.length));
                clientSocket.recieve(datagramPacket);
                
                String recievedMessage = new String(datagramPacket.getData());
                System.out.println(recievedMessage);
            }
        }//end of try
        catch (SocketException e){
            e.printStackTrace();
        }//end of catch
        catch (IOException e){
            //e.printStackTrace();
            System.out.println("Timeout. Client is closing.");
        }
    }//end of run()
}//end of UDP_Client class
