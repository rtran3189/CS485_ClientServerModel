/**
 * This class represents a connected chat client and contains
 * fields and methods relating to it.
 *
 * @author Richard Tran
 */

import java.io.*;
import java.net.Socket;

/**
 * Client object. Each client object contains its name, socket, and who the
 * client is looking for to chat with. All of these properties have set and
 * get functions.
 */
public class Client implements Runnable
{
    private String clientName;
    private int id;
    private Socket socket;
    private String lookingFor;
    //Received messages
    private BufferedReader inReader;
    //Sent messages
    private PrintWriter outputStream;
    private Thread clientThread;
    private boolean paired;

    /**
     * Constructor.
     * @param socket - Socket of the client.
     */
    public Client(Socket socket, int id)
    {
        this.clientName = "UNKNOWN_CLIENT_NAME";
        this.lookingFor = "UNKNOWN_CLIENT_DEST";
        this.paired = false;
        this.socket = socket;
        this.id = id;
        try {
            this.inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the client's name.
     * @param name The name to set.
     */
    public void setClientName(String name)
    {
        clientName = name;
    }

    /**
     * Gets the client's name.
     * @return The client's name.
     */
    public String getClientName()
    {
        return clientName;
    }

    /**
     * Sets the client's socket.
     * @param socket The client's socket.
     */
    public void setClientSocket(Socket socket)
    {
        this.socket = socket;
    }

    /**
     * Get's the client's socket.
     * @return The client's socket.
     */
    public Socket getClientSocket()
    {
        return socket;
    }

    /**
     * Gets the name of who the client wishes to chat with.
     * @return The name of who the client wishes to chat with.
     */
    public String getLookingFor()
    {
        return this.lookingFor;
    }

    /**
     * Set a client's output stream to a given socket.
     * @param socket Socket to change the client's output stream to.
     */
    public void setOutputStream(Socket socket)
    {
        try {
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a client's input stream to a given socket.
     * @param socket Socket to change the client's input stream to.
     */
    public void setInputStream(Socket socket)
    {
        try {
            this.inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets this client's paired status. Used for searching and pairing.
     * @param b Boolean if they are paired or not.
     */
    public void setPairedStatus(boolean b)
    {
        this.paired = b;
    }

    /**
     * Returns the status if the client is paired or not.
     * @return boolean
     */
    public boolean isPaired()
    {
        return paired;
    }

    /**
     * Gets a client's input stream.
     * @return The client's input stream.
     */
    public BufferedReader getInputStream()
    {
        return inReader;
    }

    /**
     * Gets the ID of the client.
     * @return int ID of the client.
     */
    public int getId()
    {
        return id;
    }

    /**
     * This function takes a string of format (src dest) and splits it which assigns its
     * name and who they wish to chat with.
     * @param input The string to parse.
     */
    private void parseInput(String input)
    {
        if(!isPaired())
        {
            try
            {
                String[] parts;
                parts = input.split("`");
                this.clientName = parts[0];
                System.out.println("CLIENT " + this.clientName + ": Setting self name as: " + parts[0]);
                this.lookingFor = parts[1];
                System.out.println("CLIENT " + this.clientName + ": Setting dest name as: " + parts[1]);
            }
            catch (Exception e)
            {
                System.out.println("Exception when parsing initial connection client input: ");
                e.printStackTrace();
            }
        }
    }

    // This thread runs once and dies right away to avoid
    // a hang up in the chat sending/receiving.
    @Override
    public void run()
    {
        try {
            String input = inReader.readLine().trim();
            if (!input.isEmpty())
            {
                parseInput(input);
            }
        } catch (IOException e) {
            System.out.println("Connection lost.");
            //e.printStackTrace();
        }
    }
}
