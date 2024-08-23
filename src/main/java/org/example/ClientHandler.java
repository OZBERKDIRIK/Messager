package org.example;
import java.net.Socket;
import java.io.*;
import java.util.*;
class ClientHandler {

    private Person person;
    private Message message;
    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.person = new Person();
        this.message = new Message();
    }

    public void handleQuit() {
        out.println("QUİT SUCCESS");
    }

    public void handleRead(List<String> tokens) {
        String messageID = tokens.get(1);
        List<Message> readMessage = message.read(FileOperation.allMessage);
        readMessage.forEach((readMsg -> {
            if (readMsg.messageID.equals(messageID)) {
                out.println(message.gson.toJson(readMessage, Message.class));
            }
        }));
    }

    public void handleList(List<String> tokens) {
        String send = "Gönderilenler";
        String reiceve = "Gelenler";
        List<Message> readMessage;
        if (tokens.size() == 2) {
            if (tokens.get(1).equals(send.toLowerCase(Locale.forLanguageTag("tr-TR")))) {
                readMessage = message.read(FileOperation.send);
                out.println("GÖNDERİLENLER ------ \n" + message.gson.toJson(readMessage, List.class));
            }
            if (tokens.get(1).equals(reiceve.toLowerCase(Locale.forLanguageTag("tr-TR")))) {
                readMessage = message.read(FileOperation.send);
                out.println("GELENLER ------ \n" + message.gson.toJson(readMessage, List.class));
            }
        }
    }


    public void handleSend(List<String> tokens) {
        if (tokens.size() == 4) {
            message.setReciever(tokens.get(1));
            message.setSender(person.getAuthenticatedUser());
            message.setTopic(tokens.get(2));
            message.setContent(tokens.get(3));
            message.write(FileOperation.allMessage);

            List<Person> personRead = person.read(FileOperation.auth);

            personRead.forEach((prs -> {
                if (prs.getAuthenticatedUser().equals(message.getSender())) {
                    message.write(FileOperation.send);
                }
                if (prs.getAuthenticatedUser().equals(message.getReciever())) {
                    message.write(FileOperation.recieve);
                }
            }));
            out.println("SEND SUCCESS");

        } else {
            out.println("SEND FAILURE");
        }
    }

    public void handleAuth(List<String> tokens) throws IOException {
        if (tokens.size() == 2) {
            person.setUsername(tokens.get(1));
            String email = person.getUsername();
            if(FileOperation.auth.length()!=0) {
                person.read(FileOperation.register).forEach((person1) -> {
                    if (Person.getUserCredentials().contains(email)) {
                        person.setAuthenticatedUser(email);
                        try {
                            person.write(FileOperation.auth);
                        } catch (IOException e) {
                            System.out.println("Giriş Dosyası Yazılamadı " +e.getMessage());
                        }
                    } else {
                        out.println("AUTH FAILURE");
                    }
                });
            }else{
                person.setAuthenticatedUser(email);
                person.write(FileOperation.auth);
            }
        } else {
            out.println("AUTH FAILURE");
        }
    }

    public void handleRegister(List<String> tokens) throws IOException {
        if (tokens.size() == 2) {
            FileOperation.messager.mkdirs();
            person.setUsername(tokens.get(1));
            if (FileOperation.register.length()!=0) {
                    if (!(Person.getUserCredentials().contains(person.getUsername()))) {
                        Person.setUserCredentials(tokens.get(1));
                        person.write(FileOperation.register);
                        out.println("REGISTER SUCCSESS");
                    } else {
                        out.println("KULLANICI DAHA ONCE KAYIT OLMUSTUR");
                    }

            } else {
                Person.setUserCredentials(tokens.get(1));
                person.write(FileOperation.register);
                out.println("REGISTER SUCCSESS");
            }

        } else {
            out.println("REGISTER FAILURE");
        }

    }
}






