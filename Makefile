default:
	make WispperClient.class
	make TestServer.class

WispperClient.class: WispperClient.java LoginFrame.class LoginPanel.class Makefile
	javac WispperClient.java

%.class : %.java Makefile
	javac $*.java

run: WispperClient.class
	java WispperClient

run_server: WispperClient.class
	java TestServer
