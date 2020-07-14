# Lab 1: Client/Server Model

### Created by: Eric Turnbull and Richard Tran

This is a multi-threaded Java program that runs a server for hosting chats and connects together individual chat clients.


# How to compile

To compile this program, simply `cd` into the `src` directory and run the command: `make`. To clean up the class files, run `make clean`.

# How to run
First before you run the program, make sure it is compiled. The server needs to run first before clients can connect to it. See below for instructions on how to start the server.

## Starting the server
To run the server, make sure it is first compiled and then, using a command line, launch the server with `java ServerMain`. This will prompt you to input the port number you wish to use. If the port is already being used, the program will show an error stating this. After successful server set up, you should see that the server is waiting for connections.

## Creating and connecting clients to the server
To start connecting clients to the server, make sure the server is running successfully and is awaiting connections.  Make sure the program is compiled and then run `java ClientMain`. A window with text fields will pop up. Fill out the information and click Submit when done. Upon successful connection a blank chat window will appear. When connected to the client, the server will notify that you are connected to the other client. Note that in order to be connected to another client, both clients need to be looking for each other by their user name.

# Changing user names

A client can change their user name at any time during the chat session. Simply click on the menu in the chat window and change the user name. Both clients will see the change take effect after sending their next message.

