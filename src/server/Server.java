package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    public static final int MAX_USERS = 20;

    private ServerSocket serverSocket;
    private boolean shutdown;

    public Server() {
        this.shutdown = true;
    }

    @Override
    public void run() {
        try {
            shutdown = false;
            serverSocket = new ServerSocket(Configuration.getInstance().getPort(), MAX_USERS,
                    InetAddress.getByName("localhost"));

            while (!shutdown) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection");
                ClientHandler handler = new ClientHandler(socket);
                handler.start();
            }
        } catch (IOException e) {
            System.out.println("Error while accepting connections, shutting down");
        } finally {
            shutdown = true;
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error while closing");
            }
        }

    }
}
