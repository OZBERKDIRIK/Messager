package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class Person implements FileOperation{
    private String username;

    private String authenticatedUser = "";

    private static Set<String> userCredentials = new HashSet<>();

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username) {
        try {
            if (username.length() >= 3) {
                this.username = username;
            }
        } catch (Exception e) {
            System.out.println("KULLANICI ADI UZUNLUĞU GEÇERLİ DEĞİL");
        }
    }

    public Set<String> getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials() {
            if (!(userCredentials.isEmpty() && userCredentials.contains(username))){
                userCredentials.add(username);
            }
        }

    public String getAuthenticatedUser()
    {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(String authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

    }

    @Override
    public void write(File file) {
        if(file.exists()){
            try(BufferedWriter bf = new BufferedWriter(new FileWriter(file))){
                String person = gson.toJson(Person.class);
                bf.write(person);
            }catch(IOException e){
                System.out.println("Dosya yazılamadı ");
            }
        }
    }
    @Override
    public List<Person> read(File file) {
        List<Person> person;
        if(file.exists()){
            try(JsonReader jsonReader = new JsonReader(new FileReader(file))){
              person = gson.fromJson(jsonReader,Person.class);
              return person;
            }catch (IOException e){
                System.out.println("Dosya okunamadı");
                return null;
            }
        }else{
            System.out.println("Okunacak Dosya bulunmadı");
            return null;
        }
    }
}


