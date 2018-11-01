public class Main{
    public static void main(String[] args){
        int port = 55555;
        UDP_Server server = new UDP_Server (port);
        UDP_Client client = new UDP_Client (port);
        
        ExecuteService executeTheService = Executors.newFixedThreadPoll(2);
        executeTheService.submit(client);
        executeTheService.submit(server);
    }//end of main()
}//end of Main class
