package server;

import model.Protocol;
import server.requesthandler.HTTPRequestHandler;
import server.requesthandler.RequestHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private boolean running;
    private final Socket socket;
    private InputStreamReader inputStream;
    private OutputStreamWriter outputStream;
    private RequestHandler requestHandler;

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

    @Override
    public void run() {
        running = true;

        try {
            inputStream = new InputStreamReader(socket.getInputStream());
            outputStream = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Configuration.getInstance().getProtocol().equals(Protocol.HTTP10) ||
                Configuration.getInstance().getProtocol().equals(Protocol.HTTP11)) {
            requestHandler = new HTTPRequestHandler();
        }

        while (running) {
            StringBuilder requestText = new StringBuilder();
            try {
                char[] inputBytes = new char[Configuration.REQUEST_SIZE];
                int messageSize = inputStream.read(inputBytes);
                if (messageSize > 0) {
                    for (int i = 0; i < messageSize; i++) {
                        requestText.append(inputBytes[i]);
                    }
                    String response = requestHandler.handleRequest(requestText.toString());
                    outputStream.write(response);
                    outputStream.flush();
                    stopHandler();
                }
            } catch (IOException e) {
                System.out.println("error");
            } finally {
                stopHandler();
            }
        }
    }

}
