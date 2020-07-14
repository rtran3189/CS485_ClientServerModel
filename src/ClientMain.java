import javax.swing.*;

public class ClientMain {

    private static void createStartupGUI(){
        //Set the working frame that the chat client occupies.
        JFrame workingFrame = new JFrame("Chat Client");

        //Set what happens when a chat client is closed
        workingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create a startup panel
        StartupWindow startUp = new StartupWindow(workingFrame);

        //Add the startup panel to the workingFrame.
        workingFrame.add(startUp);

        //Pack and set the working frame to visible.
        workingFrame.pack();
        workingFrame.setVisible(true);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(ClientMain::createStartupGUI);

    }
}
