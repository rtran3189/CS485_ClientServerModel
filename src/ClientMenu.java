import javax.swing.*;
import java.awt.event.KeyEvent;

class ClientMenu extends JFrame{

    JMenuBar clientMenuBar(ClientGUI clientGUI){
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        //Create the menuBar
        menuBar = new JMenuBar();

        //Build the first menu
        menu = new JMenu("Menu");

        //Set a key event for the menu
        menu.setMnemonic(KeyEvent.VK_A);

        //Set the testing text that displays when the button is pressed?
        menu.getAccessibleContext().setAccessibleDescription("Test");

        //Add the menu to the menu bar.
        menuBar.add(menu);

        //Create a menu item
        menuItem = new JMenuItem("Set Username");

        //Set what happens when the set username menu item is clicked.
        //Opens a new frame for the user to set their username.
        menuItem.addActionListener(e -> {
            //Create the popup frame.
            JFrame newFrame = new JFrame("Test popup");

            //Set the default close operation
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Add the textfield to the popup frame.
            newFrame.getContentPane().add(changeUsername(clientGUI, newFrame));
            newFrame.pack();
            newFrame.setVisible(true);
        });

        menu.add(menuItem);

        return menuBar;
    }

    private JTextField changeUsername(ClientGUI clientGUI, JFrame popup){
        JTextField usernameField = new JTextField(20);

        usernameField.addActionListener(e -> {
            //Get the input text
            String username = usernameField.getText();

            //Set the username
            clientGUI.setUserName(username);

            //Close the popup
            popup.dispose();
        });

        return usernameField;
    }
}
