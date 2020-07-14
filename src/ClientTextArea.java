import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

class ClientTextArea extends JTextArea {

    //Sockets input stream.
    private BufferedReader in;

    //The thread that will process incoming messages from the server.
    Thread sendMessage;

    // static new line character for appending the chat text to the chat area.
    private final static String newline = "\n";

    ClientTextArea(BufferedReader input) {

        //Create a new input stream from the passed client socket.
        in = input;

        //Create a thread to process input messages without causing the GUI to wait.
        sendMessage = new Thread(() -> {
            while (true){
                try {
                    ClientTextArea.super.append(in.readLine() + newline);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
