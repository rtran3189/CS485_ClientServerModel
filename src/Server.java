/**
 * This class represents the server which is responsible for
 * maintaining and manipulating a database of waiting users
 * in order to pair them together for a chat session.
 *
 * @author Richard Tran
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Server
{
    // The list in which clients are waiting to get paired with their desired client.
    private ArrayList<Client> clientWaitingList = new ArrayList<>();


    // Empty constructor
    public Server()
    {

    }

    /**
     * Adds a client into the waiting list.
     * @param client The client to add
     */
    public void addWaitingClient(Client client)
    {
        clientWaitingList.add(client);
    }

    /**
     * Removes a client form the waiting list.
     * @param client Client to remove.
     */
    public void removeWaitingClient(Client client)
    {
        clientWaitingList.remove(client);
    }

    /**
     * Gets the size of the waiting list.
     * @return
     */
    public int getWaitingSize()
    {
        return clientWaitingList.size();
    }

    /**
     * Gets the name of the client by index.
     * @param i index
     * @return String name of the client.
     */
    public String getClientNameByIndex(int i)
    {
        return clientWaitingList.get(i).getClientName();
    }

    /**
     * Gets a client object by its ID
     * @param id ID
     * @return Client
     */
    public Client getClientById(int id)
    {
        for (int i = 0; i < clientWaitingList.size(); i++)
        {
            if(clientWaitingList.get(i).getId() == id)
            {
                return clientWaitingList.get(i);
            }
        }
        return null;
    }

    /**
     * Iterates through the database and checks for matches in a client's name
     * and who they wish to chat with.
     */
    public void lookForAndConnectPairs()
    {
        for (int i = 0; i < clientWaitingList.size(); i++)
        {
            Client lookedClient = clientWaitingList.get(i);
            for (int j = 0; j < clientWaitingList.size(); j++)
            {
                if (i != j)
                {
                    if(checkIfMutual(lookedClient, clientWaitingList.get(j)))
                    {
                        pairClients(lookedClient, clientWaitingList.get(j));
                    }
                }
            }
        }
    }

    /**
     * Checks if two clients have a mutual interest in chatting with one another.
     * @param sourceClient The first client.
     * @param destClient The second client/
     * @return Boolean if they are mutually interested.
     */
    private boolean checkIfMutual (Client sourceClient, Client destClient)
    {
        System.out.println("1. " + sourceClient.getClientName() + " is looking for: " + sourceClient.getLookingFor());
        System.out.println("2. " + destClient.getClientName() + " is looking for: " + destClient.getLookingFor());
        if (sourceClient.isPaired() || destClient.isPaired())
        {
            return false;
        }
        else if (sourceClient.getClientName().equals(destClient.getLookingFor()) &&
                destClient.getClientName().equals(sourceClient.getLookingFor()))
        {
            System.out.println("PAIRING " + sourceClient.getClientName() + " with " + destClient.getClientName());
            return true;
        }
        return false;
    }

    /**
     * Pairs the two client's streams together.
     * Input stream of sourceClient -> Output stream of destClient
     * Output stream of sourceClient -> Input stream of destClient
     * @param sourceClient Source client
     * @param destClient Destination client
     */
    private void pairClients(Client sourceClient, Client destClient)
    {
        // Set the source client's output stream to the dest client's input stream.
        sourceClient.setOutputStream(destClient.getClientSocket());
        sourceClient.setInputStream(destClient.getClientSocket());
        // Setting dest client's input stream to the source client's output stream.
        destClient.setInputStream(sourceClient.getClientSocket());
        destClient.setOutputStream(sourceClient.getClientSocket());

        sourceClient.setPairedStatus(true);
        destClient.setPairedStatus(true);
        try {
            PrintWriter srcOut =
                    new PrintWriter(sourceClient.getClientSocket().getOutputStream(), true);
            PrintWriter destOut =
                    new PrintWriter(destClient.getClientSocket().getOutputStream(), true);
            srcOut.println(("You are now connected to " + destClient.getClientName() + " :)"));
            destOut.println(("You are now connected to " + sourceClient.getClientName() + " :)"));
        } catch (IOException e) {
            e.printStackTrace();

        }

        // Creates two chat session tunnels for each other.
        ChatSessionTunnel chatSession = new ChatSessionTunnel(sourceClient, destClient);
        ChatSessionTunnel chatSession2 = new ChatSessionTunnel(destClient, sourceClient);
        Thread t = new Thread(chatSession);
        Thread t2 = new Thread(chatSession2);
        t.start();
        t2.start();
    }

}
