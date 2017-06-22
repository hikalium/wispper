import com.apple.eawt.UserSessionListener;

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
    Map<Integer, Connection> clients = new HashMap<>();
    public static java.util.Vector<User> UserList = new java.util.Vector<>();

    public void broadcastMsg() {
        int ability = 100;
        User from;
        for (User user : UserList) {
            if (Math.abs(user.x - from.x) <= ability && Math.abs(user.y - from.y) <= ability) {

            }
        }
    }

    public void broadcastMsg(String str) {
        try {
            for (Connection client : clients) {
                client.sendMsg(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                clients.put(c.getID(), c);
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
//    protected BufferedReader uin;
//    protected PrintWriter uout;
    WispperServer server;

    private static int seq = 0;
    private int id;

    public int getID(){
        return id;
    }

    public Connection(Socket client_socket, WispperServer server_frame) {
        client = client_socket;
        server = server_frame;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new java.io.PrintWriter(client.getOutputStream());

//            uin = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            uout = new java.io.PrintWriter(client.getOutputStream());

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

//    public void getUserInfo() throws IOException {
//        try {
//            String userInfo = uin.readLine();
//            String name;
//            int x, y;
//            User user = new User(name, x, y);
//            System.out.println(userInfo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public String receiveMsg() throws IOException {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}