JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        ChatSessionTunnel.java \
        Client.java \
        ClientGUI.java \
        ClientMain.java \
        ClientMenu.java \
        ClientTextArea.java \
        Server.java \
        ServerMain.java \
        StartupWindow.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class