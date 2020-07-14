import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StartupWindow extends JPanel implements ActionListener {

    private JFrame currentFrame;

    //text field for hostname address
    private JTextField hostnameField;

    //Text field for portnumber
    private JTextField portnumberField;

    //Text field for username
    private JTextField usernameField;

    //Text field for targetUser
    private JTextField targetUserField;

    private String username;
    private String targetUser;

    //StartupWindow GUI Constructor
    StartupWindow(JFrame workingFrame){

        //Set the layout to a grid bag.
        super(new GridBagLayout());

        //Set the currentFrame.
        currentFrame = workingFrame;

        //Set the number of columns for each of the text fields.
        int textFieldColumns = 40;

        //Create a text field for users to enter the hostname into.
        hostnameField = new JTextField(textFieldColumns);

        //Create a text field for users to enter the port number into.
        portnumberField = new JTextField(textFieldColumns);

        //Create the username text field.
        usernameField = new JTextField(textFieldColumns);

        //Create a textfield for the target user.
        targetUserField = new JTextField(textFieldColumns);

        //Set the submit button.
        //Button to submit the input.
        JButton submitButton = new JButton("Submit");
        submitButton.setVerticalTextPosition(AbstractButton.CENTER);
        submitButton.setHorizontalTextPosition(AbstractButton.CENTER);
        submitButton.setPreferredSize(new Dimension(100, 20));
        submitButton.addActionListener(this);

        //Create constraints for the grid
        GridBagConstraints constraints = new GridBagConstraints();

        //When components are added, they are added to the bottom
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        //Sets the window resizing behavior, the window will resize components in the horizontal direction.
        constraints.fill = GridBagConstraints.HORIZONTAL;

        //Set the inset sizes.
        constraints.insets= new Insets(4,2,2, 4);

        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        //Create a label for the host name text field and add it to the panel.
        JLabel hostnameLabel = new JLabel("Hostname");
        add(hostnameLabel, constraints);

        //Set the vertical placement of the hostnameField and add it to the panel.
        constraints.gridy = 1;
        add(hostnameField, constraints);

        //Set the vertical placement of the port number label and add it to the panel.
        constraints.gridy = 2;
        JLabel portnumberLabel = new JLabel("Port number");
        add(portnumberLabel, constraints);

        //Set the vertical placement of the port number text field and add it to the panel.
        constraints.gridy= 3;
        add(portnumberField, constraints);

        // Set the vertical placement of the username label and add it to the panel.
        constraints.gridy= 4;
        JLabel usernameLabel = new JLabel("Your Username");
        add(usernameLabel,constraints);

        // Set the vertical placement of the username field and add it to the panel.
        constraints.gridy = 5;
        add(usernameField, constraints);

        //Set the vertical placement of the target user label and add it to the panel.
        constraints.gridy = 6;
        JLabel targetUserLabel = new JLabel("Who do you want to chat with?");
        add(targetUserLabel, constraints);

        // Set the vertical placement of the target user field and add it to the panel.
        constraints.gridy = 7;
        add(targetUserField, constraints);

        // Reset the fill constraints so the submit button doesn't fill the entire bottom row.
        constraints.fill = GridBagConstraints.NONE;

        //Set the horizontal and vertical placement of the submit button.
        constraints.gridx = 1;
        constraints.gridy = 8;

        //Add the submit button to the panel.
        add(submitButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Set the hostname.
        String hostName = this.hostnameField.getText();

        //Set the port number.
        int portnumber = Integer.parseInt(this.portnumberField.getText());

        //Set the username.
        this.username = this.usernameField.getText();

        //Set the target user.
        this.targetUser = this.targetUserField.getText();

        try {
            //Create the host socket.
            Socket hostSocket = new Socket(hostName, portnumber);

            //Create an input stream from the host.
            BufferedReader in = new BufferedReader(new InputStreamReader(hostSocket.getInputStream()));

            //Create an output stream from the host socket.
            PrintWriter out = new PrintWriter(hostSocket.getOutputStream(), true);

            //Send initial setup message to server.
            out.println(this.username + "`" + this.targetUser + "`");

            //Start the client gui.
            javax.swing.SwingUtilities.invokeLater(() -> { createClientGUI(currentFrame, in, out, username, targetUser); });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void createClientGUI(JFrame workingFrame, BufferedReader in, PrintWriter out, String username, String targetusername) {

        //Set the title for the client frame.
        workingFrame.setTitle("Chat Client");

        // Creates a new ClientGUI object.
        ClientGUI client = new ClientGUI(in, out, username, targetusername);

        // Creates a new Client Menu.
        ClientMenu clientMenu = new ClientMenu();

        workingFrame.getContentPane().removeAll();

        // Adds the client to the client frame
        workingFrame.add(client);

        //Adds the menu bar to the client frame
        workingFrame.setJMenuBar(clientMenu.clientMenuBar(client));

        // Display the window.
        workingFrame.pack();
        workingFrame.setVisible(true);
    }
}
