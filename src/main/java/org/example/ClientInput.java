package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientInput extends ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientInput(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Komut alındı : " + inputLine);
                ReadUserCommand(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void ReadUserCommand(String userInput) {
        String type = userInput[0].toLowerCase(new Locale("tr","TR"));
        List<String> tokens = new ArrayList<>();
        tokens.add(type);

        if (type.equals("read")) {
            String messageID = userInput[1];
            tokens.add(messageID);
            handleRead(tokens);
            return;
        }
        if (type.equals("list")) {
            String messageFolderType = userInput[1];
            tokens.add(messageFolderType);
            handleList(tokens);
            return;
        }
        if (type.equals("send")) {
            String recieve = userInput[1];
            String topic = userInput[2];
            String content = userInput.toString().substring(3, userInput.length);
            tokens.add(recieve);
            tokens.add(topic);
            tokens.add(content);
            handleSend(tokens);
            return;
        }
        if (type.equals("auth")) {
            String auth = userInput[1];
            tokens.add(auth);
            handleAuth(tokens);
            out.println(auth + "@demoMessager.com Sisteme Giriş Yaptı");
            return;
        }
        if (type.equals("register")) {
            String email = userInput[1];
            tokens.add(email);
            handleRegister(tokens);
            return;
        }
        if(type.equals("quit")){
            handleQuit();
        }
    }
}
