package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class Message implements Serializable,FileOperation {

    private static final long serialVersionUID = 1L;

    private String sender;
    private String reciever;

    private String topic;

     private String content;

     Gson gson = new GsonBuilder().setPrettyPrinting().create();
    List<String> messageID = new ArrayList<>();
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMessageID() {
        return messageID;
    }

    public void setMessageID(List<String> messageID) {
        this.messageID = messageID;
    }


    @Override
    public void write(File file) {
        if(messageFile.exists()){
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(allMessage, true))){
                String message = gson.toJson(Message.class);
                bw.write(message);
            } catch(IOException e){
                System.out.println("Dosya yazma işlemi başarılı olamadı ...");
            }

        }else{
            messageFile.mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(allMessage, true))){
                String message = gson.toJson(Message.class);
                bw.write(message);
            } catch(IOException e){
                System.out.println("Dosya yazma işlemi başarılı olamadı ...");
            }
        }
    }
    @Override
    public List<Message> read(File file) {
        List<Message> message;
        try(JsonReader reader = new JsonReader(new FileReader(allMessage))){
            message= gson.fromJson(reader,Message.class);
            return message;
        }catch (IOException e){
            System.out.println("Dosyadan okuma başarılı olamadı ....");
            return null ;
        }
    }
}
