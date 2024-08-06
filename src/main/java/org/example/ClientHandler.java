package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

class ClientHandler {

    private Person person = new Person();


    private Message message = new Message();

    private PrintWriter out;
    private BufferedReader in;

    public void handleQuit() {
        out.println("QUİT SUCCESS");

    }
    public void handleRead(List<String> tokens) {
        String messageID = tokens.get(1);
        List<Message> readMessage =message.read(FileOperation.allMessage);
        readMessage.forEach((readMsg -> {
            if(readMsg.messageID.equals(messageID)){
                out.println(message.gson.toJson(readMessage,Message.class));
            }
        }));
    }

    public void handleList(List<String> tokens){
        String send = "Gönderilenler";
        String reiceve = "Gelenler";
        List <Message> readMessage;
        if(tokens.size()==2){
            if(tokens.get(1).equals(send.toLowerCase(Locale.forLanguageTag("tr-TR")))){
                readMessage= message.read(FileOperation.send);
                out.println("GÖNDERİLENLER ------ \n" + message.gson.toJson(readMessage,List.class));
            }
            if(tokens.get(1).equals(reiceve.toLowerCase(Locale.forLanguageTag("tr-TR")))){
                readMessage= message.read(FileOperation.send);
                out.println("GELENLER ------ \n" + message.gson.toJson(readMessage,List.class));
            }
        }
    }


    public void handleSend(List<String> tokens){
        if(tokens.size()==4){
            message.setReciever(tokens.get(1));
            message.setSender(person.getAuthenticatedUser());
            message.setTopic(tokens.get(2));
            message.setContent(tokens.get(3));
            message.write(FileOperation.allMessage);

           List<Person> personRead = person.read(FileOperation.auth);

            personRead.forEach((prs -> {
                if(prs.getAuthenticatedUser().equals(message.getSender()))
                {
                    message.write(FileOperation.send);
                }
                if(prs.getAuthenticatedUser().equals(message.getReciever())){
                    message.write(FileOperation.recieve);
                }
            }));


            out.println("SEND SUCCESS");

        }else{
            out.println("SEND FAILURE");
        }
    }



    public void handleAuth(List<String> tokens){
        if(tokens.size()==2) {
            person.setUsername(tokens.get(1));
            String email = person.getUsername();
            if ((person.getUserCredentials().contains(email))) {
                person.setAuthenticatedUser(email);
                person.write(FileOperation.auth);
                out.println("AUTH SUCCESS");
            } else {
                out.println("AUTH FAILURE");
            }
        }else{
            out.println("AUTH FAILURE");
        }
    }


    public void handleRegister(List<String> tokens){
        if(tokens.size()==2) {
            person.setUsername(tokens.get(1));
            if (!(person.getUsername().isEmpty())) {
                person.setUserCredentials();
                person.write(FileOperation.register);
                out.println("REGISTER SUCCSESS");
            } else {
                out.println("REGISTER FAILURE");
            }
        }else{
            out.println("REGISTER FAILURE");
        }
    }

}




