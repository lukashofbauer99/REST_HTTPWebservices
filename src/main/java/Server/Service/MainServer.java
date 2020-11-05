package Server.Service;
import Server.Service.Methods.DELETE.DELETE_message_Id;
import Server.Service.Methods.GET.GET_message_Id;
import Server.Service.Methods.GET.GET_messages;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Methods.POST.POST_messages;
import Server.Service.Methods.PUT.PUT_messages_Id;
import Server.Service.Request.RequestContext;
import Server.Service.Response.ResponseContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Postman / Insomnia
//Bekommenen Nachrichte aufsplitten HEADER BODY USW
//Selenium
public class MainServer implements Runnable {

    private static ServerSocket _listener = null;


    public static void main(String[] args) {
        System.out.println("start server");
        List<String> request;
        List<String> response;
        RequestContext requestContext;
        ResponseContext responseContext = null;
        List<IHTTPMethod> registeredMethods= new ArrayList<>();

        //register Methods
        registeredMethods.add(new GET_messages());
        registeredMethods.add(new DELETE_message_Id());
        registeredMethods.add(new PUT_messages_Id());
        registeredMethods.add(new GET_message_Id());
        registeredMethods.add(new POST_messages());


        try {
            _listener = new ServerSocket(8000, 5);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new MainServer()));

        try {
            while (true) {
                request= new ArrayList<>();
                Socket s = _listener.accept();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));


                String line;
                while ((line = reader.readLine()) != null) {
                   if (line.isEmpty())
                        break;
                    request.add(line);
                    System.out.println(line + "\n");
                }

                reader.skip(1);
                while ((line = reader.readLine()) != null) {        //Doesnt Read more than one line
                    if (line.isEmpty())
                        break;
                    request.add(line);
                    System.out.println(line + "\n");
                }

                if(!request.isEmpty()) {
                    requestContext = new RequestContext(request);

                    List<String> responseList;

                    for(IHTTPMethod method : registeredMethods)
                    {
                        if(method.analyse(requestContext)) {
                            responseList = method.exec(requestContext).toList();
                            for (String respLine: responseList)
                            {
                                writer.write(respLine);
                                writer.newLine();
                            }
                        }
                    }

                    /*
                    writer.write("HTTP/1.1 200 \r\n"); // Version & status code
                    writer.newLine();
                    writer.write("Content-Type: text/plain\r\n"); // The type of data
                    writer.newLine();
                    writer.write("Connection: close\r\n"); // Will close stream
                    writer.newLine();
                    writer.write("\r\n"); // End of headers
                    */

                }

                writer.close(); // Flush and close the output stream
                reader.close(); // Close the input stream
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