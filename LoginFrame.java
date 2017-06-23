import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class LoginFrame extends JFrame {
	final LoginPanel loginPanel;
	public LoginFrame(WispperClient client) { 
		setTitle("Wispper Login"); 
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		loginPanel = new LoginPanel(client);
		setSize(300, 160); 
		add(loginPanel);
		setResizable(false);
		setVisible(true);
	}
}

