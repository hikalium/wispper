import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class TestServer implements Runnable {
	public TestServer() {
		try {
			ServerListen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new TestServer();
		});
	}

	public final static int DEFAULT_PORT = 6543;
	protected ServerSocket listen_socket;
	Thread thread;
	Map<Integer, Connection> clients = new HashMap<>();

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
				clients.put(c.getID(), c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Connection extends Thread {
	// このインスタンスが接続中のクライアントごとに生成される．
	protected Socket client;
	protected BufferedReader in;
	protected PrintWriter out;
	TestServer server;

	private static int seq = 0;
	private int id;

	public int getID(){
		return id;
	}

	public Connection(Socket client_socket, TestServer server_frame) {
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
				System.out.println("disconnected");
			} catch (IOException e2) {
			}
		}
	}

	public void sendMsg(String msg) throws IOException {
		out.println(msg);
		out.flush();
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
}
