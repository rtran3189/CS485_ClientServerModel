/**
 * This class is a one-way chat tunnel that reads input from client1
 * and sends it to the output stream of client2.
 *
 * @author Richard Tran
 */

import java.io.*;
import java.net.Socket;

public class ChatSessionTunnel implements Runnable
{
    private BufferedReader client1Input;
    private PrintWriter client2Out;

    /**
     * Constructor for the chat tunnel.
     * @param client1 Client that is sending messages.
     * @param client2 Client that is receiving messages.
     */
    public ChatSessionTunnel(Client client1, Client client2)
    {
        System.out.println("Chat tunnel initialized! " + client1.getClientName() + " => " + client2.getClientName());
        Socket client1Socket = client1.getClientSocket();
        Socket client2Socket = client2.getClientSocket();

        // Creates the input stream of client 1 and the output stream of client 2.
        try {
            this.client1Input = new BufferedReader(
                new InputStreamReader(client1Socket.getInputStream()));
            this.client2Out = new PrintWriter(client2Socket.getOutputStream(), true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run()
    {
        while(true)
        {
            // Reads from the input of client 1, parses the string and sends
            // the sender's name and message.
            try {
                String input = client1Input.readLine();
                if (!input.isEmpty())
                {
                    String[] stringArr = input.split("`");
                    client2Out.println(stringArr[0] + ": " + stringArr[2]);
                }
            } catch (IOException e) {
                System.out.println("Connection lost.");
                break;
            }
        }
    }
}
