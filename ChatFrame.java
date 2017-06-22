import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class ChatFrame extends JFrame {
	final ChatPanel chatPanel;
	public ChatFrame(WispperClient client) { 
		setTitle("Wispper"); 
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		chatPanel = new ChatPanel(client);
		setSize(1024, 768); 
		add(chatPanel);
		setResizable(true);
		setVisible(true);
	}
}

