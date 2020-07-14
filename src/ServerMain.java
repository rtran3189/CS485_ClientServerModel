/**
 * This is the main driver of the server-side of this program.
 * It contains the main method and starts the server and listens
 * for connections of clients and kicks off the process of client
 * creation.
 *
 * @author Richard Tran
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {
    private static Server server = new Server();
    private static int counter = 1;

    /**
     * Checks the list of waiting clients and takes appropriate action with the client.
     * @param clientSocket
     */
    private static void checkWaitingClientList(Socket clientSocket)
    {
        // If the waiting list is empty, create a client with given socket and
        // place them into the list.
        Client client = new Client(clientSocket, counter);
        Thread t = new Thread(client);
        t.start();
        server.addWaitingClient(client);
        System.out.println("Adding client with id: " + counter);
        counter++;

        // This sleep is necessary because it allows the client thread
        // to properly set its name and destination client before the
        // pair connection check runs.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (server.getWaitingSize() > 1)
        {
            // check for merge.
            server.lookForAndConnectPairs();
        }
    }

    /**
     * Server main. As soon as it is run it will ask what port number to
     * configure it as. Takes no command line arguments.
     */
    public static void main (String[] args) throws IOException
    {
        System.out.println("----SERVER SETUP----");
        System.out.print("Enter port number: ");

        Scanner s = new Scanner(System.in);
        String input = s.nextLine();

        int portNumber = Integer.parseInt(input);

        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Port number configured. Port: " + portNumber);
        while (true)
        {
            System.out.println("Waiting for client requests... ");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established: " + clientSocket.getLocalAddress());
            System.out.println("                    IP: " + clientSocket.getInetAddress());
            checkWaitingClientList(clientSocket);

        }
    }
}
