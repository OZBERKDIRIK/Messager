package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

interface FileOperation {

    String userName = System.getProperty("user.name");
    Person person =new Person();
    String desktopPath = System.getProperty("user.home") + "\\Desktop";
    File messager = new File(desktopPath+"\\Message");
    File register =  new File(messager, "Kayıt.txt")  ;
    File auth =new File(messager,"Giriş.txt") ;

    File messageFile= new File(desktopPath+"\\Message\\Mesajlar");

    File allMessage = new File(messageFile,"Tüm Mesajlar.txt");
    File authenticateUser = new File(FileOperation.messager+"/"+person.getAuthenticatedUser());

    File send = new File(authenticateUser,"Gönderilenler.txt");
    File recieve = new File(authenticateUser , "Gelenler.txt");

   public void write(File file) throws IOException;

   public Object read(File file);


}
