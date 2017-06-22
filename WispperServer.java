//import com.apple.awt.UserSessionListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WispperServer implements Runnable {
    public WispperServer() {
        try {
            ServerListen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WispperServer();
        });
    }

    public final static int DEFAULT_PORT = 6543;
    protected ServerSocket listen_socket;
    Thread thread;
    public Map<String, Connection> clients = new HashMap<>();
	final int ability = 200;

    public void broadcastMsg(Connection senderConnection, String msg) {
		User from = senderConnection.getUser();
        for (Map.Entry<String, Connection> e : clients.entrySet()) {
			Connection connection = e.getValue();
			User user = connection.getUser();
			if(user == from) continue;	// do not send to sender itself
            if (from.getDistanceTo(user) <= ability) {
				connection.sendMsg(msg);
            }
        }
    }

	public abstract class FForeachInView {
		public String str = "";
		public abstract void doEach(Connection c);
	}

	public void foreachInView(Connection fromConnection, FForeachInView f)
	{
		User from = fromConnection.getUser();
        for (Map.Entry<String, Connection> e : clients.entrySet()) {
			Connection connection = e.getValue();
			User user = connection.getUser();
			if(user == from) continue;	// do not send to sender itself
            if (from.getDistanceTo(user) <= ability) {
				f.doEach(connection);
            }
        }
	}
/*
	public void foreachConnection(Connection fromConnection, FForeachInView f)
	{
		User from = fromConnection.getUser();
        for (Map.Entry<String, Connection> e : clients.entrySet()) {
			Connection connection = e.getValue();
			User user = connection.getUser();
			if(user == from) continue;	// do not send to sender itself
			f.doEach(connection);
        }
	}
*/
	public void notifyUsersInView(Connection toConnection)
	{
		User from = toConnection.getUser();
		FForeachInView f = new FForeachInView(){
			public void doEach(Connection c){
				User user = c.getUser();
				if(user == from) return;	// do not send to sender itself
				if (from.getDistanceTo(user) <= ability) {
					str += " " + user.toString();
				}	
			}
		};
		foreachInView(toConnection, f);
		toConnection.sendMsg("5" + f.str);
	}

	public void notifyUsersInViewAll(Connection toConnection)
	{
		User from = toConnection.getUser();
		FForeachInView f = new FForeachInView(){
			public void doEach(Connection c){
				User user = c.getUser();
				if(user == from) return;	// do not send to sender itself
				if (from.getDistanceTo(user) <= ability) {
					notifyUsersInView(c);
				}	
			}
		};
		foreachInView(toConnection, f);
	}
	

    public void ServerListen() {
        try {
            listen_socket = new ServerSocket(DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server: listening on port " + DEFAULT_PORT);
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                Socket client_socket = listen_socket.accept();
                Connection c = new Connection(client_socket, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Connection extends Thread {
    protected Socket client;
    protected BufferedReader in;
    protected PrintWriter out;
    WispperServer server;
	private User user;

    public User getUser(){
        return user;
    }

	public Connection(Socket client_socket, WispperServer server_frame) {
		client = client_socket;
		server = server_frame;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new java.io.PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			try {
				client.close();
			} catch (IOException e2) {

			}
			e.printStackTrace();
			return;
		}
		System.out.println("connected ");
		this.start();
	}

    public void run() {
        try {
            for (; ; ) {
                String line = receiveMsg();
                System.out.println(">" + line);
                if (line == null) break;
				String[] token = line.split(" ", 0);
				if(token[0].equals("1")){
					if(token.length != 2){
						this.sendMsg("2 2");
						return;
					}
					if(server.clients.containsKey(token[1])){
						// already in use
						this.sendMsg("2 1");
						return;
					}
					// login accepted
					System.out.println("login: " + token[1]);
					user = new User(token[1]);
					server.clients.put(user.getUserName(), this);
					this.sendMsg("2 0 " + user.toString());
					server.notifyUsersInView(this);
				} else if(token[0].equals("3")){
					// message
					server.broadcastMsg(
							this, "3 " + user.getUserName() + " " + token[1]);

				} else if(token[0].equals("4")){
					// move
					if(token.length != 3) return;
					int x = Integer.parseInt(token[1]);
					int y = Integer.parseInt(token[2]);
					user.setPosition(x, y);
					/*
					server.broadcastMsg(
							this, "4 " + user.getUserName() + " " + x + " " + y);
							*/
					server.notifyUsersInViewAll(this);
				}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
                System.out.println("disconnected");

				if(this.user != null){
					server.clients.remove(this.user.getUserName());
				}
            } catch (IOException e2) {
            }
        }
    }

    public void sendMsg(String msg) {
		out.println(msg);
		out.flush();
    }

    public String receiveMsg() throws IOException {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
