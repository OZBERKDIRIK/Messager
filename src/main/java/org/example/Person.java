package org.example;

import com.google.gson.*;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Person implements FileOperation {
    private String username;

    private String authenticatedUser = "";

    private static List<String> userCredentials = new ArrayList<>();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String getUsername() {
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

    public static List<String> getUserCredentials() {
        return userCredentials;
    }

    public static void setUserCredentials(String credentials) {
        userCredentials.add(credentials);
    }

    public String getAuthenticatedUser() {
        return authenticatedUser;
    }
    public void setAuthenticatedUser(String authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }
    @Override
    public void write(File file) throws IOException {
        if (FileOperation.messager.exists()) {
            if (!file.exists()) {
                file.createNewFile();
            }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    writer.write(Person.getUserCredentials().toString());
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println("Dosyaya yazma sırasında bir hata oluştu: " + e.getMessage());
                }
        }
    }
    @Override
    public List<Person> read(File file) {
        List<Person> personList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

            }
        } catch (IOException e) {
            System.out.println("Dosyadan okuma sırasında bir hata oluştu: " + e.getMessage());
        }
        return personList;
    }

}


