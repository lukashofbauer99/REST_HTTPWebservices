package Server.Service;

import Server.Service.Methods.DELETE.DELETE_message_Id;
import Server.Service.Methods.Error.NotFound;
import Server.Service.Methods.GET.GET_message_Id;
import Server.Service.Methods.GET.GET_messages;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Methods.POST.POST_messages;
import Server.Service.Methods.PUT.PUT_messages_Id;
import Server.Service.Request.IRequestContext;
import Server.Service.Request.RequestContext;
import Server.Service.Socket.IMySocket;
import Server.Service.Socket.MySocket;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

//TODO: https://stackoverflow.com/questions/30901173/handling-post-request-via-socket-in-java
//200, 201, 400, 404
//Postman / Insomnia
//Bekommenen Nachrichte aufsplitten HEADER BODY USW
//Selenium
//TODO add threading
public class MainServer implements Runnable {

    private static ServerSocket _listener = null;


    public static void main(String[] args) {
        System.out.println("start server");
        IRequestContext requestContext;
        List<IHTTPMethod> registeredMethods = new ArrayList<>();

        //register Methods
        registeredMethods.add(new GET_messages());
        registeredMethods.add(new DELETE_message_Id());
        registeredMethods.add(new PUT_messages_Id());
        registeredMethods.add(new GET_message_Id());
        registeredMethods.add(new POST_messages());

        registeredMethods.add(new NotFound());


        try {
            _listener = new ServerSocket(8000, 5);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new MainServer()));

        try {
            while (true) {
                IMySocket s = new MySocket(_listener.accept());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

                requestContext = new RequestContext(reader);

                if (!requestContext.getHeaders().isEmpty()) {
                    System.out.println(requestContext.formatedString());

                    for (IHTTPMethod method : registeredMethods) {
                        if (method.analyse(requestContext)) {
                            method.exec(requestContext).SendResponse(writer);
                            break;
                        }
                    }
                }


                writer.write("");


                writer.close();
                reader.close();
                s.close(); // Close the socket itself
            }
        } catch (Exception e) {
            e.printStackTrace();
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