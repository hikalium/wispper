default:
	make WispperClient.class
	make TestServer.class

WispperClient.class: WispperClient.java \
	LoginFrame.class LoginPanel.class \
	ChatFrame.class ChatPanel.class \
	MapPanel.class User.class \
   	Makefile
	javac WispperClient.java

%.class : %.java Makefile
	javac $*.java

run: WispperClient.class
	java WispperClient

run_server: WispperServer.class User.class
	java WispperServer

clean: 
	-rm *.class
