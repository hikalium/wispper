WispperClient.class: LoginFrame.class LoginPanel.class Makefile
	javac WispperClient.java

%.class : %.java Makefile
	javac $*.java

run: WispperClient.class
	java WispperClient
