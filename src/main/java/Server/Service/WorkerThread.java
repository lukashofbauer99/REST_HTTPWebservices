package Server.Service;

import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Request.RequestContext;
import Server.Service.Socket.IMySocket;
import lombok.AllArgsConstructor;

import java.io.*;
import java.util.List;

@AllArgsConstructor
public class WorkerThread implements Runnable {

    IMySocket s;
    List<IHTTPMethod> registeredMethods;

    @Override
    public void run() {
        IRequestContext requestContext;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

            requestContext = new RequestContext(reader);

            if (!requestContext.getHeaders().isEmpty()) {
                System.out.println(requestContext.formatedString());

                for (IHTTPMethod method : registeredMethods) {
                    if (method.analyse(requestContext)) {
                        method.exec(requestContext).sendResponse(writer);
                        break;
                    }
                }
            }


            writer.write("");


            writer.close();
            reader.close();
            s.close(); // Close the socket itself
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
