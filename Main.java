import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main{
    public static void main(String[] args){
        //int port = 55555;
        int port=50001;
        UDP_Server server = new UDP_Server (port);
        UDP_Client client = new UDP_Client (port);
        
        ExecutorService executeTheService = Executors.newFixedThreadPool(2);
        executeTheService.submit(client);
        executeTheService.submit(server);
    }//end of main()
}//end of Main class
