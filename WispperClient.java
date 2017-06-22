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
	public void sendMsg(String msg) throws IOException {
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

	public int clientID = -1;	// -1: Not assigned 

	public void startConnect() {
		try {
			sock = new Socket(this.serverName, DEFAULT_PORT);
			System.out.println("Connection established");
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			out = new java.io.PrintWriter(sock.getOutputStream());
			chatFrame = new ChatFrame(this);
			loginFrame.dispose();
		} catch (IOException e) {
			System.out.println("failed to connect (IOException)");
			loginFrame.loginPanel.setStatus("Connection Failed.", true);
		}
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	public void processMsg(String str) {
		/*
		System.out.println("received[" + str + "]");
		String[] separated = str.split(" ", 0);
		if(separated.length >= 2){
			if(separated[0].equals("id")){
				clientID = Integer.parseInt(separated[1]);
				System.out.println("assigned id:" + clientID);
				panel.appendCharacter(clientID);
				panel.revalidate();
				panel.repaint();
			} else if(separated[0].equals("connected")){
				int id = Integer.parseInt(separated[1]);
				panel.appendCharacter(id);
				panel.revalidate();
				panel.repaint();
			} else if(separated[0].equals("move")){
				int id = Integer.parseInt(separated[1]);
				if(id == clientID) return;
				int x = Integer.parseInt(separated[2]);
				int y = Integer.parseInt(separated[3]);
				panel.setCharacterPos(id, x, y);
			}
		}
		*/
	}
	public void connect(String serverName, String userName)
	{
		System.out.println("connecting " + userName + "@" + serverName + " ...");
		this.serverName = serverName;
		this.userName = userName;
		this.startConnect();
	}
	private void init() throws Exception {
		/*
		JPanel pnlHead = new JPanel();
		pnlHead.add(btnStart);
		getContentPane().add(pnlHead, BorderLayout.NORTH);

		getContentPane().add(new JScrollPane(lstMsg), BorderLayout.CENTER);
		JPanel pnlFoot = new JPanel();
		pnlFoot.add(txtInput);
		pnlFoot.add(btnSend);
		getContentPane().add(pnlFoot, BorderLayout.SOUTH);

		lstMsg.setModel(lstMsgModel);

		btnSend.addActionListener(e -> {
			if (txtInput.getText().length() != 0) {
				try {
					sendMsg(txtInput.getText());
				} catch (IOException e2) {
					processMsg(e2.toString());
				}
			}

		});

		btnStart.addActionListener(e -> {
			this.startConnect();
		});

		this.setSize(400, 300);
		this.setTitle("Chat Client");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		*/
		loginFrame = new LoginFrame(this);
		/*
		final Panel1 panel1 = new Panel1(this);
		panel1.setBackground(Color.WHITE); 
		panel = panel1;
		add(panel1);
		setVisible(true);
		final WispperClient client = this;
		// set key events
		InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getRootPane().getActionMap();
		Map<Integer, String> imList = new HashMap<>();
		imList.put(java.awt.event.KeyEvent.VK_ENTER, "key_Enter");
		imList.put(java.awt.event.KeyEvent.VK_UP, "key_Up");
		imList.put(java.awt.event.KeyEvent.VK_DOWN, "key_Down");
		imList.put(java.awt.event.KeyEvent.VK_LEFT, "key_Left");
		imList.put(java.awt.event.KeyEvent.VK_RIGHT, "key_Right");
		for(Map.Entry<Integer, String>e: imList.entrySet()){
			im.put(KeyStroke.getKeyStroke(e.getKey(), 0), e.getValue());
		}
		am.put("key_Enter",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					panel1.moveCharacter(client.clientID, 0, 10);
				}
			}
		);
		am.put("key_Up",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					panel1.moveCharacter(client.clientID, 0, -10);
				}
			}
		);
		am.put("key_Down",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					panel1.moveCharacter(client.clientID, 0, 10);
				}
			}
		);
		am.put("key_Left",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					panel1.moveCharacter(client.clientID, -10, 0);
				}
			}
		);
		am.put("key_Right",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					panel1.moveCharacter(client.clientID, 10, 0);
				}
			}
		);
		getRootPane()
			.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			.put(KeyStroke.getKeyStroke(
						java.awt.event.KeyEvent.VK_SPACE, 0), "key_Space");
		getRootPane().getActionMap().put("key_Space",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Space が押されました");
				}
			}
		);
		this.startConnect();
		*/
	}
}

