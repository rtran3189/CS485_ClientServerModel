import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientGUI extends JPanel implements ActionListener {
    // text field variable
    private JTextField chatLine;

    // text area variable
    private ClientTextArea chatArea;

    // static new line character for appending the chat text to the chat area.
    private final static String newline = "\n";

    //String to store the username.
    private String userName;

    //String to store the targetUser
    private String targetUser;

    //Store the output
    private PrintWriter output;

    // Client GUI constructor
    ClientGUI(BufferedReader in, PrintWriter out, String username, String targetUsername) {

        // Sets the layout to a Grid Bag
        super(new GridBagLayout());

        //Set the username.
        this.userName = username;

        //Set the target user.
        this.targetUser = targetUsername;

        //Set the printwriter output to out.
        output = out;

        //Sets the number of columns in the chatLine
        chatLine = new JTextField(40);

        //Adds an action listener to the chatLine
        chatLine.addActionListener(this);

        //Sets the number of rows and columns to the chatArea
        chatArea = new ClientTextArea(in);
        chatArea.setRows(20);
        chatArea.setColumns(40);
        chatArea.sendMessage.start();
        chatArea.setBackground(Color.BLACK);

        //Ensure that the chatArea is not editable.
        chatArea.setEnabled(false);

        //Adds scrolling to the chat area.
        JScrollPane chatAreaScroll = new JScrollPane(chatArea);

        //Create constraints for the grid
        GridBagConstraints constraints = new GridBagConstraints();

        //This is specifying that the components be the last one in its row.
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        //Determines the resizing of the chat area component, it resizes both horizontally and vertically.
        constraints.fill = GridBagConstraints.BOTH;

        //Set the Insets
        constraints.insets = new Insets(4,4,4,4);

        // Determines the space between the columns and rows of the grid bag layout.
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        // Adds the chatArea with the specified constraints.
        add(chatAreaScroll, constraints);

        //Determines the resizing of the chatLine components, in this case it resize's horizontally
        // but does not change its height
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Adds the chatline and with the specified constraints.
        add(chatLine, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Get the entered string.
        String chatText = chatLine.getText();

        //Send the chat message to the server.
        this.output.println(userName + "`" + targetUser + "`" + chatText);

        //Add the entered text to the text area plus a new line.
        chatArea.append(userName + ": " + chatText + newline);

        //Clear the sent text from the chat line.
        chatLine.setText("");
    }

    //Setter to allow a user to change their username.
    void setUserName(String userName) {
        this.userName = userName;
        System.out.println(this.userName);
    }
}
