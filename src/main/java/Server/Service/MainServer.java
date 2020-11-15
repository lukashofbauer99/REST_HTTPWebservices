package Server.Service;

import Server.Service.Methods.DELETE.DELETE_messages_Id;
import Server.Service.Methods.Error.NotFound;
import Server.Service.Methods.GET.GET_messages_Id;
import Server.Service.Methods.GET.GET_messages;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Methods.POST.POST_messages;
import Server.Service.Methods.PUT.PUT_messages_Id;
import Server.Service.Socket.IMySocket;
import Server.Service.Socket.MySocket;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MainServer implements Runnable {

    private static ServerSocket _listener = null;


    public static void main(String[] args) {
        System.out.println("start server");

        List<IHTTPMethod> registeredMethods = new ArrayList<>();

        //register Methods
        registeredMethods.add(new GET_messages());
        registeredMethods.add(new DELETE_messages_Id());
        registeredMethods.add(new PUT_messages_Id());
        registeredMethods.add(new GET_messages_Id());
        registeredMethods.add(new POST_messages());
        registeredMethods.add(new NotFound());


        //Create Socket that listens on port 8000
        try {
            _listener = new ServerSocket(8000, 5);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new MainServer()));

        while (true) {
            try {
                IMySocket s = new MySocket(_listener.accept());
                //Start Thread for Connection
                new Thread(new WorkerThread(s,registeredMethods)).start();


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void run() {
        try {
            _listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        _listener = null;
        System.out.println("close server");
    }
}