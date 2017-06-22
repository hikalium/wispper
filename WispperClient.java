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

public class WispperClient extends JFrame implements Runnable {
	LoginFrame loginFrame;
	ChatFrame chatFrame;
	public WispperClient() { 
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String receiveMsg() throws IOException {
		try {
			String msg = in.readLine();
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	public void sendMsg(String msg){
		out.println(msg);
		out.flush();
	}
	public void run() {
		while (true) {
			try {
				String msg = receiveMsg();
				if (msg != null) {
					processMsg(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}		
	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> {
			new WispperClient();
		});
	}
	Socket sock;
	Thread thread;
	BufferedReader in;
	PrintWriter out;
	public final static int DEFAULT_PORT = 6543;
	public String userName;
	public String serverName;
	public MapPanel mapPanel;

	public Map<String, MapPanel.Character> users = new HashMap<>();

	public MapPanel.Character getMainCharacter(){
		return users.get(userName);
	}

	public void startConnect() {
		try {
			sock = new Socket(this.serverName, DEFAULT_PORT);
			System.out.println("Connection established");
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			out = new java.io.PrintWriter(sock.getOutputStream());
			sendMsg("1 " + userName);
			if (thread == null) {
				thread = new Thread(this);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("failed to connect (IOException)");
			loginFrame.loginPanel.setStatus("Connection Failed.", true);
		}
	}
	public void processMsg(String msg) {
		System.out.println("received[" + msg + "]");
		String[] token = msg.split(" ", 0);
		if(token[0].equals("2") && token.length >= 2){
			if(token[1].equals("1")){
				// duplicated user:
				loginFrame.loginPanel.setStatus("User name already in use", true);
				return;
			} else if(token[1].equals("0")){
				// OK
				chatFrame = new ChatFrame(this);
				loginFrame.dispose();
				//
				MapPanel.Character u;
				u = new MapPanel.Character(this, "orange.png");
				u.setLocation(
						Integer.parseInt(token[3]),
						Integer.parseInt(token[4]), false);
				users.put(token[2], u);
				return;
			}
			loginFrame.loginPanel.setStatus("Unknown error", true);
		} else if(token[0].equals("4")){
			// move
			/*
			for(int i = 1; i < token.length; i += 3){
				MapPanel.Character u;
				if(users.containsKey(token[i])){
					u = users.get(token[i]);
				} else{
					u = new MapPanel.Character(this, "orange.png");
					users.put(token[i], u);
				}
				u.setLocation(
						Integer.parseInt(token[i + 1]),
						Integer.parseInt(token[i + 2]));
			}
			*/
			/*
			for(Map.Entry<String, Character> e : users.entrySet()){
				
			}
			*/
		} else if(token[0].equals("5")){
			// notifyUsersInView
			for(int i = 1; i < token.length; i += 3){
				MapPanel.Character u;
				if(users.containsKey(token[i])){
					u = users.get(token[i]);
				} else{
					u = new MapPanel.Character(this, "orange.png");
					users.put(token[i], u);
				}
				u.setLocation(
						Integer.parseInt(token[i + 1]),
						Integer.parseInt(token[i + 2]), false);
			}
			/*
			for(Map.Entry<String, Character> e : users.entrySet()){
				
			}
			*/
		}
	}
	public void connect(String serverName, String userName)
	{
		System.out.println("connecting " + userName + "@" + serverName + " ...");
		this.serverName = serverName;
		this.userName = userName;
		this.startConnect();
	}
	private void init() throws Exception {
		loginFrame = new LoginFrame(this);
	}
}

