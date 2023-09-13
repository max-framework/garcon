package server;

import server.requesthandler.HTTPRequestHandler;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private boolean running;
    private Socket socket;
    private InputStreamReader inputStream;
    private OutputStreamWriter outputStream;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.running = false;
    }

    public synchronized void stopHandler() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        running = true;

        try {
            this.inputStream = new InputStreamReader(socket.getInputStream());
            this.outputStream = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HTTPRequestHandler requestHandler = new HTTPRequestHandler();

        while (running) {
            StringBuilder requestText = new StringBuilder();
            try {
                char[] inputBytes = new char[Configuration.REQUEST_SIZE];
                int messageSize = inputStream.read(inputBytes);
                if (messageSize > 0) {
                    for (int i = 0; i < messageSize; i++) {
                        requestText.append(inputBytes[i]);
                    }
                    System.out.println(requestText);
                    String response = requestHandler.handleRequest(requestText.toString());
                    System.out.println(response);
                    outputStream.write(response);
                    outputStream.flush();
                    stopHandler();
                }
            } catch (IOException e) {
                System.out.println("error");
                stopHandler();
            }


        }
    }

}
