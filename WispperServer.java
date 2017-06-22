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

    public void broadcastMsg(Connection senderConnection, String msg) {
		User from = senderConnection.getUser();
        int ability = 100;
        for (Map.Entry<String, Connection> e : clients.entrySet()) {
			Connection connection = e.getValue();
			User user = connection.getUser();
            if (from.getDistanceTo(user) <= ability) {
				connection.sendMsg(msg);
            }
        }
    }
/*
    public void broadcastMsg(String str) {
        try {
            for (Connection client : clients) {
                client.sendMsg(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
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
				if(token[0].equals("1") && token.length == 2){
					// login
					System.out.println("login: " + token[1]);
					user = new User(token[1]);
					server.clients.put(user.getUserName(), this);
					String response = "2 0 " + server.clients.size() + " ";
					for (Map.Entry<String, Connection> e : server.clients.entrySet()) {
						Connection connection = e.getValue();
						User user = connection.getUser();
						response += user.toString() + " ";
					}
					this.sendMsg(response);
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
