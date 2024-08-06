package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleMessageServer {
    private static final int PORT = 12345;
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Sunucu " + PORT + " portunda başlatıldı");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Yeni bağlantı kabul edildi: " + clientSocket.getInetAddress());
                threadPool.execute(new ClientInput(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
