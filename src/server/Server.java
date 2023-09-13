package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    public static final int MAX_USERS = 20;

    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlerList;
    private boolean shutdown;

    public Server() {
        this.clientHandlerList = new ArrayList<>();
        this.shutdown = false;
    }

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(Configuration.getInstance().getPort(), MAX_USERS,
                    InetAddress.getByName("localhost"));

            while (!shutdown) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection");
                ClientHandler handler = new ClientHandler(socket);
                clientHandlerList.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            System.out.println("Error while accepting connections, shutting down");
        } finally {
            shutdown = true;
            for (ClientHandler clientHandler : clientHandlerList) {
                clientHandler.stopHandler();
            }

            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error while closing");
            }
        }

    }
}
