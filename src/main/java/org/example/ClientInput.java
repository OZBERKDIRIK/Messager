package org.example;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientInput  extends  Runnable {

    ClientHandler handler1;
    ClientInput(Socket socket, ClientHandler handler) throws IOException {
        super(socket);
        handler1 = handler;
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Komut alındı : " + inputLine);
                ReadUserCommand(inputLine);
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

    public void ReadUserCommand(String userInput) throws IOException {
        String [] tokens =  userInput.split(" ",3);
        String type = tokens[0].toLowerCase(new Locale("tr","TR"));

        List<String> tokenList = new ArrayList<>();
        tokenList.add(type);

        if (type.equals("read")) {
            String messageID = tokens[1];
            tokenList.add(messageID);
            handler1.handleRead(tokenList);
            return;
        }
        if (type.equals("list")) {
            String messageFolderType = tokens[1];
            tokenList.add(messageFolderType);
            handleList(tokenList);
            return;
        }
        if (type.equals("send")) {
            String recieve = tokens[1];
            String[] topicContent = tokens[2].split(" ",2);
            String topic = topicContent[0];
            String content = topicContent[1];
            tokenList.add(recieve);
            tokenList.add(topic);
            tokenList.add(content);
            handleSend(tokenList);
            return;
        }
        if (type.equals("auth")) {
            String auth = tokens[1];
            tokenList.add(auth);
            handleAuth(tokenList);
            out.println(auth + "@demoMessager.com Sisteme Giriş Yaptı");
            return;
        }
        if (type.equals("register")) {
            String email = tokens[1];
            tokenList.add(email);
            handleRegister(tokenList);
            return;
        }
        if(type.equals("quit")){
            handleQuit();
        }
    }
}
